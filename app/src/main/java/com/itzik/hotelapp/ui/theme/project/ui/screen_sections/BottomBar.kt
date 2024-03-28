package com.itzik.hotelapp.ui.theme.project.ui.screen_sections

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.itzik.hotelapp.R
import com.itzik.hotelapp.ui.theme.project.ui.navigation.ScreenContainer
import com.itzik.hotelapp.ui.theme.project.viewmodels.PropertyViewModel


@Composable
fun BottomBar(
    navController: NavHostController,
    propertyViewModel: PropertyViewModel
) {

    var isRowBackgroundGray by remember { mutableStateOf(false) }

    LaunchedEffect(propertyViewModel) {
        propertyViewModel.isDrawerOpened.collect {
            isRowBackgroundGray = it
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                if (isRowBackgroundGray) colorResource(id = R.color.transparent) else colorResource(
                    id = R.color.transparent
                )
            )
    ) {

        val screens = listOf(
            ScreenContainer.Home,
            ScreenContainer.Liked,
            ScreenContainer.Settings,
            ScreenContainer.Profile
        )
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination


        Row(
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth()
                .border(
                    border = BorderStroke(
                        width = 0.3.dp,
                        color = Color.Black
                    ),
                )
                .background(Color.White),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            screens.forEach {
                AddItem(
                    screen = it,
                    currentDestination = currentDestination,
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: ScreenContainer,
    currentDestination: NavDestination?,
    navController: NavHostController,
) {
    val selected = currentDestination?.hierarchy?.any {
        it.route == screen.route
    } == true

    val contentColor= if(selected) colorResource(id = R.color.dark_blue) else Color.Gray

    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(Color.White)
            .clickable {
                navController.navigate(screen.route) {
                    popUpTo(navController.graph.findStartDestination().id)
                    launchSingleTop = true
                }
            }
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            screen.icon?.let {
                Icon(
                    imageVector = it, contentDescription = null,
                    tint = contentColor
                )
            }

            AnimatedVisibility(visible = selected) {
                screen.title?.let {
                    Text(
                        fontWeight = FontWeight.Bold,
                        text = it,
                        color = contentColor
                    )
                }
            }
        }
    }
}