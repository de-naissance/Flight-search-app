package com.example.flightsearchapp.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flightsearchapp.data.local.airport.Airport
import com.example.flightsearchapp.data.local.AppRepository
import com.example.flightsearchapp.data.local.flights.Flights
import kotlinx.coroutines.delay
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

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()


    private val _airport = MutableStateFlow(homeUiState.value.airportList)

    val airport = searchText
        .debounce(1000L)
        .onEach { _isSearching.update { true } }
        .combine(_airport) { text, airport ->
            if (text.isBlank()) {
                airport
            } else {
                delay(2000L)
                airport.filter {
                    doesMatchSearchQuery(
                        query = text,
                        iataCode = it.iataCode,
                        name = it.name
                    )
                }
            }
        }
        .onEach { _isSearching.update { false } }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _airport.value
        )

    fun onSearchTextChange(text: String) {
        Log.i("Size_arr", _airport.value.size.toString())
        _searchText.value = text
    }

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

private fun doesMatchSearchQuery(
    query: String,
    iataCode: String,
    name: String
): Boolean {
    val matchingCombinations = listOf(
        "$iataCode$name",
        "$iataCode $name",
        "${iataCode.first()} ${name.first()}",
    )

    return matchingCombinations.any {
        it.contains(query, ignoreCase = true)
    }
}

data class HomeUiState(val airportList: List<Airport> = listOf())