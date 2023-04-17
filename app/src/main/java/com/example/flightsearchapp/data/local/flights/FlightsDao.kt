package com.example.flightsearchapp.data.local.flights

import androidx.room.*
import kotlinx.coroutines.flow.Flow
@Dao
interface FlightsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(flights: Flights)

    @Query("DELETE FROM flights")
    suspend fun deleteFlights()

    @Query("SELECT * from flights WHERE departure_code = :departureCode")
    fun getFlights(departureCode: String): Flow<List<Flights>>

}