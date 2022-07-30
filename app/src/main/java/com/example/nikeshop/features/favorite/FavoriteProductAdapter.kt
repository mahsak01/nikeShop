package com.example.nikeshop.features.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nikeshop.R
import com.example.nikeshop.data.model.Product
import com.example.nikeshop.service.http.ImageLoadingService
import com.example.nikeshop.view.NikeImageView

class FavoriteProductAdapter(val imageLoadingService: ImageLoadingService,val favoriteProductEventLister: FavoriteProductEventListener,val products: MutableList<Product>):
    RecyclerView.Adapter<FavoriteProductAdapter.ViewHolder>() {

    inner class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val productTitleTextView= itemView.findViewById<TextView>(R.id.productTitleTextView)
        val productImageView= itemView.findViewById<NikeImageView>(R.id.productImageView)
        fun bindProduct(product: Product){
            productTitleTextView.text=product.title
            imageLoadingService.load(productImageView,product.image)
            itemView.setOnClickListener {
                favoriteProductEventLister.onClick(product)
            }
            itemView.setOnLongClickListener {
                products.remove(product)
                notifyItemRemoved(adapterPosition)
                favoriteProductEventLister.onLongClick(product)
                return@setOnLongClickListener false
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_favorite_product,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindProduct(products[position])
    }

    override fun getItemCount(): Int =products.size

    interface FavoriteProductEventListener{
        fun onClick(product: Product)
        fun onLongClick(product: Product)

    }
}