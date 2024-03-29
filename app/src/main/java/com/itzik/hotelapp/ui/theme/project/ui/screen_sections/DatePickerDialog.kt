package com.itzik.hotelapp.ui.theme.project.ui.screen_sections


import android.annotation.SuppressLint
import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.itzik.hotelapp.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


@SuppressLint("UnrememberedMutableState")
@Composable
fun DatePickerDialog(
    modifier: Modifier,
    errorMessage: String,
    isSelectionOfDatesAvailableReversed: Boolean,
    insertedDate: (MutableState<String>) -> Unit,
    isCheckInIcon: Boolean,
    updateIsFirstTimeCheckInSelected: (MutableState<Boolean>) -> Unit,
    updateIsFirstTimeCheckOutSelected: (MutableState<Boolean>) -> Unit
) {

    var isValidEnteredDate by remember { mutableStateOf(false) }
    val todayDate by remember { mutableStateOf(Calendar.getInstance()) }
    var selectedDate by remember { mutableStateOf<Calendar?>(null) }
    var isDatePickerVisible by remember { mutableStateOf(false) }
    val datePickerDialog: DatePickerDialog?

    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            disabledContainerColor = Color.White,
        )
    ) {
        OutlinedTextField(
            value = SimpleDateFormat(
                "dd/MM/yyyy",
                Locale.US
            ).format(if (selectedDate != null) selectedDate!!.time else todayDate.time),
            onValueChange = {

            },
            enabled = false,
            leadingIcon = {
                IconButton(
                    modifier = Modifier
                        .padding(end = 2.dp)
                        .size(38.dp),
                    onClick = {
                        isDatePickerVisible = true
                    }
                ) {
                    Icon(
                        tint = Color.Black,
                        imageVector = if (isCheckInIcon) Icons.Default.CalendarMonth else Icons.Default.CalendarMonth,
                        contentDescription = null
                    )
                }
            },
            textStyle = TextStyle(
                color = Color.Black,
                fontSize = 18.sp,
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text
            ),
            modifier = Modifier
                .clickable {
                    isDatePickerVisible = true
                },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.White,
                backgroundColor = Color.White
            ),
            isError = isValidEnteredDate,
            label = {
                if (!isValidEnteredDate && selectedDate != null) {
                    Text(
                        text = errorMessage,
                        fontSize = 16.sp,
                        color = Color.Red
                    )
                }
            }
        )
    }


    updateIsFirstTimeCheckInSelected(
        if (selectedDate != null) mutableStateOf(true) else mutableStateOf(false)
    )
    updateIsFirstTimeCheckOutSelected(
        if (selectedDate != null) mutableStateOf(true) else mutableStateOf(false)
    )


    if (isDatePickerVisible) {
        val onDismissDateSelector = { isDatePickerVisible = false }
        val onDateSelected: (Long) -> Unit = {

            selectedDate = Calendar.getInstance().apply {
                timeInMillis = it
            }
            val formattedDate = parseDateToStringFormat(selectedDate).toString()
            insertedDate(mutableStateOf(formattedDate))

            isValidEnteredDate = if (isSelectionOfDatesAvailableReversed) {
                validateAge(selectedDate ?: Calendar.getInstance())
            } else maxOutAtAYearAhead(selectedDate ?: Calendar.getInstance())
            onDismissDateSelector()

        }

        datePickerDialog = DatePickerDialog(
            LocalContext.current,
            R.style.DatePickerTheme,
            { _, year, month, dayOfMonth ->
                val updatedDate = Calendar.getInstance().apply {
                    set(year, month, dayOfMonth)
                }
                onDateSelected(updatedDate.timeInMillis)
            },
            todayDate.get(Calendar.YEAR),
            todayDate.get(Calendar.MONTH),
            todayDate.get(Calendar.DAY_OF_MONTH),
        )
        if (isSelectionOfDatesAvailableReversed) datePickerDialog.datePicker.maxDate =
            System.currentTimeMillis()
        else datePickerDialog.datePicker.minDate = System.currentTimeMillis()
        datePickerDialog.show()
        datePickerDialog.setOnDismissListener {
            isDatePickerVisible = false
        }
    }
}

