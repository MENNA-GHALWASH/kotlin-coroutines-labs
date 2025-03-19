package com.example.androidarchitecturemvvm.Data.remote

import com.example.androidarchitecturemvvm.Data.model.Products

interface RemoteDataSource {
    abstract suspend fun getAllProducts(): List<Products?>
}