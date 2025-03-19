package com.example.androidarchitecturemvvm.Data.Repo

import com.example.androidarchitecturemvvm.Data.model.Products
import kotlinx.coroutines.flow.Flow

interface Repo {
    abstract fun getAllProducts(isOnline: Boolean): Flow<List<Products?>>
    abstract suspend fun addProduct(products: Products): Unit
    abstract suspend fun deleteProduct(product: Products): Flow<Long>

}