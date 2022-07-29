package com.example.nikeshop.features.product

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nikeshop.R
import com.example.nikeshop.data.model.Comment
import com.example.nikeshop.data.model.Product
import com.example.nikeshop.features.main.ProductListAdapter
import java.util.ArrayList

class CommentAdapter(val showAll:Boolean=false):RecyclerView.Adapter<CommentAdapter.ViewHolder>() {

    var comments=ArrayList<Comment>()

        set(value) {
        field=value
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val titleTextView= itemView.findViewById<TextView>(R.id.commentTileTextView)
        val dateTitleView= itemView.findViewById<TextView>(R.id.commentDateTitleView)
        val authorTextView= itemView.findViewById<TextView>(R.id.commentAuthorTextView)
        val contentTextView= itemView.findViewById<TextView>(R.id.commentContentTextView)

        fun bindComment(comment: Comment){
            titleTextView.text=comment.title
            dateTitleView.text=comment.date
            authorTextView.text= comment.author.email
            contentTextView.text=comment.content
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_comment,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindComment(comments[position])
    }

    override fun getItemCount(): Int = if (comments.size>3 && !showAll) 3 else comments.size


}