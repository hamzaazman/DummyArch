package com.hamza.data.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hamzaazman.dummyarch.data.api.ProductService
import com.hamza.data.data.model.Product
import javax.inject.Inject

class ProductPagingSource @Inject constructor(
    private val productService: ProductService
) : PagingSource<Int, Product>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
        val nextPage = params.key ?: 0

        return try {
            val limit = params.loadSize
            val productResponse = productService.getAllProductByPaging(nextPage, limit)

            LoadResult.Page(
                data = productResponse.products,
                prevKey = null,
                nextKey = nextPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Product>): Int? {
        return null
    }
}
