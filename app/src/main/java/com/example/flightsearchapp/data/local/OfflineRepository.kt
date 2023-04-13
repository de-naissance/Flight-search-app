package com.example.flightsearchapp.data.local

import kotlinx.coroutines.flow.Flow

class OfflineRepository(
    private val airportDao: AirportDao,
    private val favoriteDao: FavoriteDao
): AppRepository {
    override fun getAllAirportStream(): Flow<List<Airport>> = airportDao.getAllAirport()

    override fun getAllFavoriteStream(): Flow<List<Favorite>> = favoriteDao.getAllFavorite()

    override fun getAirportStream(id: Int): Flow<Airport> = airportDao.getAirport(id)

    override fun getFavoriteStream(id: Int): Flow<Favorite> = favoriteDao.getAirport(id)

    override suspend fun insertFavorite(favorite: Favorite) = favoriteDao.insert(favorite)

    override suspend fun deleteFavorite(favorite: Favorite) = favoriteDao.delete(favorite)

    override suspend fun updateFavorite(favorite: Favorite) {
        TODO("Not yet implemented")
    }
}