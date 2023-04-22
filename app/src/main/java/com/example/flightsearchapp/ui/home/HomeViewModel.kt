package com.example.flightsearchapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flightsearchapp.data.local.airport.Airport
import com.example.flightsearchapp.data.local.AppRepository
import com.example.flightsearchapp.data.local.flights.Flights
import kotlinx.coroutines.flow.*

class HomeViewModel(
    private val appRepository: AppRepository
) : ViewModel() {
    val homeUiState: StateFlow<HomeUiState> =
        appRepository.getAllAirportStream().map { HomeUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = HomeUiState()
            )

    /**
     * Очищает, а затем заполняет таблицу случайными рейсами
     */
    suspend fun flightsGeneration() {
        appRepository.deleteFlights() // Удаление сущестующих рейсов

        val lst = homeUiState.value.airportList // Список айропортов

        /**
         * Достаём не повторяющийся код аэропорта
         */
        fun notRepeatingCode(_departureCode: String): String {
            while (true) {
                val destinationCode = lst.random().iataCode
                if (_departureCode != destinationCode) return destinationCode
            }
        }

        /**
         * Заполняем 100 рейсов в БД [flights]
         */
        repeat(100) {
            val departureCode = lst.random().iataCode
            appRepository.insertFlights(
                Flights(
                    departureCode = departureCode,
                    destinationCode = notRepeatingCode(departureCode)
                )
            )
        }
    }
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}



data class HomeUiState(val airportList: List<Airport> = listOf())