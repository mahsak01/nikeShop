package com.example.nikeshop.features.profile

import com.example.nikeshop.common.NikeViewModel
import com.example.nikeshop.data.Repository.UserRepository
import com.example.nikeshop.data.model.TokenContainer

class ProfileViewModel(val userRepository: UserRepository):NikeViewModel() {

    val username:String
    get() = userRepository.getUserName()

    val isSignedIn:Boolean
        get()= TokenContainer.token!=null

    fun signOut()=userRepository.signOut()
}