package com.example.androidarchitecturemvvm.Data.local
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.androidarchitecturemvvm.Data.model.Products

@Database(entities = [Products::class], version = 1, exportSchema = false)
abstract class RoomHelper : RoomDatabase() {
    abstract fun getProductsDao(): RoomDAO

    companion object {
        @Volatile
        private var INSTANCE: RoomHelper? = null
        fun getInstance(ctx: Context): RoomHelper {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    ctx.applicationContext,
                    RoomHelper::class.java,
                    "product_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
