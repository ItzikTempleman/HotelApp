package com.itzik.hotelapp.ui.theme.project.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Favorite
import androidx.compose.material.icons.twotone.Home
import androidx.compose.material.icons.twotone.Person
import androidx.compose.material.icons.twotone.Settings
import androidx.compose.ui.graphics.vector.ImageVector

object Graph {
    const val ROOT = "ROOT"
    const val AUTHENTICATION = "AUTHENTICATION"
    const val HOME = "HOME"

}

sealed class ScreenContainer(
    val route: String,
    val title: String? = null,
    val icon: ImageVector? = null
) {
    data object Splash : ScreenContainer(route = "splash")
    data object Login : ScreenContainer(route = "login")
    data object Registration : ScreenContainer(route = "registration")
    data object Home : ScreenContainer(route = "home", title = "Home", icon = Icons.TwoTone.Home)

    data object Liked : ScreenContainer(route = "liked", title = "Liked", icon = Icons.TwoTone.Favorite)
    data object Profile : ScreenContainer(route = "profile", title = "Profile", icon = Icons.TwoTone.Person)

    data object Settings : ScreenContainer(route = "settings",title = "Settings", icon = Icons.TwoTone.Settings)
    data object Details : ScreenContainer(route = "details")


}

