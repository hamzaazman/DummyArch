package com.hamzaazman.trendyolarch.domain.model

data class ProductUiModel(
    val id: Int,
    val price: Int?,
    val rating: Double?,
    val thumbnail: String?,
    val title: String?,
    val description: String?
)