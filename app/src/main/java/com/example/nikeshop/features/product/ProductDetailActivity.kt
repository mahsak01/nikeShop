package com.example.nikeshop.features.product

import android.content.Intent
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nikeshop.R
import com.example.nikeshop.common.EXTRA_KEY_DATA
import com.example.nikeshop.common.EXTRA_KEY_ID
import com.example.nikeshop.common.NikeActivity
import com.example.nikeshop.common.NikeCompletableObserver
import com.example.nikeshop.data.model.Comment
import com.example.nikeshop.features.product.comment.CommentListActivity
import com.example.nikeshop.service.http.ImageLoadingService
import com.example.nikeshop.view.scroll.ObservableScrollView
import com.example.nikeshop.view.scroll.ObservableScrollViewCallbacks
import com.example.nikeshop.view.scroll.ScrollState
import com.google.android.material.snackbar.Snackbar
import com.sevenlearn.nikestore.common.formatPrice
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_product_detail.*
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.util.ArrayList

class ProductDetailActivity : NikeActivity() {

    val productDetailViewModel: ProductDetailViewModel by viewModel { parametersOf(
        intent.extras) }
    val imageLoadingService: ImageLoadingService by inject()
    val commentAdapter=CommentAdapter()
    val compositeDisposable=CompositeDisposable()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)
        if (supportActionBar != null) {
            supportActionBar?.hide();
        }
        productDetailViewModel.productLiveData.observe(this) {
            imageLoadingService.load(productImageView, it.image)
            productTitleTextView.text = it.title
            previousPriceTextView.text = formatPrice(it.previous_price)
            currentPriceTextView.text = formatPrice(it.price)
            toolbarTitleTextView.text=it.title
            previousPriceTextView.paintFlags= Paint.STRIKE_THRU_TEXT_FLAG

        }

        commentRecyclerView.layoutManager=LinearLayoutManager(this,RecyclerView.VERTICAL,false)
        commentRecyclerView.adapter=commentAdapter

        initViews()

        productDetailViewModel.commentsLiveData.observe(this){
            commentAdapter.comments= it as ArrayList<Comment>
            if (it.size>3)
                viewAllCommentsButtons.visibility= View.VISIBLE
        }

        viewAllCommentsButtons.setOnClickListener{
            startActivity(Intent(this,CommentListActivity::class.java).apply {
                putExtra(EXTRA_KEY_ID, productDetailViewModel.productLiveData.value!!.id)
            })
        }

        backButton.setOnClickListener {
            finish()
        }


addToCartButton.setOnClickListener {
 productDetailViewModel.onAddToCartButton()
     .subscribeOn(Schedulers.io())
     .observeOn(AndroidSchedulers.mainThread())
     .subscribe(object : NikeCompletableObserver(compositeDisposable){
         override fun onComplete() {
             showSnackBar(getString(R.string.successAddToCart))
         }

     })
}




    }
    fun initViews(){

        productImageView.post{
            val productImageViewHeight = productImageView.height
            val toolbarView = toolbarView
            val productImageView= productImageView
            observableScrollView.addScrollViewCallbacks(object : ObservableScrollViewCallbacks {
                override fun onScrollChanged(scrollY: Int, firstScroll: Boolean, dragging: Boolean) {
                    toolbarView.alpha=scrollY.toFloat() / productImageViewHeight.toFloat()
                    productImageView.translationY = scrollY.toFloat() /2
                }

                override fun onDownMotionEvent() {
                }

                override fun onUpOrCancelMotionEvent(scrollState: ScrollState?) {

                }

            })
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

}