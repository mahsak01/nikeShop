package com.example.nikeshop.data.model

object TokenContainer {
    var token: String? = null
        private set
    var refreshToken: String? = null
        private set

    fun update(token: String?, refreshToken: String?) {
        this.token = token
        this.refreshToken = refreshToken
    }
}