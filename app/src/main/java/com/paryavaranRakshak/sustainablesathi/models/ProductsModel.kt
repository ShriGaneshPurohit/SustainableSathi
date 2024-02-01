package com.paryavaranRakshak.sustainablesathi.models

data class ProductsModel(
    val name: String,
    val image_link: String,
    val category: String,
    val subCategory: String,
    val description: String,
    val quantity: Int,
    val price: Double
)