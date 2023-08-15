package com.hamza.domain.domain.mapper

interface ProductMapper<I, O> {
    fun map(input: I?): O
}