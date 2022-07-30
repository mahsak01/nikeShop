package com.example.nikeshop.features.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nikeshop.R
import com.example.nikeshop.common.EXTRA_KEY_DATA
import com.example.nikeshop.common.NikeActivity
import com.example.nikeshop.data.model.Product
import com.example.nikeshop.features.product.ProductDetailActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_favorite.*
import kotlinx.android.synthetic.main.view_default_empty_state.*
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel


class FavoriteProductsActivity : NikeActivity(),
    FavoriteProductAdapter.FavoriteProductEventListener {
    val viewModel: FavoriteProductsViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
        if (supportActionBar != null) {
            supportActionBar?.hide();
        }

        helpButton.setOnClickListener {
            Snackbar.make(it,R.string.favorites_help_message,Snackbar.LENGTH_LONG).show()
        }

        viewModel.productsLiveData.observe(this) {
            if (it.isNotEmpty()) {
                favoriteProductsRecyclerView.layoutManager =
                    LinearLayoutManager(this, RecyclerView.VERTICAL, false)
                favoriteProductsRecyclerView.adapter =
                    FavoriteProductAdapter(get(), this, it as MutableList<Product>)

            } else{
                showEmptyState(R.layout.view_default_empty_state)
                emptyStateMessageTv.text=getString(R.string.favorites_empty_state_message)
            }
        }

    }

    override fun onClick(product: Product) {
        startActivity(Intent(this, ProductDetailActivity::class.java).apply {
            putExtra(EXTRA_KEY_DATA, product)
        })
    }

    override fun onLongClick(product: Product) {
        viewModel.removeFromFavorites(product)
    }
}