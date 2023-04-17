package com.example.flightsearchapp.data.local.flights

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

interface FlightsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(flights: Flights)

    @Delete
    suspend fun delete(flights: Flights)

    @Query("SELECT * from flights WHERE departure_code = :departure_code")
    fun getFlights(departureCode: String): Flow<List<Flights>>

}