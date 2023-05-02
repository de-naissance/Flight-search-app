package com.example.flightsearchapp.ui.flight

import android.provider.Settings.Global.getString
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flightsearchapp.FlightSearchTopAppBar
import com.example.flightsearchapp.R
import com.example.flightsearchapp.data.local.flights.Flights
import com.example.flightsearchapp.ui.AppViewModelProvider
import com.example.flightsearchapp.ui.flight.SelectedAirportDestination.itemIdArg
import com.example.flightsearchapp.ui.navigation.NavigationDestination

object SelectedAirportDestination : NavigationDestination {
    override val route = "flight"
    override val titleRes = R.string.selectedAirport
    const val itemIdArg = "itemId"
    val routeWithArgs = "$route/{$itemIdArg}"
}

@Composable
fun SelectedAirportScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SelectedAirportViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val selectedAirportUiState by viewModel.selectedAirportUiState.collectAsState()
    val iata = viewModel.airportInform(viewModel.departureCode)
    Scaffold(
        topBar = {
            FlightSearchTopAppBar(
                title = stringResource(SelectedAirportDestination.titleRes),
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        },
    ) {innerPadding ->
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(innerPadding)
        ) {
            Text(
                text = stringResource(R.string.flightsFrom, viewModel.departureCode),
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 8.dp),
                fontWeight = FontWeight.Bold
            )
            LazyColumn(
                modifier = Modifier
            ) {
                items(selectedAirportUiState.selectedAirportList) {
                    CardFlight(
                        flight = it,
                        modifier = modifier,
                        viewModel = viewModel
                    )
                }
            }
        }
        
    }
}

@Composable
fun CardFlight(
    flight: Flights,
    modifier: Modifier = Modifier,
    viewModel: SelectedAirportViewModel
) {
    Column(
        modifier = modifier.fillMaxWidth().padding(horizontal = 10.dp, vertical = 8.dp)
    ) {

        Text(
            text = stringResource(id = R.string.depart),
            modifier = modifier
                .fillMaxWidth().padding(vertical = 8.dp)
        )
        Row {
            Text(
                text = flight.departureCode,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = modifier.width(10.dp))
            Text(text = "Место отбытия")
        }
        Text(
            text = stringResource(id = R.string.arrive),
            modifier = modifier
                .fillMaxWidth().padding(vertical = 8.dp)
        )
        Row {
            Text(
                text = flight.destinationCode,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = modifier.width(10.dp))
            Text(text = "Место прибытия")
        }
    }
}