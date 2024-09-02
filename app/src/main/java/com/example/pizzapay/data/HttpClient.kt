package com.example.pizzapay.data

import android.os.StrictMode
import android.util.Log
import com.google.gson.Gson
import com.example.pizzapay.Config
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import java.util.UUID

class HttpClient {

    fun <B, R : Any> post(
        uri: String,
        requestBody: B,
        classOfR: Class<R>,
        sessionId: String? = null,
        baseUrl: String? = null
    ): ApiResult<R> {
        try {
            val client = OkHttpClient()

            val policy = StrictMode.ThreadPolicy.Builder().permitNetwork().build()

            StrictMode.setThreadPolicy(policy)

            val gson = Gson()

            val body = gson.toJson(requestBody).toRequestBody("application/json".toMediaType())

            val url = (baseUrl ?: Config.BASE_URL) + uri

            val requestBuilder = Request.Builder().url(url).post(body)

            if (sessionId != null) {
                requestBuilder.header("Authorization", "Bearer $sessionId")
            }

            val xRequestId = generateXRequestId()

            requestBuilder.header("x-request-id", xRequestId)

            val request = requestBuilder.build()

            val response = client.newCall(request).execute()

            Log.d(
                "APP_DEBUG", "[$xRequestId] call: " + url + " -- response code: " + response.code
            )

            return if (response.code != 200) {
                ApiResult.Error(Exception("[$xRequestId] call: " + url + " -- response code: " + response.code))
            } else {
                val parsedResponse = gson.fromJson(response.body?.string(), classOfR)

                ApiResult.Success<R>(parsedResponse)
            }
        } catch (e: Throwable) {
            return ApiResult.Error(IOException("error in $uri request", e))
        }
    }

    private fun generateXRequestId(): String {
        return UUID.randomUUID().toString()
    }

}