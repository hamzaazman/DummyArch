package com.hamzaazman.trendyolarch.domain.mapper

interface ProductMapper<I, O> {
    fun map(input: I?): O
}