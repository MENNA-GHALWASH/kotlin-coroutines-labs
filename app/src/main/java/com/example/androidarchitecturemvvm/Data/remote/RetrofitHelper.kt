package com.example.androidarchitecturemvvm.Data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {

    var BASE_URL = "https://dummyjson.com/"

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    val retrofitService : ApiService = retrofit.create(ApiService::class.java)
}