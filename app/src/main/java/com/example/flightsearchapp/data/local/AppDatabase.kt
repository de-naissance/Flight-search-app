package com.example.flightsearchapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        Airport::class,
        Favorite::class
               ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun airportDao(): AirportDao
    abstract fun favoriteDao(): FavoriteDao
    @Volatile
    private var Instance: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        // если экземпляр не равен null, верните его, или создайте новый экземпляр базы данных.
        return Instance ?: synchronized(this) {
            Room.databaseBuilder(context, AppDatabase::class.java, "app_database")
                .build()
                .also { Instance = it }
        }
    }
}