package com.example.nikeshop.data.implement

import com.example.nikeshop.data.Repository.UserRepository
import com.example.nikeshop.data.model.TokenContainer
import com.example.nikeshop.data.model.TokenResponse
import com.example.nikeshop.data.source.UserDataSource
import com.example.nikeshop.data.source.local.UserLocalDataSource
import io.reactivex.Completable

class UserRepositoryImplement(
    val userLocalDataSource: UserDataSource,
    val userRemoteDataSource: UserDataSource
) : UserRepository {
    override fun login(username: String, password: String): Completable =
        userRemoteDataSource.login(username, password).doOnSuccess {
            onSuccessfulLogin(username, it)
        }.ignoreElement()

    override fun signUp(username: String, password: String): Completable {
        return userRemoteDataSource.signUp(username, password).flatMap {
            userRemoteDataSource.login(username, password)
        }.doOnSuccess {
            onSuccessfulLogin(username, it)
        }.ignoreElement()
    }

    override fun loadToken() {
        userLocalDataSource.loadToken()
    }

    override fun getUserName(): String {
        return userLocalDataSource.getUsername()
    }

    override fun signOut() {
        userLocalDataSource.signOut()
        TokenContainer.update(null,null)
    }

    fun onSuccessfulLogin(username: String, tokenResponse: TokenResponse) {
        TokenContainer.update(tokenResponse.access_token, tokenResponse.refresh_token)
        userLocalDataSource.saveToken(tokenResponse.access_token, tokenResponse.refresh_token)
        userLocalDataSource.saveUsername(username)
    }
}