package com.hamza.data.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.hamza.common.common.Resource
import com.hamza.domain.domain.repository.ProductRepository
import com.hamzaazman.dummyarch.data.api.ProductService
import com.hamza.data.data.model.Product
import com.hamza.data.data.model.ProductResponse
import com.hamza.data.data.paging.ProductPagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productService: ProductService,
) : ProductRepository {

    override suspend fun getProducts() = flow<Resource<ProductResponse>> {
        emit(Resource.Loading(null))
        try {
            val response = productService.getAllProduct()
            emit(Resource.Success(response))
        } catch (e: Throwable) {
            emit(Resource.Error(e.fillInStackTrace()))
        }
    }.catch { e ->
        emit(Resource.Error(e.fillInStackTrace()))
    }

    override fun getAllProductByPaging(): Flow<PagingData<Product>> {
        return Pager(
            config = PagingConfig(
                pageSize = 15,
                enablePlaceholders = true
            ),
            pagingSourceFactory = {
                ProductPagingSource(productService)
            }
        ).flow
    }

    override suspend fun getProductBySearch(query: String): Flow<Resource<ProductResponse>> = flow {
        try {
            emit(Resource.Loading())
            val response = productService.getProductBySearch(query)
            emit(Resource.Success(data = response))
        } catch (e: Throwable) {
            emit(Resource.Error(e))
        }
    }.catch { e ->
        emit(Resource.Error(e.fillInStackTrace()))
    }


}