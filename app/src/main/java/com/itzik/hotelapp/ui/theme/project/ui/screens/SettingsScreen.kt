package com.itzik.hotelapp.ui.theme.project.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope

@Composable
fun SettingsScreen(
    modifier: Modifier,
    coroutineScope: CoroutineScope,
    navController: NavHostController,
) {
    ConstraintLayout(
        modifier = modifier.fillMaxSize()
    ) {
        val (icon) = createRefs()

        Icon(
            imageVector = Icons.TwoTone.Settings,
            contentDescription = null,
            modifier = Modifier
                .padding(start = 4.dp, top = 16.dp)
                .constrainAs(icon) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
                .size(26.dp)
        )

    }
}