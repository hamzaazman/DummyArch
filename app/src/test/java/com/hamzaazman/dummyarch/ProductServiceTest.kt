package com.hamzaazman.dummyarch

import com.hamzaazman.dummyarch.data.api.ProductService
import com.hamzaazman.dummyarch.data.model.ProductResponse
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class ProductServiceTest {

    private lateinit var productService: ProductService

    private val mockResponse: ProductResponse = mock()

    @Before
    fun setUp() {
        productService = mock()
    }

    @Test
    fun testGetAllProduct() {
        runBlocking {
            // Given
            `when`(productService.getAllProduct()).thenReturn(mockResponse)

            // When
            val result = productService.getAllProduct()

            // Then
            assert(result == mockResponse)
        }
    }

    @Test
    fun testGetAllProductByPaging() {
        runBlocking {
            // Given
            val nextPage = 1
            val limit = 10
            `when`(productService.getAllProductByPaging(nextPage, limit)).thenReturn(mockResponse)

            // When
            val result = productService.getAllProductByPaging(nextPage, limit)

            // Then
            assert(result == mockResponse)
        }
    }

    @Test
    fun testGetProductBySearch() {
        runBlocking {
            // Given
            val query = "Laptop"
            `when`(productService.getProductBySearch(query)).thenReturn(mockResponse)

            // When
            val result = productService.getProductBySearch(query)

            // Then
            assert(result == mockResponse)
        }
    }
}