package com.example.flightsearchapp.ui.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
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
                navigateToFlightEntry = {
                    navController.navigate("${SelectedAirportDestination.route}/$it")
                    Log.e("navigateFromHome", "${SelectedAirportDestination.route}/$it")
                }
            )
        }

        composable(
            route = SelectedAirportDestination.routeWithArgs,
            arguments = listOf(navArgument(SelectedAirportDestination.itemIdArg) { type = NavType.StringType })
        ) {
            Log.e("navigateInSelectAirRoute", SelectedAirportDestination.routeWithArgs)
            Log.e("navigateInSelectAirItemIdArg", SelectedAirportDestination.itemIdArg)
            SelectedAirportScreen(
                navigateBack = { navController.navigateUp() }
            )
        }
    }
}