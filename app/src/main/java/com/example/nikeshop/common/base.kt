package com.example.nikeshop.common

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable


abstract class NikeFragment : NikeView, Fragment() {
    override fun setProgressIndicator(mustShow: Boolean) {
        TODO("Not yet implemented")
    }
}

abstract class NikeActivity : NikeView, AppCompatActivity() {
    override fun setProgressIndicator(mustShow: Boolean) {
        TODO("Not yet implemented")
    }
}

interface NikeView {
    fun setProgressIndicator(mustShow: Boolean)
}

abstract class NikeViewModel : ViewModel() {
    val compositeDisposable = CompositeDisposable()


    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

}