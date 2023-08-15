package com.hamza.data.data.model

import com.hamza.data.data.model.Product

data class ProductResponse(
    val limit: Int = 0,
    val products: List<Product> = emptyList(),
    val skip: Int = 0,
    val total: Int = 0
)