package com.example.nikeshop.data.Repository

import io.reactivex.Completable
import io.reactivex.Single

interface UserRepository {

    fun login(username:String,password:String):Completable

    fun signUp(username:String,password:String):Completable

    fun loadToken()

    fun getUserName():String

    fun signOut()
}