package com.example.nikeshop.features.list

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.nikeshop.R
import com.example.nikeshop.common.*
import com.example.nikeshop.data.model.Product
import com.example.nikeshop.features.main.ProductListAdapter
import com.example.nikeshop.features.product.ProductDetailActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_product_list.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ProductListActivity : NikeActivity(),ProductListAdapter.ProductEventOnClickListener {
    val viewModel:ProductListViewModel by viewModel{ parametersOf(intent.extras!!.getInt(EXTRA_KEY_ID))}
    val productListAdapter:ProductListAdapter by inject{ parametersOf(VIEW_TYPE_SMALL)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)
        if (supportActionBar != null) {
            supportActionBar?.hide();
        }

        productListAdapter.productOnClickListener=this

        val gridLayoutManager=GridLayoutManager(this,2)
        productRecyclerView.layoutManager=gridLayoutManager

        productRecyclerView.adapter=productListAdapter
        viewModel.productLiveData.observe(this){
               productListAdapter.productList= it as ArrayList<Product>
        }
        viewTypeChangerButton.setOnClickListener {
            if (productListAdapter.viewType== VIEW_TYPE_SMALL){
                viewTypeChangerButton.setImageResource(R.drawable.ic_view_type_large)
                gridLayoutManager.spanCount=1
                productListAdapter.viewType= VIEW_TYPE_LARGE
                productListAdapter.notifyDataSetChanged()

            }else{
                viewTypeChangerButton.setImageResource(R.drawable.ic_grid)
                gridLayoutManager.spanCount=2
                productListAdapter.viewType= VIEW_TYPE_SMALL
                productListAdapter.notifyDataSetChanged()

            }

        }

        viewModel.selectedSortTitleLiveData.observe(this){
            selectedSortTitle.text= getString(it)
        }


        sortButton.setOnClickListener {
            val dialog=MaterialAlertDialogBuilder(this)
                .setSingleChoiceItems(R.array.sortArray,viewModel.sort
                ) { dialog, selectedSortIndex ->
                    viewModel.onSelectedSortChangeByUser(selectedSortIndex)
                }.setTitle(getString(R.string.sort))
            dialog.show()
        }

        backButton.setOnClickListener {
            finish()
        }
    }

    override fun onProductClick(product: Product) {
        startActivity(Intent(this,ProductDetailActivity::class.java).apply {
            putExtra(EXTRA_KEY_DATA,product)
        })
    }

    override fun onFavoriteButtonClick(product: Product) {
        TODO("Not yet implemented")
    }
}