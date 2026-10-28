package com.example.cappuccinoscreen.data

import com.example.cappuccinoscreen.R

import com.example.cappuccinoscreen.model.Category
import com.example.cappuccinoscreen.model.Product
import com.example.cappuccinoscreen.model.Volume

object DataSource {
    var cappuccino = Product(
        name = "Cappuccino",
        rate = 4.3,
        price = 25.40,
        description = R.string.cappuccino_description,
        image = R.drawable.cup,
        categoryId = 1
    )

    val category = Category(
        id = 1,
        name = "Coffee",
        products = listOf(cappuccino),
        imageId = R.drawable.coffee
    )
}