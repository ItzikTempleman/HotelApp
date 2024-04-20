package com.itzik.hotelapp.ui.theme.project.ui.screens

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.itzik.hotelapp.ui.theme.project.model.User
import com.itzik.hotelapp.ui.theme.project.model.properties.PropertyInfoResponse
import com.itzik.hotelapp.ui.theme.project.ui.navigation.ScreenContainer
import com.itzik.hotelapp.ui.theme.project.ui.screen_sections.HotelCard
import com.itzik.hotelapp.ui.theme.project.ui.screen_sections.SearchAndDateSelection
import com.itzik.hotelapp.ui.theme.project.utils.getEmptyMockData
import com.itzik.hotelapp.ui.theme.project.utils.getLocationName
import com.itzik.hotelapp.ui.theme.project.viewmodels.PropertyViewModel
import com.itzik.hotelapp.ui.theme.project.viewmodels.UserViewModel
import kotlinx.coroutines.CoroutineScope


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@Composable
fun HomeScreen(
    propertyList: PropertyInfoResponse? = null,
    modifier: Modifier,
    propertyViewModel: PropertyViewModel,
    coroutineScope: CoroutineScope,
    navController: NavHostController,
    userViewModel: UserViewModel,
    user: User
) {
    val listState = rememberLazyListState()
    var countryName by remember { mutableStateOf("") }
    var cityName by remember { mutableStateOf("") }
    var propertyInfo by remember {
        mutableStateOf(getEmptyMockData())
    }

    if (propertyList != null) propertyInfo = propertyList
    var pageLimit by remember {
        mutableIntStateOf(10)
    }
    var sortedLabel by remember {
        mutableStateOf("")
    }

    var isProgressBarVisible by remember { mutableStateOf(false) }

    LaunchedEffect(propertyViewModel.propertyList) {
        propertyViewModel.propertyList.collect {
            propertyInfo = it ?: getEmptyMockData()
        }
    }

    LaunchedEffect(Unit) {
        propertyViewModel.locationNameState.collect { locationName ->
            locationName.let { (city, country) ->
                cityName = city ?: ""
                countryName = country ?: ""
            }
        }
    }


    ConstraintLayout(
        modifier = modifier.fillMaxSize()
    ) {
        val (progressBar) = createRefs()
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            state = listState
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                ) {
                    SearchAndDateSelection(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 40.dp),
                        propertyViewModel = propertyViewModel,
                        coroutineScope = coroutineScope,
                        navController = navController,
                        userViewModel = userViewModel,
                        updatedPropertyInfo = {
                            propertyInfo = it
                            navController.currentBackStackEntry?.savedStateHandle?.set(
                                key = "propertyInfo",
                                value = propertyInfo
                            )
                        },
                        countryName = countryName,
                        cityName = cityName,
                        updateProgressBarState = {
                            isProgressBarVisible = it.value
                        },
                        pageLimit = pageLimit
                    )
                }
            }

            items(propertyInfo.infoData.hotels) { hotelItem ->
                countryName = getLocationName(
                    hotelItem.coordinates.last(),
                    propertyInfo.infoData.hotels.first().coordinates.first()
                ).first
                cityName = getLocationName(
                    hotelItem.coordinates.last(),
                    propertyInfo.infoData.hotels.first().coordinates.first()
                ).second
                LaunchedEffect(Unit) {
                    propertyViewModel.locationNameState.collect { locationName ->
                        Log.d("TAG", "Location name: $locationName")
                        locationName.let { (city, country) ->
                            cityName = city ?: ""
                            countryName = country ?: ""
                        }
                    }
                }
                HotelCard(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .clickable {
                            navController.navigate(ScreenContainer.Details.route)
                        }, hotelItem, userViewModel, user, coroutineScope
                )
            }
        }

        if (isProgressBarVisible) {
            CircularProgressIndicator(
                modifier = Modifier.constrainAs(progressBar) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
                color = Color.Gray
            )
        }
    }

}


