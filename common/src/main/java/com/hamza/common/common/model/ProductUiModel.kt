package com.hamza.common.common.model

data class ProductUiModel(
    val id: Int,
    val price: Int?,
    val rating: Double?,
    val thumbnail: String?,
    val title: String?,
    val description: String?
)