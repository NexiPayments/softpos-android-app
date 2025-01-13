package com.example.pizzapay

import android.app.Activity
import android.util.Log
import android.view.View
import com.example.pizzapay.data.ApiResult
import com.example.pizzapay.data.ConfigDataSource
import com.example.pizzapay.data.ConfigRepository
import com.example.pizzapay.data.NexiApp2AppContainer
import com.example.pizzapay.data.UriDataSource
import com.example.pizzapay.data.UriRepository
import it.nexipos.app2app.App2AppIPC
import it.nexipos.app2app.data.model.AppData
import it.nexipos.app2app.utility.App2AppDomain
import com.google.android.material.snackbar.Snackbar

class NexiApp2App {

    fun getApp2App(
        activity: Activity, view: View, sessionId: String, username: String, onSdkConnected: () -> Unit
    ): NexiApp2AppContainer? {
        // Repository used to retrieve application's config
        val configRepository = ConfigRepository(ConfigDataSource())

        // Repository used to retrieve requestUri from the server
        val uriRepository = UriRepository(UriDataSource())

        // Retrieve application's config
        val resultConfig = configRepository.getConfig(sessionId)

        // String used to get back in our app
        val deepLinkScheme = Config.DEEPLINK_SCHEME

        // Value set in the app configuration during onboarding
        val redirectUri = Config.REDIRECT_URI

        // Check if configuration call is success
        if (resultConfig is ApiResult.Success) {
            // Create SDK object
            val app2app = App2AppIPC(activity, AppData(
                resultConfig.data.client_id, // AppID / ClientID retrieved from server
                resultConfig.data.android_app_user_config.point_of_sale, // MerchantID retrieved from server
                redirectUri, // As configured during onboarding
                username, // Logged in user's email
                deepLinkScheme, // Used to redirect the user on our app
                App2AppDomain.STAGING
            ), fun(deviceId: String, onSuccess: (String) -> Unit, onError: (String) -> Unit) {
                Log.d("APP_DEBUG", "onGetRequestUri --> deviceId = $deviceId")

                // Get requestUri from server
                val resultRequestUri = uriRepository.getRequestUri(sessionId, redirectUri, deviceId)

                // Check result
                if (resultRequestUri is ApiResult.Success) {
                    Log.d(
                        "APP_DEBUG",
                        "resultRequestUri.data.request_uri -> " + resultRequestUri.data.request_uri
                    )

                    showSnackbar(view, "PAR eseguita correttamente")

                    // Call SDK function passing the requestUri retrieved by the server
                    onSuccess(resultRequestUri.data.request_uri)
                } else {
                    onError("Unable to retrieve request_uri")
                }
            }, fun() {
                // Notify the app that the SDK is connected and ready to start a new operation
                onSdkConnected()
            })

            // SDK initialization
            app2app.connectToRemoteService()

            return NexiApp2AppContainer(app2app, resultConfig.data);
        }

        return null;
    }

    private fun showSnackbar(view: View, message: String) {
        val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
        snackbar.show()
    }

}