package com.example.flightsearchapp.ui.home

import android.util.Log
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
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flightsearchapp.FlightSearchTopAppBar
import com.example.flightsearchapp.R
import com.example.flightsearchapp.data.local.airport.Airport
import com.example.flightsearchapp.data.local.favorite.Favorite
import com.example.flightsearchapp.ui.navigation.NavigationDestination
import com.example.flightsearchapp.ui.AppViewModelProvider
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
                placeholder = { Text(text = "Название рейса")}
            )
            if(isSearching) {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(8.dp)
                ) {
                    items(airport) { airport ->
                        SearchBox(
                            airport = airport,
                            onItemClick = {
                                navigateToFlightEntry(it.iataCode)
                            }
                        )
                    }
                }
            }

            if (favoriteUiState.favoriteList.isEmpty()) {
                Text(
                    text = "Хрюк-пук",
                    modifier = modifier,
                )
            } else {
                LazyColumn(
                    modifier = modifier,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(
                        items = favoriteUiState.favoriteList,
                        key = { it.id }
                    ) {
                        Divider()
                        ItCard(FavoriteInfo = it,
                        modifier = modifier)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
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
        Divider()
        Text(
            text = "${airport.iataCode} ${airport.name}",
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 8.dp)
        )
    }
}

@Composable
fun InputForm(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        TODO()
    }
}
@Composable
fun ItCard(
    modifier: Modifier = Modifier,
    FavoriteInfo: Favorite
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column {
            Row {
                Text(text = FavoriteInfo.departureCode)
                Spacer(modifier = modifier.width(10.dp))
                Text(text = FavoriteInfo.destinationCode)

            }
        }

    }
}