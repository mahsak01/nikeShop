package com.example.nikeshop.features.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nikeshop.R
import com.example.nikeshop.common.NikeFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class CartFragment:NikeFragment() {

    val viewModel:CartViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cart,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.progressLiveData.observe(viewLifecycleOwner){
            setProgressIndicator(it)
        }
        viewModel.cartItemLiveData.observe(viewLifecycleOwner){
            Timber.i(it.toString())
        }
        viewModel.purchaseDetailLiveData.observe(viewLifecycleOwner){
            Timber.i(it.toString())

        }
    }

    override fun onStart() {
        viewModel.refresh()
        super.onStart()
    }
}