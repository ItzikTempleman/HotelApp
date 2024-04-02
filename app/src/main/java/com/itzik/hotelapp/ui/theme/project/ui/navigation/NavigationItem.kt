package com.itzik.hotelapp.ui.theme.project.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.RateReview
import androidx.compose.material.icons.filled.SortByAlpha
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItem(
    val title: String,
    val selectedIcon: ImageVector
)

val items = listOf(

    NavigationItem(
        title = "Name",
        selectedIcon = Icons.Filled.SortByAlpha,
    ),
    NavigationItem(
        title = "Price",
        selectedIcon = Icons.Filled.AttachMoney,
    ),
    NavigationItem(
        title = "Star rating",
        selectedIcon = Icons.Filled.Star,
    ),

    NavigationItem(
        title = "User rating",
        selectedIcon = Icons.Filled.RateReview,
    )

)