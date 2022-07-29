package com.example.nikeshop.data.source.remote

import com.example.nikeshop.common.CLIENT_ID
import com.example.nikeshop.common.CLIENT_SECRET
import com.example.nikeshop.data.model.TokenResponse
import com.example.nikeshop.data.source.UserDataSource
import com.example.nikeshop.service.http.ApiService
import com.google.gson.JsonObject
import com.sevenlearn.nikestore.data.MessageResponse
import io.reactivex.Single

class UserRemoteDataSource(val apiService: ApiService) : UserDataSource {
    override fun login(username: String, password: String): Single<TokenResponse> =
        apiService.login(
            JsonObject().apply {
                addProperty("username", username)
                addProperty("password", password)
                addProperty("grant_type", "password")
                addProperty("client_id", CLIENT_ID)
                addProperty("client_secret", CLIENT_SECRET)

            }
        )

    override fun signUp(username: String, password: String): Single<MessageResponse> = apiService.signUp(JsonObject().apply {
        addProperty("email", username)
        addProperty("password", password)
    })

    override fun loadToken() {
        TODO("Not yet implemented")
    }

    override fun saveToken(token: String, refreshToken: String) {
        TODO("Not yet implemented")
    }
}