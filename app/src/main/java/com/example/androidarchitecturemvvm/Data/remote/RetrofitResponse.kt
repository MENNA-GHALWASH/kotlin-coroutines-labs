package com.example.androidarchitecturemvvm.Data.remote

import com.example.androidarchitecturemvvm.Data.model.Products
import com.google.gson.annotations.SerializedName

data class ProductsList(
    @SerializedName("products") val productsList: List<Products>
)