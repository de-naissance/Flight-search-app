package com.example.flightsearchapp.data.local.flights

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 * Здесь буду хранится случайно сгенерированные рейсы
 */
@Entity(tableName = "flights")
data class Flights(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "departure_code")
    val departureCode: String,
    @ColumnInfo(name = "destination_code")
    val  destinationCode: String
)