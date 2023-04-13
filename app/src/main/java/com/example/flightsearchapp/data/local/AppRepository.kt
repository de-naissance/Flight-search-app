package com.example.flightsearchapp.data.local

import kotlinx.coroutines.flow.Flow

interface AppRepository {

    /**
     * Извлеките все элементы из данного источника данных.
     */
    fun getAllAirportStream(): Flow<List<Airport>>

    fun getAllFavoriteStream(): Flow<List<Favorite>>

    /**
     * Извлеките элемент из заданного источника данных, который соответствует [id].
     */
    fun getAirportStream(id: Int): Flow<Airport>

    fun getFavoriteStream(id: Int): Flow<Favorite>

    /**
     * Вставить элемент в источник данных
     */
    suspend fun insertFavorite(favorite: Favorite)
    /**
     * Удалить элемент из источника данных
     */
    suspend fun deleteFavorite(favorite: Favorite)
    /**
     * Обновить элемент в источнике данных
     */
    suspend fun updateFavorite(favorite: Favorite)
}