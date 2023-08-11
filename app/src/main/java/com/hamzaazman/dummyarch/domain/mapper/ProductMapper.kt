package com.hamzaazman.dummyarch.domain.mapper

interface ProductMapper<I, O> {
    fun map(input: I?): O
}