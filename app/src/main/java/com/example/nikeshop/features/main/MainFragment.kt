package com.example.nikeshop.features.main


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nikeshop.R
import com.example.nikeshop.common.EXTRA_KEY_DATA
import com.example.nikeshop.common.NikeFragment
import com.example.nikeshop.data.model.Product
import com.example.nikeshop.features.product.ProductDetailActivity
import com.sevenlearn.nikestore.common.convertDpToPixel
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class MainFragment: NikeFragment(),ProductListAdapter.ProductOnClickListener {
    val mainViewModel:MainViewModel by viewModel()
    val productListAdapter:ProductListAdapter by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        latestProducts.layoutManager=LinearLayoutManager(requireContext(),RecyclerView.HORIZONTAL,false)
        latestProducts.adapter=productListAdapter

        productListAdapter.productOnClickListener=this

        mainViewModel.latestProductLiveData.observe(viewLifecycleOwner){
          Timber.i(it.toString())
            productListAdapter.productList=it as ArrayList<Product>
        }

//        mainViewModel.popularProductLiveData.observe(viewLifecycleOwner){
//            Timber.i(it.toString())
//            productListAdapter.productList=it as ArrayList<Product>
//        }

        mainViewModel.progressLiveData.observe(viewLifecycleOwner){
            setProgressIndicator(it)
        }
        mainViewModel.bannerLiveData.observe(viewLifecycleOwner){
            Timber.i(it.toString())
            val bannerSliderAdapter = BannerSliderAdapter(this,it)
            bannerSliderViewPager.adapter = bannerSliderAdapter
            val height=(((bannerSliderViewPager.measuredWidth- convertDpToPixel(32f,requireContext()))*173)/328).toInt()
            val layoutParams=bannerSliderViewPager.layoutParams
            layoutParams.height=height
            bannerSliderViewPager.layoutParams=layoutParams
            dots_indicator.setViewPager2(bannerSliderViewPager)

        }
    }

    override fun onProductClick(product: Product) {
        startActivity(Intent(requireContext(),ProductDetailActivity::class.java).apply {
            putExtra(EXTRA_KEY_DATA,product)
        })
    }
}