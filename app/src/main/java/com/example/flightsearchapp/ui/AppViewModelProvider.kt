package com.example.flightsearchapp.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.flightsearchapp.FlightSearchApplication
import com.example.flightsearchapp.ui.flight.SelectedAirportViewModel
import com.example.flightsearchapp.ui.home.HomeViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Инициализация HomeViewModel
        initializer {
            HomeViewModel(flightSearchApplication().container.appRepository)
        }
        // Инициализация FlightViewModel
        initializer {
            SelectedAirportViewModel(
                this.createSavedStateHandle(),
                flightSearchApplication().container.appRepository
            )
        }
    }
}

/**
 * Функция расширения для запросов к объекту [Application] и возвращает экземпляр
 * [FlightSearchApplication].
 */
fun CreationExtras.flightSearchApplication(): FlightSearchApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as FlightSearchApplication)