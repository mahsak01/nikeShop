package com.example.nikeshop.data.source

import com.example.nikeshop.data.model.TokenResponse
import com.sevenlearn.nikestore.data.MessageResponse
import io.reactivex.Completable
import io.reactivex.Single

interface UserDataSource {


    fun login(username: String, password: String): Single<TokenResponse>

    fun signUp(username: String, password: String): Single<MessageResponse>

    fun loadToken()

    fun saveToken(token: String, refreshToken: String)

    fun saveUsername(username: String)

    fun getUsername(): String

    fun signOut()
}