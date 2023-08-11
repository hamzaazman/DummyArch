package com.hamzaazman.trendyolarch.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hamzaazman.trendyolarch.data.api.ProductService
import com.hamzaazman.trendyolarch.data.model.Product

class ProductPagingSource(
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
        // We don't need a refresh key since data doesn't change in this source
        return null
    }
}
