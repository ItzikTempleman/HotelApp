package com.itzik.hotelapp.ui.theme.project.ui.navigation

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.itzik.hotelapp.ui.theme.project.model.User
import com.itzik.hotelapp.ui.theme.project.model.properties.PropertyInfoResponse
import com.itzik.hotelapp.ui.theme.project.ui.navigation.Graph.HOME
import com.itzik.hotelapp.ui.theme.project.ui.screen_sections.BottomBar
import com.itzik.hotelapp.ui.theme.project.ui.screens.DetailsScreen
import com.itzik.hotelapp.ui.theme.project.ui.screens.HomeScreen
import com.itzik.hotelapp.ui.theme.project.ui.screens.LikedScreen
import com.itzik.hotelapp.ui.theme.project.ui.screens.ProfileScreen
import com.itzik.hotelapp.ui.theme.project.ui.screens.SettingsScreen
import com.itzik.hotelapp.ui.theme.project.viewmodels.PropertyViewModel
import com.itzik.hotelapp.ui.theme.project.viewmodels.UserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@Composable
fun BottomBarNavHost(
    newNavController: NavHostController = rememberNavController(),
    paramNavController: NavHostController = rememberNavController(),
    propertyViewModel: PropertyViewModel,
    userViewModel: UserViewModel,
    coroutineScope: CoroutineScope,
) {
    var userList by remember {
        mutableStateOf(listOf<User>())
    }

    var user = User(userName = "", email = "", password = "", isItemLiked = false, phoneNumber = 0)




    LaunchedEffect(key1 = true) {
        coroutineScope.launch {
            userViewModel.fetchLoggedInUsers().collect {
                userList = it
            }
        }
    }

    if (userList.isNotEmpty()) {
        user = userList.first()
    }

    Scaffold(
        bottomBar = {
            BottomBar(
                navController = newNavController,
                propertyViewModel = propertyViewModel
            )
        }
    ) {
        NavHost(
            modifier = Modifier.padding(it),
            navController = newNavController,
            startDestination = HOME
        ) {
            navigation(
                route = HOME,
                startDestination = ScreenContainer.Home.route
            ) {

                composable(route = ScreenContainer.Home.route) {
                    val propertyList =
                        newNavController.previousBackStackEntry?.savedStateHandle?.get<PropertyInfoResponse>(
                            "propertyList"
                        )
                    HomeScreen(
                        propertyList = propertyList,
                        modifier = Modifier,
                        userViewModel = userViewModel,
                        propertyViewModel = propertyViewModel,
                        coroutineScope = coroutineScope,
                        navController = newNavController,
                        user = user
                    )
                }

                composable(route = ScreenContainer.Liked.route) {
                    LikedScreen(
                        modifier = Modifier,
                        userViewModel = userViewModel,
                        propertyViewModel = propertyViewModel,
                        coroutineScope = coroutineScope,
                        navController = newNavController,
                        user = user
                    )
                }

                composable(route = ScreenContainer.Settings.route) {
                    SettingsScreen(
                        modifier = Modifier,
                        navController = paramNavController,
                        coroutineScope = coroutineScope,
                        propertyViewModel = propertyViewModel,
                    )
                }

                composable(route = ScreenContainer.Profile.route) {

                    ProfileScreen(
                        modifier = Modifier,
                        navController = paramNavController,
                        userViewModel = userViewModel,
                        coroutineScope = coroutineScope,
                        user = user,

                    )
                }

                composable(route = ScreenContainer.Details.route) {
                    val propertyInfo =
                        newNavController.previousBackStackEntry?.savedStateHandle?.get<PropertyInfoResponse>(
                            "propertyInfo"
                        )
                    if (propertyInfo != null) {
                        DetailsScreen(
                            paramNavController=paramNavController,
                            navController = newNavController,
                            propertyViewModel = propertyViewModel,
                            coroutineScope = coroutineScope,
                            propertyInfo = propertyInfo
                        )
                    }
                }

            }
        }
    }
}

