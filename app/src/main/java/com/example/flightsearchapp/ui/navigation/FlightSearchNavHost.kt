package com.example.flightsearchapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.flightsearchapp.ui.flight.SelectedAirportDestination
import com.example.flightsearchapp.ui.flight.SelectedAirportScreen
import com.example.flightsearchapp.ui.home.HomeDestination
import com.example.flightsearchapp.ui.home.HomeScreen

@Composable
fun FlightSearchNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        composable(route = HomeDestination.route) {
            HomeScreen(
                navigateToFlightEntry = { navController.navigate(SelectedAirportDestination.route) }
            )
        }

        composable(route = SelectedAirportDestination.route) {
            SelectedAirportScreen(
                navigateBack = { navController.navigateUp() }
            )
        }
    }
}