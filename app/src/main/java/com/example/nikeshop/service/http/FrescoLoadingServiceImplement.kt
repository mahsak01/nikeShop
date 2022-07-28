package com.example.nikeshop.service.http

import com.example.nikeshop.view.NikeImageView
import com.facebook.drawee.view.SimpleDraweeView

class FrescoLoadingServiceImplement:ImageLoadingService {
    override fun load(imageView: NikeImageView, imageUrl: String) {
        if (imageView is SimpleDraweeView)
            imageView.setImageURI(imageUrl)
        else
            throw  IllegalStateException("ImageView must be instance of SimpleDraweeView")
    }
}