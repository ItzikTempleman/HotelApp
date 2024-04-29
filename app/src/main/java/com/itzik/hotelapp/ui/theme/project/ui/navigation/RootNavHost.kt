package com.itzik.hotelapp.ui.theme.project.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.itzik.hotelapp.ui.theme.project.ui.navigation.Graph.AUTHENTICATION
import com.itzik.hotelapp.ui.theme.project.ui.navigation.Graph.HOME
import com.itzik.hotelapp.ui.theme.project.ui.navigation.Graph.ROOT
import com.itzik.hotelapp.ui.theme.project.ui.screens.LoginScreen
import com.itzik.hotelapp.ui.theme.project.ui.screens.RegistrationScreen
import com.itzik.hotelapp.ui.theme.project.ui.screens.SplashScreen
import com.itzik.hotelapp.ui.theme.project.viewmodels.PropertyViewModel
import com.itzik.hotelapp.ui.theme.project.viewmodels.UserViewModel
import kotlinx.coroutines.CoroutineScope


@RequiresApi(Build.VERSION_CODES.TIRAMISU)

@Composable
fun RootNavHost(
    navController: NavHostController,
    propertyViewModel: PropertyViewModel,
    userViewModel: UserViewModel,
    coroutineScope: CoroutineScope,

) {
    NavHost(
        startDestination = ROOT,
        navController = navController
    ) {
        navigation(
            startDestination = ScreenContainer.Splash.route,
            route = ROOT
        ) {
            composable(route = ScreenContainer.Splash.route) {
                SplashScreen(
                    navController = navController,
                    userViewModel = userViewModel,
                    coroutineScope = coroutineScope
                )
            }
        }

        navigation(
            startDestination = ScreenContainer.Login.route,
            route = AUTHENTICATION
        ) {
            composable(route = ScreenContainer.Login.route) {
                LoginScreen(
                    navController = navController,
                    userViewModel = userViewModel,
                    coroutineScope = coroutineScope
                )
            }

            composable(route = ScreenContainer.Registration.route) {
                RegistrationScreen(
                    navController = navController,
                    userViewModel = userViewModel,
                    coroutineScope = coroutineScope
                )
            }
        }

        navigation(
            startDestination = ScreenContainer.Home.route,
            route = HOME
        ) {
            composable(route = ScreenContainer.Home.route) {
                BottomBarNavHost(

                    paramNavController = navController,
                    userViewModel = userViewModel,
                    propertyViewModel = propertyViewModel,
                    coroutineScope = coroutineScope,
                )
            }
        }
    }
}





