package com.example.nikeshop.features.cart
import NikeSingleObserver
import androidx.lifecycle.MutableLiveData
import com.example.nikeshop.common.NikeViewModel
import com.example.nikeshop.data.Repository.CartRepository
import com.example.nikeshop.data.model.PurchaseDetail
import com.example.nikeshop.data.model.TokenContainer
import com.sevenlearn.nikestore.common.asyncNetworkRequest
import com.sevenlearn.nikestore.data.CartItem
import com.sevenlearn.nikestore.data.CartResponse
import io.reactivex.Completable

class CartViewModel(val cartRepository: CartRepository) : NikeViewModel() {

    val cartItemLiveData = MutableLiveData<List<CartItem>>()
    val purchaseDetailLiveData = MutableLiveData<PurchaseDetail>()

    private fun getCartItems() {

        progressLiveData.value = true
        if (!TokenContainer.token.isNullOrEmpty())
            cartRepository.get()
                .asyncNetworkRequest()
                .doFinally { progressLiveData.value = false }
                .subscribe(object : NikeSingleObserver<CartResponse>(compositeDisposable) {
                    override fun onSuccess(t: CartResponse) {
                        if (t.cart_items.isNotEmpty()) {
                            cartItemLiveData.value = t.cart_items
                            purchaseDetailLiveData.value =
                                PurchaseDetail(t.total_price, t.shipping_cost, t.payable_price)

                        }
                    }

                })
    }

    fun removeItemFromCart(cartItem: CartItem): Completable =
        cartRepository.remove(cartItem.cart_item_id)
            .doOnSuccess {
                calculateAndPublishPurchaseDetail()
            }.ignoreElement();


    fun increaseItemFromCart(cartItem: CartItem): Completable =
        cartRepository.changeCount(cartItem.cart_item_id, ++cartItem.count).doOnSuccess {
            calculateAndPublishPurchaseDetail()
        }.ignoreElement()


    fun decreaseItemFromCart(cartItem: CartItem): Completable =
        cartRepository.changeCount(cartItem.cart_item_id, --cartItem.count).doOnSuccess {
            calculateAndPublishPurchaseDetail()
        }.ignoreElement()

    fun refresh() = getCartItems()

    private fun calculateAndPublishPurchaseDetail(){
         cartItemLiveData.value?.let { cartItems->
             purchaseDetailLiveData.value?.let {purchaseDetail->
                 var totalPrice=0
                 var payablePrice=0
                 cartItems.forEach{ cartItem->
                     totalPrice+=cartItem.product.price*cartItem.count
                     payablePrice+=(cartItem.product.price-cartItem.product.discount)*cartItem.count
                 }
                 purchaseDetail.totalPrice=totalPrice
                 purchaseDetail.payable_price=payablePrice
                 purchaseDetailLiveData.postValue(purchaseDetail)
             }
         }
    }
}