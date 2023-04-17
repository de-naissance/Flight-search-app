package com.example.flightsearchapp.data.local.favorite

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favorite: Favorite)

    /**
     * Очищаем таблицу перед заполнением
     */
    @Delete
    suspend fun delete(favorite: Favorite)

    @Query("SELECT * from favorite WHERE id = :id")
    fun getAirport(id: Int): Flow<Favorite>

    @Query("SELECT * FROM favorite ORDER BY id ASC")
    fun getAllFavorite(): Flow<List<Favorite>>
}