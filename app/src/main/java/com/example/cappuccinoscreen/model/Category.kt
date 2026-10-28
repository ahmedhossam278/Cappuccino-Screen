package com.example.cappuccinoscreen.model

import androidx.annotation.DrawableRes

data class Category(
    val id: Int,
    val name: String,
    val products: List<Product>,
    @DrawableRes val imageId: Int
)
