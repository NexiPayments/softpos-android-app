package com.example.pizzapay.data

import com.example.pizzapay.data.model.RequestUriResponse

class UriRepository(val dataSource: UriDataSource) {

    fun getRequestUri(
        sessionId: String, redirectUri: String, deviceId: String
    ): ApiResult<RequestUriResponse> {
        return dataSource.getRequestUri(sessionId, redirectUri, deviceId)
    }

}

