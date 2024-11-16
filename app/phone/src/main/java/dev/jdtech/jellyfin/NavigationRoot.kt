package dev.jdtech.jellyfin

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.jdtech.jellyfin.presentation.setup.addserver.AddServerScreen
import dev.jdtech.jellyfin.presentation.setup.login.LoginScreen
import dev.jdtech.jellyfin.presentation.setup.servers.ServersScreen
import dev.jdtech.jellyfin.presentation.setup.welcome.WelcomeScreen
import kotlinx.serialization.Serializable

@Serializable
data object WelcomeRoute

@Serializable
data object ServersScreenRoute

@Serializable
data object AddServerRoute

@Serializable
data object LoginRoute

@Composable
fun NavigationRoot(
    navController: NavHostController,
    hasServers: Boolean,
    isLoggedIn: Boolean,
) {
    NavHost(
        navController = navController,
        startDestination = when {
            hasServers && !isLoggedIn -> LoginRoute
            else -> WelcomeRoute
        },
    ) {
        composable<WelcomeRoute> {
            WelcomeScreen(onContinueClick = {
                navController.navigate(ServersScreenRoute)
            })
        }
        composable<ServersScreenRoute> {
            ServersScreen(
                navigateToUsers = {},
                navigateToLogin = {
                    navController.navigate(LoginRoute)
                },
                onAddClick = {
                    navController.navigate(AddServerRoute)
                },
                onBackClick = {
                    navController.popBackStack()
                },
            )
        }
        composable<AddServerRoute> {
            AddServerScreen(
                onSuccess = {
                    navController.navigate(LoginRoute)
                },
                onBackClick = {
                    navController.popBackStack()
                },
            )
        }
        composable<LoginRoute> {
            LoginScreen(
                onSuccess = {},
                onBackClick = {
                    navController.popBackStack()
                },
            )
        }
    }
}