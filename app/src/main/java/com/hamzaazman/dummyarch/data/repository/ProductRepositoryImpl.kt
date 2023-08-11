package com.hamzaazman.dummyarch.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.hamzaazman.dummyarch.common.Resource
import com.hamzaazman.dummyarch.data.api.ProductService
import com.hamzaazman.dummyarch.data.model.Product
import com.hamzaazman.dummyarch.data.model.ProductResponse
import com.hamzaazman.dummyarch.data.paging.ProductPagingSource
import com.hamzaazman.dummyarch.di.IoDispatcher
import com.hamzaazman.dummyarch.domain.repository.ProductRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productService: ProductService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
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
    }.flowOn(ioDispatcher)

    override fun getAllProductByPaging(): Flow<PagingData<Product>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = true
            ),
            pagingSourceFactory = {
                ProductPagingSource(productService)
            }
        ).flow
    }

}