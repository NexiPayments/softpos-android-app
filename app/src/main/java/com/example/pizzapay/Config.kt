package com.example.pizzapay

class Config {

    companion object {
        // Server backend's url
        const val BASE_URL: String = "https://your-server-url.example.com/pizzapay/api/v1"

        // As set in your AndroidManifest.xml
        const val DEEPLINK_SCHEME: String = "pizzapay"

        // As configured during onboarding
        const val REDIRECT_URI: String = "pizzapay://confirm-pay"
    }

}
