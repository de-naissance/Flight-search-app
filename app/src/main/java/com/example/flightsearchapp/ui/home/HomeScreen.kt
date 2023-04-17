package com.example.flightsearchapp.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flightsearchapp.data.local.airport.Airport
import com.example.flightsearchapp.ui.AppViewModelProvider

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val homeUiState by viewModel.homeUiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = "Пук-хрюк", modifier = modifier.padding(16.dp))
        LazyColumn(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(items = homeUiState.airportList) {item ->
                FlightCard(airportInfo = item, modifier = modifier
                    .padding(16.dp))
            }
        }
    }
}

@Composable
fun FlightCard(
    modifier: Modifier = Modifier,
    airportInfo: Airport
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column {
            Row {
                Text(text = airportInfo.iataCode)
                Spacer(modifier = modifier.width(10.dp))
                Text(text = airportInfo.name)

            }
            Text(text = airportInfo.passengers.toString())
        }

    }
}