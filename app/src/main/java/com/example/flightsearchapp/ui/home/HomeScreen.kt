package com.example.flightsearchapp.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flightsearchapp.FlightSearchTopAppBar
import com.example.flightsearchapp.R
import com.example.flightsearchapp.data.local.airport.Airport
import com.example.flightsearchapp.data.local.favorite.Favorite
import com.example.flightsearchapp.ui.navigation.NavigationDestination
import com.example.flightsearchapp.ui.AppViewModelProvider
import com.example.flightsearchapp.ui.flight.FavoriteIcon
import com.example.flightsearchapp.ui.flight.SelectedAirportViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object HomeDestination : NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.app_name
}
@Composable
fun HomeScreen(
    navigateToFlightEntry: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState by viewModel.uiState.collectAsState() // Пока не трогать
    val favoriteUiState by viewModel.favoriteUiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val searchText by viewModel.searchText.collectAsState()
    val airport by viewModel.airport.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()

    Scaffold(
        topBar = {
            FlightSearchTopAppBar(
                title = stringResource(HomeDestination.titleRes),
                canNavigateBack = false
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    coroutineScope.launch {
                        viewModel.flightsGeneration()
                    }
                          },
                modifier = modifier
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Generate flights",
                    tint = MaterialTheme.colors.onPrimary
                )
            }
        }
    ) {innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = searchText,
                onValueChange = viewModel::onSearchTextChange,
                label = null,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                singleLine = true,
                placeholder = { Text(text = stringResource(id = R.string.EnterAirport))}
            )
            if(isSearching) {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            } else {
                if (searchText.isEmpty() && favoriteUiState.favoriteList.isNotEmpty()) {
                    SavedFavoriteAirport(
                        modifier = modifier,
                        favoriteUiState = favoriteUiState,
                        viewModel = viewModel
                    )
                } else {
                    SearchArea(
                        navigateToFlightEntry = navigateToFlightEntry,
                        airportList = airport,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
fun SearchArea(
    navigateToFlightEntry: (String) -> Unit,
    airportList: List<Airport>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        items(airportList) { airport ->
            SearchBox(
                airport = airport,
                onItemClick = {
                    navigateToFlightEntry(it.iataCode)
                }
            )
        }
    }
}

@Composable
fun SearchBox(
    airport: Airport,
    onItemClick: (Airport) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onItemClick(airport) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp, horizontal = 10.dp)
        ) {
            Text(
                text = "${airport.iataCode} ",
                fontWeight = FontWeight.Bold
            )
            Text(airport.name)

        }
        Divider()
    }
}
@Composable
fun SavedFavoriteAirport(
    modifier: Modifier = Modifier,
    favoriteUiState: FavoriteUiState,
    viewModel: HomeViewModel
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = favoriteUiState.favoriteList,
            key = { it.id }
        ) {
            ItCard(
                FavoriteInfo = it,
                modifier = modifier,
                viewModel = viewModel
            )
        }
    }
}
@Composable
fun ItCard(
    modifier: Modifier = Modifier,
    FavoriteInfo: Favorite,
    viewModel: HomeViewModel
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 6.dp, vertical = 5.dp)
            .clip(RoundedCornerShape(10.dp)),
        backgroundColor = Color.Transparent,
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 6.dp),
        ) {
            Text(
                text = stringResource(id = R.string.depart), modifier.padding(vertical = 6.dp)
            )
            Row {
                Text(
                    text = FavoriteInfo.departureCode,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = viewModel.airportInform(FavoriteInfo.departureCode).collectAsState().value)
            }
            Text(
                text = stringResource(id = R.string.arrive),
                modifier = modifier.padding(vertical = 6.dp)
            )
            Row(modifier.padding(bottom = 8.dp)) {
                Text(
                    text = FavoriteInfo.destinationCode,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = viewModel.airportInform(FavoriteInfo.destinationCode).collectAsState().value)
            }
        }
    }
}