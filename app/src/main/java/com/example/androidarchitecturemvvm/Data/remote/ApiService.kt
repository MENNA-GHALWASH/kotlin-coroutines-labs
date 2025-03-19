package com.example.androidarchitecturemvvm.Data.remote

import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("products")
    suspend fun getProducts(): Response<ProductsList>
}