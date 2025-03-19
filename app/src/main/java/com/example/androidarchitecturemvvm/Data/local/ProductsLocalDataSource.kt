package com.example.androidarchitecturemvvm.Data.local

import com.example.androidarchitecturemvvm.Data.model.Products

class ProductsLocalDataSource(private val dao: RoomDAO):LocalDataSource {


    override suspend fun getAllProducts(): List<Products?> {
        return dao.getAll()
    }

    override suspend fun insertProduct(products: Products): Long {
        return dao.insert(products)
    }

    override suspend fun deleteProduct(products: Products): Int {
        return dao.delete(products)
    }



}