package com.example.nikeshop.common

import io.reactivex.CompletableObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import timber.log.Timber

abstract class NikeCompletableObserver(val compositeDisposable: CompositeDisposable):CompletableObserver {
    override fun onSubscribe(d: Disposable) {
        compositeDisposable.add(d)

    }

    override fun onError(e: Throwable) {
        Timber.e(e)
    }
}