package com.itzik.hotelapp.ui.theme.project.ui.screen_sections

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Year
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale


@SuppressLint("UnrememberedMutableState")
@Composable
fun DateSelectionScreenSection(
    checkInDate: String,
    checkOutDate: String,
    checkInErrorMessage: String,
    checkOutErrorMessage: String,
    modifier: Modifier,

    updateCheckInDate: (String) -> Unit,
    updateCheckOutDate: (String) -> Unit,

    updateIsCheckInFirstTime: (MutableState<Boolean>)->Unit,
    updateIsCheckOutFirstTime: (MutableState<Boolean>)->Unit
) {
    var mutableCheckInDate by remember {
        mutableStateOf(checkInDate)
    }
    var mutableCheckOutDate by remember {
        mutableStateOf(checkOutDate)
    }

    var isCheckInFirstTimeClicked by remember {
        mutableStateOf(true)
    }

    var isCheckOutFirstTimeClicked by remember {
        mutableStateOf(true)
    }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        DatePickerDialog(
            modifier = Modifier
                .weight(1f)
                .padding(end = 2.dp),
            errorMessage = checkInErrorMessage,
            isSelectionOfDatesAvailableReversed = false,
            insertedDate = {
                mutableCheckInDate = it.value
                updateCheckInDate(mutableCheckInDate)

            },
            isCheckInIcon = true,



            updateIsFirstTimeCheckInSelected = {
                isCheckInFirstTimeClicked=it.value
                updateIsCheckInFirstTime(it)

            },
            updateIsFirstTimeCheckOutSelected = {
                isCheckOutFirstTimeClicked=it.value
                updateIsCheckOutFirstTime(it)
            }
        )

        DatePickerDialog(
            modifier = Modifier
                .weight(1f)
                .padding(start = 2.dp),
            errorMessage = checkOutErrorMessage,
            isSelectionOfDatesAvailableReversed = false,
            insertedDate = {
                mutableCheckOutDate = it.value
                updateCheckOutDate(mutableCheckOutDate)
            },
            isCheckInIcon = false,



            updateIsFirstTimeCheckInSelected = {
                isCheckOutFirstTimeClicked=it.value
                updateIsCheckOutFirstTime(it)
            },
            updateIsFirstTimeCheckOutSelected = {
                isCheckInFirstTimeClicked=it.value
                updateIsCheckInFirstTime(it)
            }
        )
    }
}


const val pattern = "dd/MM/yyyy"
val timeFormat = SimpleDateFormat(pattern, Locale.US)
var today: String = timeFormat.format(Calendar.getInstance().time)
var dateSelected = ""
var age = 0

@SuppressLint("SimpleDateFormat", "SuspiciousIndentation")
fun parseDateToStringFormat(selectedDate: Calendar?): String? {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd")
    return selectedDate?.time?.let { dateFormat.format(it) }
}


fun validateAge(chosenDate: Calendar): Boolean {
    dateSelected = formattedDate(chosenDate)
    age = extractAgeFromDate(dateSelected)
    return age > 9
}

fun maxOutAtAYearAhead(chosenDate: Calendar): Boolean {
    dateSelected = formattedDate(chosenDate)
    val chosenYear = chosenDate.get(Calendar.YEAR)
    val currentYear = Year.now().value
    return chosenYear > currentYear
}

fun formattedDate(calendar: Calendar): String {
    val dateFormat = timeFormat
    return dateFormat.format(calendar.time)
}

fun extractAgeFromDate(dateSelected: String): Int {
    val formatter = DateTimeFormatter.ofPattern(pattern)

    val thisYear = LocalDate.parse(today, formatter).year
    val selectedYear = LocalDate.parse(dateSelected, formatter).year
    val thisDayOfMonth = LocalDate.parse(today, formatter).dayOfMonth
    val selectedDayOfMonth = LocalDate.parse(dateSelected, formatter).dayOfMonth

    var tempAge = thisYear - selectedYear
    if (thisDayOfMonth < selectedDayOfMonth) {
        tempAge--
    }
    return tempAge
}


fun formatDate(date: String): String? {
    val originalDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(date)
    val targetFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return originalDate?.let {
        targetFormat.format(it)
    }
}


fun isValidDateRange(checkInDate: String, checkOutDate: String): Boolean {
    val checkInComponents = checkInDate.split("-")
    val checkOutComponents = checkOutDate.split("-")

    if (checkInComponents.size == 3 && checkOutComponents.size == 3) {

        val checkInYear = checkInComponents[0].toInt()
        val checkInMonth = checkInComponents[1].toInt()
        val checkInDay = checkInComponents[2].toInt()

        val checkOutYear = checkOutComponents[0].toInt()
        val checkOutMonth = checkOutComponents[1].toInt()
        val checkOutDay = checkOutComponents[2].toInt()

        if (checkInYear < checkOutYear) {
            return true
        } else if (checkInYear == checkOutYear) {
            if (checkInMonth < checkOutMonth) {
                return true
            } else if (checkInMonth == checkOutMonth) {
                return checkInDay < checkOutDay
            }
        }
    }
    return false
}
