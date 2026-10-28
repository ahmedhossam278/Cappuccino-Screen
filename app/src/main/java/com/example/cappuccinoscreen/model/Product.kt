package com.example.cappuccinoscreen.model

import androidx.annotation.DrawableRes

data class Product(
    val name: String,
    val rate: Double,
    val price: Double,
    val description: Int,
    @DrawableRes val image: Int,
    val categoryId: Int
)
