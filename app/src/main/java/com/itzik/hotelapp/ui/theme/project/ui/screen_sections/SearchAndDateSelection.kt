package com.itzik.hotelapp.ui.theme.project.ui.screen_sections


import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.itzik.hotelapp.R
import com.itzik.hotelapp.ui.theme.project.model.properties.Data
import com.itzik.hotelapp.ui.theme.project.model.properties.PropertyInfoResponse
import com.itzik.hotelapp.ui.theme.project.ui.semantics.CustomButton
import com.itzik.hotelapp.ui.theme.project.ui.semantics.CustomTextField
import com.itzik.hotelapp.ui.theme.project.viewmodels.PropertyViewModel
import com.itzik.hotelapp.ui.theme.project.viewmodels.UserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@SuppressLint(
    "UnrememberedMutableState",
    "MutableCollectionMutableState",
    "SuspiciousIndentation",
    "CoroutineCreationDuringComposition", "UnusedMaterial3ScaffoldPaddingParameter"
)
@Composable
fun SearchAndDateSelection(
    modifier: Modifier,
    propertyViewModel: PropertyViewModel,
    userViewModel: UserViewModel,
    coroutineScope: CoroutineScope,
    navController: NavHostController,
    updatedPropertyInfo: (PropertyInfoResponse) -> Unit,
    countryName: String,
    cityName: String,
    pageLimit: Int,
    updateProgressBarState: (MutableState<Boolean>) -> Unit,
) {

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
        ConstraintLayout(
            modifier = modifier
        ) {
            val (searchTF, datePickerSection, searchBtn, nearByLayout) = createRefs()

            var isValidCheckIn by remember { mutableStateOf(true) }
            var isValidCheckOut by remember { mutableStateOf(true) }
            var isCheckInFirstClicked by remember { mutableStateOf(true) }
            var isCheckOutFirstClicked by remember { mutableStateOf(true) }
            var isExpanded by remember { mutableStateOf(false) }
            val expansionIcon =
                if (isExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown
            var checkInParamDate by remember { mutableStateOf("") }
            var checkOutParamDate by remember { mutableStateOf("") }
            var isSearched by remember { mutableStateOf(false) }
            var isButtonEnabled by remember { mutableStateOf(false) }
            var locationName by remember { mutableStateOf("") }
            var locationQuery by remember { mutableStateOf("") }
            var propertyId by remember { mutableStateOf("") }
            var names by remember { mutableStateOf(listOf<Data>()) }
            val hotelsList by remember { mutableStateOf(mutableListOf<Data>()) }
            var propertyInfo by rememberSaveable { mutableStateOf<PropertyInfoResponse?>(null) }

            var isSearchFieldEmpty by remember { mutableStateOf(false) }
            fun updateButtonState(searchParam: String) {
                isButtonEnabled = searchParam.isNotBlank()
            }


            Column(
                modifier = Modifier
                    .constrainAs(searchTF) {
                        top.linkTo(parent.top)
                    }
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                //TODO FIX CANCEL BUTTON LAYOUT
                if (locationQuery.length > 3) {
                    IconButton(
                        modifier= Modifier.padding(top=40.dp),
                        onClick = {
                        locationQuery=""
                    }) {
                        Icon(imageVector = Icons.Default.Cancel, contentDescription = "")
                    }
                }
                CustomTextField(
                    invokedFunction = {
                        if (locationQuery.isNotBlank()) {
                            isSearchFieldEmpty = false
                            isExpanded = !isExpanded
                            updateProgressBarState(mutableStateOf(true))
                        } else {
                            isSearchFieldEmpty = true
                            return@CustomTextField
                        }
                    },
                    value = locationQuery,
                    thisValueChange = {
                        locationQuery = it
                        updateButtonState(locationQuery)
                    },
                    modifier = Modifier

                        .padding(8.dp)
                        .fillMaxWidth()
                        .zIndex(3f),
                    leadingIcon = Icons.Rounded.Search,
                    trailingIcon = expansionIcon,
                    placeholder = if (isSearchFieldEmpty) stringResource(id = R.string.blank_search) else stringResource(
                        id = R.string.search
                    ),
                    contentColor = Color.Black
                )

                DropdownMenu(
                    modifier = Modifier
                        .fillMaxWidth(),
                    expanded = isExpanded,
                    onDismissRequest = {
                        isExpanded = false
                        updateProgressBarState(mutableStateOf(false))
                    }
                ) {
                    coroutineScope.launch {
                        propertyViewModel.getPropertyIdByQuery(locationQuery)
                            .collect { result ->
                                names = result.data
                                updateProgressBarState(mutableStateOf(false))
                            }
                    }
                    names.forEach { item ->
                        if (item.entityType == "hotel") {
                            hotelsList.add(item)
                        }

                    }
                    Text(
                        text = "Regions found nearby",
                        modifier = Modifier.padding(start = 16.dp)
                    )
                    hotelsList.forEach { hotel ->

                        DropdownMenuItem(
                            onClick = {
                                propertyId = hotel.entityId
                                locationQuery = "${hotel.entityName}, ${hotel.entityType}"
                                locationName = hotel.entityName
                                isExpanded = false
                            }
                        ) {
                            Text(text = hotel.entityName)
                        }
                    }
                }
            }

            DateSelectionScreenSection(
                checkInDate = checkInParamDate,
                checkOutDate = checkOutParamDate,
                modifier = Modifier
                    .constrainAs(datePickerSection) {
                        top.linkTo(searchTF.bottom)
                    },
                updateCheckInDate = { checkIn ->
                    checkInParamDate = checkIn
                    isValidCheckIn = isValidDateRange(checkIn, checkOutParamDate)
                },
                checkInErrorMessage = if (isValidCheckIn && isCheckInFirstClicked) "" else stringResource(
                    id = R.string.check_in_error
                ),
                updateCheckOutDate = { checkOut ->
                    checkOutParamDate = checkOut
                    isValidCheckOut = isValidDateRange(checkInParamDate, checkOut)
                },
                checkOutErrorMessage = if (isValidCheckOut && isCheckOutFirstClicked) "" else stringResource(
                    id = R.string.check_out_error
                ),
                updateIsCheckInFirstTime = {
                    isCheckInFirstClicked = it.value
                },
                updateIsCheckOutFirstTime = {
                    isCheckOutFirstClicked = it.value
                }
            )

            CustomButton(
                text = "Search",
                modifier = Modifier
                    .width(180.dp)
                    .padding(16.dp)
                    .constrainAs(searchBtn) {
                        top.linkTo(datePickerSection.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                onButtonClick = {
                    coroutineScope.launch {
                        updateProgressBarState(mutableStateOf(true))
                        propertyViewModel.getPropertyInfo(
                            propertyId,
                            checkInParamDate,
                            checkOutParamDate,
                            1,
                            1,
                            limit = pageLimit,
                            "USD"
                        ).collect {
                            propertyInfo = it
                            propertyViewModel.updatePropertyList(it)
                            propertyViewModel.updateCityAndCountry(Pair(cityName, countryName))
                            updatedPropertyInfo(propertyInfo!!)
                            updateProgressBarState(mutableStateOf(false))
                            isSearched = true
                        }
                    }
                },
                borderColor = if (isButtonEnabled) Color.DarkGray else Color.Transparent,
                isEnabled = isButtonEnabled,
                fontSize = 16.sp,
                containerColor = if (isButtonEnabled) Color.White else colorResource(id = R.color.very_light_gray),
                contentColor = if (isButtonEnabled) Color.DarkGray else Color.White,
                roundedShape = 12.dp
            )


            if (isSearched) {
                ConstraintLayout(
                    modifier = Modifier
                        .constrainAs(nearByLayout) {
                            top.linkTo(searchBtn.bottom)
                        }
                        .padding(8.dp)
                        .fillMaxWidth()
                ) {
                    val (resultsTitle, selectedDatesTitle, cityAndCountryNameText) = createRefs()
                    Text(
                        modifier = Modifier.constrainAs(resultsTitle) {
                            start.linkTo(parent.start)
                            top.linkTo(parent.top)
                        },
                        text = "Results near $locationName",
                        fontSize = 16.sp,
                        color = Color.DarkGray,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        modifier = Modifier.constrainAs(selectedDatesTitle) {
                            start.linkTo(parent.start)
                            top.linkTo(resultsTitle.bottom)
                        },
                        text = formatDate(checkInParamDate) + " - " + formatDate(
                            checkOutParamDate
                        ) + ",",
                        fontSize = 14.sp,
                        color = Color.DarkGray,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "$cityName, $countryName",
                        modifier = Modifier
                            .padding(start = 4.dp)
                            .constrainAs(cityAndCountryNameText) {
                                start.linkTo(selectedDatesTitle.end)
                                bottom.linkTo(parent.bottom)
                            },
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}