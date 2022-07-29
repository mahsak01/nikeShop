package com.example.nikeshop.data.source.local

import android.content.SharedPreferences
import com.example.nikeshop.data.model.TokenContainer
import com.example.nikeshop.data.model.TokenResponse
import com.example.nikeshop.data.source.UserDataSource
import com.sevenlearn.nikestore.data.MessageResponse
import io.reactivex.Single

class UserLocalDataSource(val sharedPreferences: SharedPreferences):UserDataSource {
    override fun login(username: String, password: String): Single<TokenResponse> {
        TODO("Not yet implemented")
    }

    override fun signUp(username: String, password: String): Single<MessageResponse> {
        TODO("Not yet implemented")
    }

    override fun loadToken() {
       TokenContainer.update(sharedPreferences.getString("access_token",null),
           sharedPreferences.getString("refresh_token",null))
    }

    override fun saveToken(token: String, refreshToken: String) {
        sharedPreferences.edit().apply{
            putString("access_token",token)
            putString("refresh_token",refreshToken)

        }.apply()
    }

}