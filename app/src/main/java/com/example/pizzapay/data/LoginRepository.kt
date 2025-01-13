package com.example.pizzapay.data

import com.example.pizzapay.data.model.LoggedInUser

class LoginRepository(val dataSource: LoginDataSource) {

    var user: LoggedInUser? = null
        private set

    init {
        user = null
    }

    fun login(username: String, password: String): ApiResult<LoggedInUser> {
        val result = dataSource.login(username, password)

        return if (result is ApiResult.Success) {
            if (result.data.success) {
                setLoggedInUser(result.data)

                result
            } else {
                ApiResult.Error(Exception("Incorrect credentials exception"))
            }
        } else {
            result
        }
    }

    private fun setLoggedInUser(loggedInUser: LoggedInUser) {
        this.user = loggedInUser
    }
}