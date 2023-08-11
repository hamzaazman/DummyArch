package com.hamzaazman.trendyolarch.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.hamzaazman.trendyolarch.common.Resource
import com.hamzaazman.trendyolarch.data.api.ProductService
import com.hamzaazman.trendyolarch.data.model.Product
import com.hamzaazman.trendyolarch.data.model.ProductResponse
import com.hamzaazman.trendyolarch.data.paging.ProductPagingSource
import com.hamzaazman.trendyolarch.di.IoDispatcher
import com.hamzaazman.trendyolarch.domain.source.RemoteDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val remote: RemoteDataSource,
    private val productService: ProductService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) {

    fun getProducts() = flow<Resource<ProductResponse>> {
        emit(Resource.Loading(null))
        try {
            val response = remote.getProducts()
            emit(Resource.Success(response))
        } catch (e: Throwable) {
            emit(Resource.Error(e.fillInStackTrace()))
        }
    }.catch { e ->
        emit(Resource.Error(e.fillInStackTrace()))
    }.flowOn(ioDispatcher)

    fun getAllProductByPaging(): Flow<PagingData<Product>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20, // Sayfa başına kaç ürün alınacağı
                enablePlaceholders = false // Yer tutucu kullanımını devre dışı bırakma
            ),
            pagingSourceFactory = {
                ProductPagingSource(productService)
            }
        ).flow
    }

}