package com.example.flightsearchapp.data.local

import com.example.flightsearchapp.data.local.airport.Airport
import com.example.flightsearchapp.data.local.airport.AirportDao
import com.example.flightsearchapp.data.local.favorite.Favorite
import com.example.flightsearchapp.data.local.favorite.FavoriteDao
import com.example.flightsearchapp.data.local.flights.Flights
import com.example.flightsearchapp.data.local.flights.FlightsDao
import kotlinx.coroutines.flow.Flow

class OfflineRepository(
    private val airportDao: AirportDao,
    private val favoriteDao: FavoriteDao,
    private val flightsDao: FlightsDao
): AppRepository {
    override fun getAllAirportStream(): Flow<List<Airport>> = airportDao.getAllAirport()

    override fun getAllFavoriteStream(): Flow<List<Favorite>> = favoriteDao.getAllFavorite()

    override fun getAirportStream(iataCode: String): Flow<Airport> = airportDao.getAirport(iataCode)

    override fun getFavoriteFlight(id: Int): Flow<Favorite?> = favoriteDao.getFavoriteFlight(id)

    override fun getFlights(
        departureCode: String
    ): Flow<List<Flights>> = flightsDao.getFlights(departureCode)

    override suspend fun insertFavorite(favorite: Favorite) = favoriteDao.insert(favorite)

    override suspend fun deleteFavorite(favorite: Favorite) = favoriteDao.delete(favorite)

    override suspend fun insertFlights(flights: Flights) = flightsDao.insert(flights)

    override suspend fun deleteFlights() = flightsDao.deleteFlights()

    override suspend fun deleteAllFavorite() = favoriteDao.deleteAllFavorite()
}