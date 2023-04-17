package com.example.flightsearchapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.flightsearchapp.data.local.airport.Airport
import com.example.flightsearchapp.data.local.airport.AirportDao
import com.example.flightsearchapp.data.local.favorite.Favorite
import com.example.flightsearchapp.data.local.favorite.FavoriteDao
import com.example.flightsearchapp.data.local.flights.Flights
import com.example.flightsearchapp.data.local.flights.FlightsDao

@Database(
    entities = [
        Airport::class,
        Favorite::class,
        Flights::class
               ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun airportDao(): AirportDao
    abstract fun favoriteDao(): FavoriteDao
    abstract fun flightsDao(): FlightsDao
    companion object {
        @Volatile
        private var Instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // если экземпляр не равен null, верните его, или создайте новый экземпляр базы данных.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, AppDatabase::class.java, "app_database")
                    .createFromAsset("database/flight_search.db")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also {
                        Instance = it
                    }
            }
        }
    }
}