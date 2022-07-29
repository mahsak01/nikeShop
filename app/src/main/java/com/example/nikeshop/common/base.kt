package com.example.nikeshop.common

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nikeshop.R
import io.reactivex.disposables.CompositeDisposable


abstract class NikeFragment : NikeView, Fragment() {

    override val rootView: CoordinatorLayout?
        get() = view as CoordinatorLayout

    override val viewContext: Context?
        get() = context
}

abstract class NikeActivity : NikeView, AppCompatActivity() {
    override val rootView: CoordinatorLayout?
        get() = window.decorView.rootView as CoordinatorLayout?

    override val viewContext: Context?
        get() = this


}

interface NikeView {

    val rootView: CoordinatorLayout?
    val viewContext: Context?
    fun setProgressIndicator(mustShow: Boolean) {
        rootView?.let {
            viewContext?.let { context ->
                var loadingView = it.findViewById<View>(R.id.loadingView)
                if (loadingView == null && mustShow){
                    loadingView=LayoutInflater.from(context).inflate(R.layout.view_loading,it,false)
                    it.addView(loadingView)
                }
                loadingView?.visibility = if (mustShow) View.VISIBLE else View.GONE


            }
        }
    }
}

abstract class NikeViewModel : ViewModel() {
    val compositeDisposable = CompositeDisposable()
    val progressLiveData= MutableLiveData<Boolean>()



    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

}
