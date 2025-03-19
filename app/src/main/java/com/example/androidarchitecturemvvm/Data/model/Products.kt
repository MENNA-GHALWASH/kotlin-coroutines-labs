package com.example.androidarchitecturemvvm.Data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Products")
data class Products(val title: String,
                    val description: String,
                    val price: Double,
                    val rating: Double,
                    val brand: String,
                    val thumbnail: String) {

    @PrimaryKey(autoGenerate = true)
    var id:Int = 0
}

