package com.example.nikeshop.data.model

data class Product(
    val discount: Int,
    val id: Int,
    val image: String,
    val previous_price: Int,
    val price: Int,
    val status: Int,
    val title: String
)