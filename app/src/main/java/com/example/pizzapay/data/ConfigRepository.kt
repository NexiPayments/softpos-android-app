package com.example.pizzapay.data

import com.example.pizzapay.data.model.ConfigResponse

class ConfigRepository(val dataSource: ConfigDataSource) {

    fun getConfig(
        sessionId: String
    ): ApiResult<ConfigResponse> {
        return dataSource.getConfig(sessionId)
    }

}

