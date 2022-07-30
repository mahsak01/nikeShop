package com.example.nikeshop.features.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nikeshop.R
import com.example.nikeshop.common.NikeFragment
import com.example.nikeshop.features.auth.AuthActivity
import com.example.nikeshop.features.favorite.FavoriteProductsActivity
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.android.ext.android.inject

class ProfileFragment:NikeFragment() {

    private val profileViewModel:ProfileViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile,container,false)
    }

    override fun onResume() {
        super.onResume()
        checkAuthState()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favoriteProductButton.setOnClickListener {
            startActivity(Intent(requireContext(),FavoriteProductsActivity::class.java))
        }
    }

    private fun checkAuthState(){
         if (profileViewModel.isSignedIn){
             authButton.text=getString(R.string.signOut)
             authButton.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_sign_out,0)
             usernameTextView.text=profileViewModel.username
             authButton.setOnClickListener {
                 profileViewModel.signOut()
                 checkAuthState()
             }
         }else{
             authButton.text=getString(R.string.signIn)
             authButton.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_sign_in,0)
             usernameTextView.text=getString(R.string.guest)
             authButton.setOnClickListener {
                 startActivity(Intent(requireContext(),AuthActivity::class.java))
             }
         }
    }


}