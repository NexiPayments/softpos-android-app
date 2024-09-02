package com.example.pizzapay.data

import com.example.pizzapay.data.model.RequestUriResponse

class UriDataSource {
    fun getRequestUri(
        sessionId: String, redirectUri: String, deviceId: String
    ): ApiResult<RequestUriResponse> {
        val client = HttpClient()

        val uri = "/par/execute"

        val requestBody = mapOf(
            "redirect_uri" to redirectUri, "device_id" to deviceId
        )

        return client.post(uri, requestBody, RequestUriResponse::class.java, sessionId)
    }
}