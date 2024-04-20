package com.itzik.hotelapp.ui.theme.project.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.itzik.hotelapp.ui.theme.project.model.properties.PropertyInfoResponse
import com.itzik.hotelapp.ui.theme.project.ui.screen_sections.OptionsMenuBar
import com.itzik.hotelapp.ui.theme.project.utils.getEmptyMockData
import com.itzik.hotelapp.ui.theme.project.viewmodels.PropertyViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(
    modifier: Modifier,
    coroutineScope: CoroutineScope,
    navController: NavHostController,
    propertyViewModel: PropertyViewModel,
    propertyList: PropertyInfoResponse? = null,
) {

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


    ConstraintLayout(
        modifier = modifier.fillMaxSize()
    ) {
        val (icon, optionMenu) = createRefs()

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

        OptionsMenuBar(
            modifier = modifier
                .fillMaxWidth()
                .constrainAs(optionMenu) {
                    top.linkTo(icon.bottom)
                },
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