package com.example.flightsearchapp.data.local

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AirportDao {
    @Query("SELECT * from airport WHERE id = :id")
    fun getAirport(id: Int): Flow<Airport>
    @Query("SELECT * FROM airport ORDER BY iataCode ASC")
    fun getAllAirport(): Flow<List<Airport>>
}