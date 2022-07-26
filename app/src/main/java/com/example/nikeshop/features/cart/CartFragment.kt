package com.example.nikeshop.features.cart

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nikeshop.R
import com.example.nikeshop.common.EXTRA_KEY_DATA
import com.example.nikeshop.common.NikeCompletableObserver
import com.example.nikeshop.common.NikeFragment
import com.example.nikeshop.features.auth.AuthActivity
import com.example.nikeshop.features.product.ProductDetailActivity
import com.example.nikeshop.service.http.ImageLoadingService
import com.sevenlearn.nikestore.data.CartItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_cart.*
import kotlinx.android.synthetic.main.view_empty_state.*
import kotlinx.android.synthetic.main.view_empty_state.view.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class CartFragment : NikeFragment(), CartItemAdapter.CartItemViewCallbacks {

    val viewModel: CartViewModel by viewModel()
    var cartItemAdapter: CartItemAdapter? = null
    val imageLoadingService: ImageLoadingService by inject()
    val compositeDisposable = CompositeDisposable()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.progressLiveData.observe(viewLifecycleOwner) {
            setProgressIndicator(it)
        }
        viewModel.cartItemLiveData.observe(viewLifecycleOwner) {
            Timber.i(it.toString())
            cartItemRecyclerView.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            cartItemAdapter =
                CartItemAdapter(it as MutableList<CartItem>, imageLoadingService, this)
            cartItemRecyclerView.adapter = cartItemAdapter
        }
        viewModel.purchaseDetailLiveData.observe(viewLifecycleOwner) {
            Timber.i(it.toString())
            cartItemAdapter?.let { adapter ->
                adapter.purchaseDetail = it
                adapter.notifyItemChanged(adapter.cartItems.size)
            }

        }

        viewModel.emptyStateLiveData.observe(viewLifecycleOwner) {
            if (it.mustShow) {
                val emptyState = showEmptyState(R.layout.view_empty_state)
                emptyState?.let { view ->
                    view.emptyStateMessageTextView.text = getString(it.messageResId)
                    view.emptyStateButton.visibility =
                        if (it.mostShowCallToActionButton) View.VISIBLE else View.GONE

                    view.emptyStateButton.setOnClickListener {
                        startActivity(Intent(requireContext(), AuthActivity::class.java))
                    }
                }
            } else {
                emptyStateRootView?.visibility = View.GONE
            }
        }
    }

    override fun onStart() {
        viewModel.refresh()
        super.onStart()
    }

    override fun onRemoveCartItemButtonClick(cartItem: CartItem) {
        viewModel.removeItemFromCart(cartItem)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : NikeCompletableObserver(compositeDisposable) {
                override fun onComplete() {
                    cartItemAdapter?.removeCartItem(cartItem)
                }

            })
    }

    override fun onIncreaseCartItemButtonClick(cartItem: CartItem) {
        viewModel.increaseItemFromCart(cartItem)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : NikeCompletableObserver(compositeDisposable) {
                override fun onComplete() {
                    cartItemAdapter?.increaseCartItem(cartItem)
                }

            })
    }

    override fun onDecreaseCartItemButtonClick(cartItem: CartItem) {
        viewModel.decreaseItemFromCart(cartItem)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : NikeCompletableObserver(compositeDisposable) {
                override fun onComplete() {
                    cartItemAdapter?.decreaseCartItem(cartItem)
                }

            })
    }

    override fun onProductImageClick(cartItem: CartItem) {
        startActivity(Intent(requireContext(), ProductDetailActivity::class.java).apply {
            putExtra(EXTRA_KEY_DATA, cartItem.product)
        })
    }
}