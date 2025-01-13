package com.example.pizzapay.data

import com.example.pizzapay.data.model.ConfigResponse
import it.nexipos.app2app.App2AppIPC

data class NexiApp2AppContainer(
    val app2AppIPC: App2AppIPC? = null, val configResponse: ConfigResponse? = null
)