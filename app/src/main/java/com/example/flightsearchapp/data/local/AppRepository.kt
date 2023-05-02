package com.example.flightsearchapp.data.local

import com.example.flightsearchapp.data.local.airport.Airport
import com.example.flightsearchapp.data.local.favorite.Favorite
import com.example.flightsearchapp.data.local.flights.Flights
import kotlinx.coroutines.flow.Flow

interface AppRepository {

    /**
     * Извлеките все элементы из данного источника данных.
     */
    fun getAllAirportStream(): Flow<List<Airport>>

    fun getAllFavoriteStream(): Flow<List<Favorite>>

    fun getFlights(departureCode: String): Flow<List<Flights>>

    /**
     * Извлеките элемент из заданного источника данных, который соответствует [id].
     */
    fun getAirportStream(iataCode: String): Flow<Airport>

    fun getFavoriteStream(id: Int): Flow<Favorite>

    /**
     * Вставить элемент в источник данных
     */
    suspend fun insertFavorite(favorite: Favorite)

    suspend fun insertFlights(flights: Flights)
    /**
     * Удалить элемент из источника данных
     */
    suspend fun deleteFlights()

    suspend fun deleteFavorite(favorite: Favorite)
    /**
     * Обновить элемент в источнике данных
     */
    suspend fun updateFavorite(favorite: Favorite)
}