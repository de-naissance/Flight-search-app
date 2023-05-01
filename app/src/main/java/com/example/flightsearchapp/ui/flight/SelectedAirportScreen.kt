package com.example.flightsearchapp.ui.flight

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flightsearchapp.FlightSearchTopAppBar
import com.example.flightsearchapp.R
import com.example.flightsearchapp.ui.AppViewModelProvider
import com.example.flightsearchapp.ui.home.HomeDestination
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
    /**
     * На данный момент из-за этого ломается приложение
     */
    viewModel: SelectedAirportViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    Log.e("navigateObject", "${SelectedAirportDestination.route}, ${SelectedAirportDestination.itemIdArg}, ${SelectedAirportDestination.routeWithArgs}")
    //val selectedAirportUiState by viewModel.selectedAirportUiState.collectAsState()

    Scaffold(
        topBar = {
            FlightSearchTopAppBar(
                title = stringResource(SelectedAirportDestination.titleRes),
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        },
    ) {innerPadding ->
        Text(text = "Пук=Хрюк", modifier = Modifier.padding(innerPadding))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {

        }
    }
}