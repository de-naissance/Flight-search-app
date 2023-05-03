package com.example.flightsearchapp.ui.flight

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flightsearchapp.data.local.AppRepository
import com.example.flightsearchapp.data.local.favorite.Favorite
import com.example.flightsearchapp.data.local.flights.Flights
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class SelectedAirportViewModel (
    savedStateHandle: SavedStateHandle,
    private val appRepository: AppRepository
) : ViewModel(){

    val departureCode: String =
        checkNotNull(savedStateHandle[SelectedAirportDestination.itemIdArg])

    /**
     * Запросить все рейсы
     */
    val selectedAirportUiState: StateFlow<SelectedAirportUiState> =
        appRepository.getFlights(departureCode)
            .filterNotNull()
            .map { SelectedAirportUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = SelectedAirportUiState()
            )

    /**
     * Запросить название аэропорта
     */
   fun airportInform(
       iataCode: String
   ): StateFlow<String> {
       return appRepository.getAirportStream(iataCode)
           .map { it.name }
           .stateIn(
               scope = viewModelScope,
               started = SharingStarted.Lazily,
               initialValue = ""
           )
   }

    suspend fun insertFavorite(
        flights: Flights
    ) {
        appRepository.insertFavorite(
            flights.toFavorite()
        )
    }

    suspend fun deleteFavorite() {
        appRepository
    }
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class SelectedAirportUiState(val selectedAirportList: List<Flights> = listOf())

fun Flights.toFavorite(): Favorite = Favorite(
    id = id,
    departureCode = departureCode,
    destinationCode = destinationCode
)