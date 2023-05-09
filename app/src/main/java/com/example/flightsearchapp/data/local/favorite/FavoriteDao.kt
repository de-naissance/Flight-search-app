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

    @Query("DELETE FROM favorite")
    suspend fun deleteAllFavorite()

    @Query("SELECT * from favorite WHERE id = :id")
    fun getFavoriteFlight(id: Int): Flow<Favorite?>

    @Query("SELECT * FROM favorite ORDER BY id ASC")
    fun getAllFavorite(): Flow<List<Favorite>>
}