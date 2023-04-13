package com.example.flightsearchapp

import android.app.Application
import com.example.flightsearchapp.data.local.AppContainer
import com.example.flightsearchapp.data.local.AppDataContainer

class FlightSearchApplication : Application() {

    /**
     * Экземпляр AppContainer, используемый остальными классами для получения зависимостей
     */
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}