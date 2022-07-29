package com.example.nikeshop.features.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.nikeshop.R

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        if (supportActionBar != null) {
            supportActionBar?.hide();
        }

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer,LoginFragment())
        }.commit()
    }
}