package com.example.androidarchitecturemvvm.Data.Repo

import ProductsRemoteDataSource
import com.example.androidarchitecturemvvm.Data.local.LocalDataSource
import com.example.androidarchitecturemvvm.Data.model.Products
import com.example.androidarchitecturemvvm.Data.remote.RemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ProductsRepo private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val loclDataSource: LocalDataSource
):Repo {
    override suspend fun deleteProduct(products: Products) {
        loclDataSource.deleteProduct(products) // Deletes product without unnecessary Flow
    }

    companion object{
        private var INSTANCE : Repo? = null
        fun getInstance (remoteDataSource: RemoteDataSource, localDataSource: LocalDataSource):Repo{
            val temp = ProductsRepo(remoteDataSource,localDataSource)
            INSTANCE = temp
            return temp
        }
    }

    override fun getAllProducts(isOnline: Boolean):Flow<List<Products?>> = flow {
        if (isOnline){
            emit(remoteDataSource.getAllProducts())
        }
        else{
            emit(loclDataSource.getAllProducts())
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun addProduct(products: Products) {
        loclDataSource.insertProduct(products)
    }

}