package com.example.flightsearchapp.ui.flight

import android.annotation.SuppressLint
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flightsearchapp.FlightSearchTopAppBar
import com.example.flightsearchapp.R
import com.example.flightsearchapp.data.local.flights.Flights
import com.example.flightsearchapp.ui.AppViewModelProvider
import com.example.flightsearchapp.ui.navigation.NavigationDestination
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
    val coroutineScope = rememberCoroutineScope()

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 6.dp, vertical = 5.dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable {
                coroutineScope.launch(Dispatchers.IO) {
                    viewModel.insertFavorite(flight)
                }
            },
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 6.dp),
        ) {
            Row(
                modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.depart),
                )
                FavoriteIcon(
                    flight = flight,
                    viewModel = viewModel
                )
            }
            Row(modifier.padding(bottom = 8.dp)) {
                Text(
                    text = flight.departureCode,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = viewModel.airportInform(flight.departureCode).collectAsState().value)
            }
            Text(
                text = stringResource(id = R.string.arrive),
                modifier = Modifier.padding(bottom = 7.dp)
            )
            Row(modifier.padding(bottom = 8.dp)) {
                Text(
                    text = flight.destinationCode,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = viewModel.airportInform(flight.destinationCode).collectAsState().value)
            }
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun FavoriteIcon(
    flight: Flights,
    viewModel: SelectedAirportViewModel
) {
    val coroutineScope = rememberCoroutineScope()
    var checked by remember { mutableStateOf<Boolean?>(null) }

    if (checked == null) {
        coroutineScope.launch {
            checked = viewModel.checkAirport(flight.id)
        }
    }
    IconToggleButton(
        checked = checked == true,
        onCheckedChange = {
            coroutineScope.launch(Dispatchers.IO) {
                if (checked == true) {
                    viewModel.deleteFavorite(flight)
                } else {
                    viewModel.insertFavorite(flight)
                }
                checked = null
            }

        }) {
        /** Выбор цвета иконки */
        val tint by animateColorAsState(
            if (checked == true) Color(0xFFFF8006)
            else Color(0xFFB0BEC5)
        )
        /**
         * Возможно, стоит в дальнейшём вынести эту иконку в [res], чтобы не нагружать приложение
         */
        Icon(Icons.Filled.Bookmark, contentDescription = "Bookmark", tint = tint)
    }
}