package com.example.nikeshop.features.main

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOverlay
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nikeshop.R
import com.example.nikeshop.common.VIEW_TYPE_LARGE
import com.example.nikeshop.common.VIEW_TYPE_ROUND
import com.example.nikeshop.common.VIEW_TYPE_SMALL
import com.example.nikeshop.data.model.Product
import com.example.nikeshop.service.http.ImageLoadingService
import com.example.nikeshop.view.NikeImageView
import com.sevenlearn.nikestore.common.formatPrice
import com.sevenlearn.nikestore.common.implementSpringAnimationTrait

class ProductListAdapter(var viewType:Int = VIEW_TYPE_ROUND, val imageLoadingService: ImageLoadingService) :
    RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {

    var productOnClickListener:ProductOnClickListener?=null

    var productList=ArrayList<Product>()
    set(value) {
        field=value
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val currentPriceTextView: TextView = itemView.findViewById(R.id.currentPriceTextView)
        val previousPriceTextView: TextView = itemView.findViewById(R.id.previousPriceTextView)
        val productImageView: NikeImageView = itemView.findViewById(R.id.productImageView)
        val titleTextView: TextView = itemView.findViewById(R.id.productTitleTextView)

        fun bindProduct(product: Product) {
            imageLoadingService.load(productImageView,product.image)
            titleTextView.text=product.title
            currentPriceTextView.text= formatPrice(product.price)
            previousPriceTextView.text= formatPrice(product.previous_price)
            previousPriceTextView.paintFlags= Paint.STRIKE_THRU_TEXT_FLAG
            itemView.implementSpringAnimationTrait()
            itemView.setOnClickListener{
                productOnClickListener?.onProductClick(product)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return viewType
    }


    override fun getItemCount(): Int =productList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutResId=when(viewType){
            VIEW_TYPE_ROUND->R.layout.item_product
            VIEW_TYPE_SMALL ->R.layout.item_product_small
            VIEW_TYPE_LARGE ->R.layout.item_product_large
            else-> throw  IllegalStateException("viewType is not valid")
        }
        return ViewHolder(LayoutInflater.from(parent.context).inflate(layoutResId,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bindProduct(productList[position])

    interface ProductOnClickListener{
        fun onProductClick(product: Product)
    }
    }