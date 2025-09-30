package mx.dev.cmg.android.reference.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import mx.dev.cmg.android.reference.presentation.ui.screens.ShareDialogScreen
import mx.dev.cmg.android.reference.presentation.viewmodel.ShareDialogViewModel

/**
 * Reference App Navigation
 * 
 * Main navigation component for the Reference application.
 * Uses Jetpack Compose Navigation with type-safe routes.
 * Follows clean architecture principles with ViewModel integration.
 */
@Composable
fun ReferenceNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = ShareDialogRoute
    ) {
        composable<ShareDialogRoute> {
            val viewModel: ShareDialogViewModel = hiltViewModel()
            
            ShareDialogScreen(
                onShareClick = {
                    viewModel.handleIntent(mx.dev.cmg.android.reference.presentation.state.ShareDialogIntent.ShareClicked)
                }
            )
        }
    }
}