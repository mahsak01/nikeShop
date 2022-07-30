package com.example.nikeshop.features.product.comment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nikeshop.R
import com.example.nikeshop.common.EXTRA_KEY_ID
import com.example.nikeshop.common.NikeActivity
import com.example.nikeshop.data.model.Comment
import com.example.nikeshop.features.product.CommentAdapter
import com.example.nikeshop.service.http.ImageLoadingService
import kotlinx.android.synthetic.main.activity_comment_list.*
import kotlinx.android.synthetic.main.activity_product_detail.*
import kotlinx.android.synthetic.main.activity_product_detail.commentRecyclerView
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.util.ArrayList

class CommentListActivity : NikeActivity() {
    val viewModel:CommentListViewModel by viewModel{ parametersOf(intent.extras!!.getInt(EXTRA_KEY_ID))}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment_list)
        if (supportActionBar != null) {
            supportActionBar?.hide();
        }
        val adapter=CommentAdapter(true)
        findViewById<ImageView>(R.id.backButton).setOnClickListener {
            finish()
        }

        viewModel.commentsLiveData.observe(this){
            commentRecyclerView.layoutManager=LinearLayoutManager(this,RecyclerView.VERTICAL,false)
            adapter.comments= it as ArrayList<Comment>
            commentRecyclerView.adapter=adapter

        }



    }
}