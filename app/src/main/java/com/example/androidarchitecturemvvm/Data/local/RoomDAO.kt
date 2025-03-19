package com.example.androidarchitecturemvvm.Data.local
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.androidarchitecturemvvm.Data.model.Products

@Dao
interface RoomDAO {

    @Query("SELECT * FROM Products")
    suspend fun getAll(): List <Products>

    @Insert
    suspend fun insert(prod: Products): Long

    @Update
    suspend fun update(prod: Products)

    @Delete
    suspend fun delete(prod: Products): Int
}