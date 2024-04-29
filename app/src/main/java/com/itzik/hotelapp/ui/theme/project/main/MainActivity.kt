package com.itzik.hotelapp.ui.theme.project.main

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.itzik.hotelapp.ui.theme.project.ui.navigation.RootNavHost
import com.itzik.hotelapp.ui.theme.project.viewmodels.PropertyViewModel
import com.itzik.hotelapp.ui.theme.project.viewmodels.UserViewModel
import com.itzik.hotelapp.ui.theme.themes.HotelAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var propertyViewModel: PropertyViewModel
    private lateinit var userViewModel: UserViewModel

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            propertyViewModel = viewModel()
            userViewModel = viewModel()


            val coroutineScope: CoroutineScope = rememberCoroutineScope()
            val navController: NavHostController = rememberNavController()
            HotelAppTheme {
                RootNavHost(
                    userViewModel = userViewModel,
                    propertyViewModel = propertyViewModel,
                    coroutineScope = coroutineScope,
                    navController = navController
                )
            }
        }
    }
}