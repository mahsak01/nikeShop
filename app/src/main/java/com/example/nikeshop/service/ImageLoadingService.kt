package com.example.nikeshop.service.http

import com.example.nikeshop.view.NikeImageView

interface ImageLoadingService {
    fun  load(imageView: NikeImageView , imageUrl:String)
}