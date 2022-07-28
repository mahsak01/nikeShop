package com.example.nikeshop.features.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.nikeshop.R
import com.example.nikeshop.common.EXTRA_KEY_DATA
import com.example.nikeshop.data.model.Banner
import com.example.nikeshop.service.http.ImageLoadingService
import com.example.nikeshop.view.NikeImageView
import org.koin.android.ext.android.inject

class BannerFragment:Fragment() {
    val imageLoadingService:ImageLoadingService by inject()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val imageView= inflater.inflate(R.layout.fragment_banner,container,false) as NikeImageView
        val banner= requireArguments().getParcelable<Banner>(EXTRA_KEY_DATA)?:throw IllegalStateException("image not null")
        imageLoadingService.load(imageView,banner.image)
        return imageView
    }

    companion object{
        fun newInstance(banner: Banner):BannerFragment{
            val bannerFragment=BannerFragment()
            bannerFragment.arguments=Bundle().apply {
                putParcelable(EXTRA_KEY_DATA,banner)
            }
            return bannerFragment
        }
    }
}