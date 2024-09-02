package com.example.pizzapay.data

import com.example.pizzapay.data.model.ConfigResponse

class ConfigDataSource {
    fun getConfig(
        sessionId: String
    ): ApiResult<ConfigResponse> {
        val client = HttpClient()

        val uri = "/android-app-config/obtain"

        return client.post(uri, null, ConfigResponse::class.java, sessionId)
    }
}