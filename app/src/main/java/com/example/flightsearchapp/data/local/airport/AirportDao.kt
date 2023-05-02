package com.example.flightsearchapp.data.local.airport

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AirportDao {
    @Query("SELECT * from airport WHERE iata_code = :iataCode")
    fun getAirport(iataCode: String): Flow<Airport>
    @Query("SELECT * FROM airport ORDER BY iata_code ASC")
    fun getAllAirport(): Flow<List<Airport>>
}