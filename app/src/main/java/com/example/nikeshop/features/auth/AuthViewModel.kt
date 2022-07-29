package com.example.nikeshop.features.auth

import com.example.nikeshop.common.NikeViewModel
import com.example.nikeshop.data.Repository.UserRepository
import io.reactivex.Completable

class AuthViewModel(val userRepository: UserRepository) : NikeViewModel() {
    fun login(email: String, password: String): Completable {
        progressLiveData.value = true
        return userRepository.login(email,password).doFinally{
            progressLiveData.postValue(false)

        }
    }

    fun signup(email: String, password: String): Completable {
        progressLiveData.value = true
        return userRepository.signUp(email,password).doFinally{
            progressLiveData.value = false

        }
    }
}