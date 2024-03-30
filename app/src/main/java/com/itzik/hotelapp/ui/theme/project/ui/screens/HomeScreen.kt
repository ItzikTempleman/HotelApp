package com.itzik.hotelapp.ui.theme.project.ui.screens

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.twotone.Home
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.itzik.hotelapp.R
import com.itzik.hotelapp.ui.theme.project.model.User
import com.itzik.hotelapp.ui.theme.project.ui.navigation.ScreenContainer
import com.itzik.hotelapp.ui.theme.project.ui.screen_sections.HotelCard
import com.itzik.hotelapp.ui.theme.project.ui.screen_sections.OptionsMenuBar
import com.itzik.hotelapp.ui.theme.project.ui.screen_sections.SearchAndDateSelection
import com.itzik.hotelapp.ui.theme.project.utils.getEmptyMockData
import com.itzik.hotelapp.ui.theme.project.utils.getLocationName
import com.itzik.hotelapp.ui.theme.project.viewmodels.PropertyViewModel
import com.itzik.hotelapp.ui.theme.project.viewmodels.UserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@Composable
fun HomeScreen(
    modifier: Modifier,
    propertyViewModel: PropertyViewModel,
    coroutineScope: CoroutineScope,
    navController: NavHostController,
    userViewModel: UserViewModel,
    user: User
) {
    val toolbarHeight by remember { mutableStateOf(300.dp) }
    val listState = rememberLazyListState()
    var countryName by remember { mutableStateOf("") }
    var cityName by remember { mutableStateOf("") }
    var propertyInfo by remember {
        mutableStateOf(getEmptyMockData())
    }
    var pageLimit by remember {
        mutableIntStateOf(10)
    }
    var sortedLabel by remember {
        mutableStateOf("")
    }

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()

        var isProgressBarVisible by remember { mutableStateOf(false) }
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            ModalNavigationDrawer(
                drawerContent = {

                    ModalDrawerSheet {

                        ConstraintLayout(
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(start = 20.dp, top = 12.dp),
                        ) {
                            val (close) = createRefs()


                            IconButton(
                                onClick = {
                                    scope.launch {
                                        drawerState.close()
                                    }
                                },
                                modifier = Modifier
                                    .constrainAs(close) {
                                        end.linkTo(parent.end)
                                        top.linkTo(parent.top)
                                        bottom.linkTo(parent.bottom)
                                    }
                                    .size(48.dp)
                                    .padding(end = 28.dp)
                            ) {
                                Icon(imageVector = Icons.Default.Close, contentDescription = null)
                            }
                        }

                        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {

                            Row(
                                modifier = modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                OptionsMenuBar(
                                    modifier = modifier.fillMaxWidth(),
                                    onLimitChange = {
                                        pageLimit = it
                                    },
                                    onSortSelect = {
                                        sortedLabel = it
                                        coroutineScope.launch {
                                            propertyViewModel.sortPropertyList(
                                                sortedLabel,
                                                propertyInfo
                                            ).collect {
                                                propertyInfo = it
                                            }
                                        }
                                    }
                                )
                            }
                        }
                    }
                },
                drawerState = drawerState
            ) {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            backgroundColor = Color.White,
                            contentColor = Color.Black,
                            navigationIcon = {
                                IconButton(onClick = {
                                    scope.launch {
                                        drawerState.open()
                                    }
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Menu,
                                        contentDescription = null
                                    )
                                }
                            },
                            title = {
                                ConstraintLayout(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                ) {
                                    val (icon) = createRefs()
                                    Icon(
                                        imageVector = Icons.TwoTone.Home,
                                        contentDescription = null,
                                        modifier = Modifier
                                            .constrainAs(icon) {
                                                top.linkTo(parent.top)
                                                bottom.linkTo(parent.bottom)
                                                end.linkTo(parent.end)
                                            }
                                            .size(28.dp)
                                    )
                                }
                            }
                        )
                    },
                    content = {
                        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                            LazyColumn(
                                modifier = modifier.fillMaxSize().background(Color.White),
                                state = listState
                            ) {
                                item {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(toolbarHeight)

                                    ) {
                                        SearchAndDateSelection(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .padding(top = 70.dp),
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
                                    HotelCard(
                                        modifier = Modifier
                                            .padding(top = 20.dp)
                                            .clickable {
                                                navController.navigate(ScreenContainer.Details.route)
                                            }, hotelItem, userViewModel, user, coroutineScope
                                    )
                                }
                            }

                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                if (isProgressBarVisible) {
                                    CircularProgressIndicator(
                                        modifier = Modifier,
                                        color = colorResource(id = R.color.light_blue)
                                    )
                                }
                            }
                        }
                    }
                )
            }
        }
    }
}

