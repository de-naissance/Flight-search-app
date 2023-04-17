package com.example.flightsearchapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flightsearchapp.data.local.airport.Airport
import com.example.flightsearchapp.data.local.AppRepository
import com.example.flightsearchapp.data.local.flights.Flights
import kotlinx.coroutines.flow.*
import kotlin.random.Random

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
     * Очищает, а затем заполняет таблицу случайными рейсами*/
    suspend fun flightsGeneration() {
        appRepository.getAllAirportStream().toList().random()
        appRepository.insertFlights(
            Flights(
                departureCode = "SIUUU",
                destinationCode = "СИУУУУУУУУУУУУ"
            )
        )

    }
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class HomeUiState(val airportList: List<Airport> = listOf())