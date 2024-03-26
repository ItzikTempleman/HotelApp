package com.itzik.hotelapp.ui.theme.project.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.RateReview
import androidx.compose.material.icons.filled.SortByAlpha
import androidx.compose.material.icons.outlined.AttachMoney
import androidx.compose.material.icons.outlined.RateReview
import androidx.compose.material.icons.outlined.SortByAlpha
import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

val items = listOf(

    NavigationItem(
        title = "Name",
        selectedIcon = Icons.Filled.SortByAlpha,
        unselectedIcon = Icons.Outlined.SortByAlpha
    ),
    NavigationItem(
        title = "Price",
        selectedIcon = Icons.Filled.AttachMoney,
        unselectedIcon = Icons.Outlined.AttachMoney
    ),
    NavigationItem(
        title = "Ratings",
        selectedIcon = Icons.Filled.RateReview,
        unselectedIcon = Icons.Outlined.RateReview
    )
)