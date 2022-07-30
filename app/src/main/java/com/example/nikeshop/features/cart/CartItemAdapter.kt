package com.example.nikeshop.features.cart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nikeshop.R
import com.example.nikeshop.common.VIEW_TYPE_CART_ITEM
import com.example.nikeshop.common.VIEW_TYPE_PURCHASE_DETAILS
import com.example.nikeshop.data.model.PurchaseDetail
import com.example.nikeshop.service.http.ImageLoadingService
import com.sevenlearn.nikestore.common.formatPrice
import com.sevenlearn.nikestore.data.CartItem
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.activity_product_list.view.*
import kotlinx.android.synthetic.main.item_card.view.*
import kotlinx.android.synthetic.main.item_purchase_detail.view.*

class CartItemAdapter(
    val cartItems: MutableList<CartItem>,
    val imageLoadingService: ImageLoadingService,
    val cartItemViewCallbacks: CartItemViewCallbacks
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    var purchaseDetail:PurchaseDetail?=null

    inner class CartItemViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bindCartItem(cartItem: CartItem) {
            containerView.productTitleTextView.text = cartItem.product.title
            containerView.cartItemCountTextView.text = cartItem.count.toString()
            containerView.previousPriceTextView.text =
                formatPrice(cartItem.product.price + cartItem.product.discount)
            containerView.currentPriceTextView.text = formatPrice(cartItem.product.price)
            imageLoadingService.load(containerView.productImageView, cartItem.product.image)
            containerView.increaseButton.setOnClickListener {
                cartItemViewCallbacks.onIncreaseCartItemButtonClick(cartItem)
            }

            containerView.decreaseButton.setOnClickListener {
                if (cartItem.count > 1) {
                    cartItem.changeCountProgressBarIsVisible = true
                    containerView.changeCountProgressBar.visibility = View.VISIBLE
                    containerView.cartItemCountTextView.visibility = View.INVISIBLE
                    cartItemViewCallbacks.onDecreaseCartItemButtonClick(cartItem)
                }

            }

            containerView.removeFromCartButton.setOnClickListener {
                cartItemViewCallbacks.onRemoveCartItemButtonClick(cartItem)
            }

            containerView.changeCountProgressBar.visibility =
                if (cartItem.changeCountProgressBarIsVisible) View.VISIBLE else View.INVISIBLE

            containerView.cartItemCountTextView.visibility =
                if (!cartItem.changeCountProgressBarIsVisible) View.VISIBLE else View.INVISIBLE

            containerView.productImageView.setOnClickListener {
                cartItem.changeCountProgressBarIsVisible = true
                containerView.changeCountProgressBar.visibility = View.VISIBLE
                containerView.cartItemCountTextView.visibility = View.INVISIBLE
                cartItemViewCallbacks.onProductImageClick(cartItem)
            }
        }
    }

    inner class PurchaseDetailViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(totalPrice: Int, shippingCost: Int, payableCost: Int) {

            containerView.totalPriceTextView.text = formatPrice(totalPrice)
            containerView.shippingPriceTextView.text = formatPrice(shippingCost)
            containerView.payablePriceTextView.text = formatPrice(payableCost)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == VIEW_TYPE_CART_ITEM)
            return CartItemViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
            )

        return PurchaseDetailViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_purchase_detail, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CartItemViewHolder)
             holder.bindCartItem(cartItems[position])
        else if (holder is PurchaseDetailViewHolder)
            purchaseDetail?.let {
                 holder.bind(it.totalPrice,
                    it.shipping_cost, it.payable_price)
            }

    }

    override fun getItemViewType(position: Int): Int {
        if (position == cartItems.size)
            return VIEW_TYPE_PURCHASE_DETAILS
        return VIEW_TYPE_CART_ITEM
    }

    override fun getItemCount(): Int = cartItems.size + 1

    fun removeCartItem(cartItem: CartItem){
        val index=cartItems.indexOf(cartItem)
        if (index>=0) {
            cartItems.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    fun increaseCartItem(cartItem: CartItem){
        val index=cartItems.indexOf(cartItem)
        if (index>=0) {
            cartItems[index].changeCountProgressBarIsVisible=false
            notifyItemChanged(index)
        }
    }


    fun decreaseCartItem(cartItem: CartItem){
        val index=cartItems.indexOf(cartItem)
        if (index>=0) {
            cartItems[index].changeCountProgressBarIsVisible=false
            notifyItemChanged(index)

        }
    }


    interface CartItemViewCallbacks {
        fun onRemoveCartItemButtonClick(cartItem: CartItem)
        fun onIncreaseCartItemButtonClick(cartItem: CartItem)
        fun onDecreaseCartItemButtonClick(cartItem: CartItem)
        fun onProductImageClick(cartItem: CartItem)
    }
}