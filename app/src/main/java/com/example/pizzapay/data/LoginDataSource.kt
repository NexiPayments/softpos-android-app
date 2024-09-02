package com.example.pizzapay.data

import com.example.pizzapay.data.model.LoggedInUser

class LoginDataSource {

    fun login(email: String, password: String): ApiResult<LoggedInUser> {
        val client = HttpClient()

        val uri = "/user/login"

        val requestBody = mapOf(
            "email" to email, "password" to password
        )

        return client.post(uri, requestBody, LoggedInUser::class.java)
    }

    fun logout() {
        // TODO: revoke authentication
    }
}