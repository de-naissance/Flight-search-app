package com.example.flightsearchapp.data.local

import android.content.Context

interface AppContainer {
    val appRepository: AppRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val appRepository: AppRepository by lazy {
        OfflineRepository(
            AppDatabase.getDatabase(context).airportDao(),
            AppDatabase.getDatabase(context).favoriteDao(),
            AppDatabase.getDatabase(context).flightsDao()
        )
    }
}