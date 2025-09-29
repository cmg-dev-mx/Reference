package com.cmgdevmx.reference.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.cmgdevmx.reference.presentation.ui.screens.HomeScreen
import com.cmgdevmx.reference.presentation.ui.screens.SettingsScreen

@Composable
fun ReferenceNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = HomeRoute
    ) {
        composable<HomeRoute> {
            HomeScreen(
                onNavigateToSettings = {
                    navController.navigate(SettingsRoute)
                }
            )
        }
        
        composable<SettingsRoute> {
            SettingsScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}