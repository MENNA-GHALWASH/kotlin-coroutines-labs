package com.example.androidarchitecturemvvm.Data.local

import com.example.androidarchitecturemvvm.Data.model.Products

interface LocalDataSource {
    suspend fun  getAllProducts(): List<Products?>
    suspend fun insertProduct(products: Products): Long
    suspend fun deleteProduct(products: Products): Int
}