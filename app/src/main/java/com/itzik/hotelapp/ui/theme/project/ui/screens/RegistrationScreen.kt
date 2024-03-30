package com.itzik.hotelapp.ui.theme.project.ui.screens


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.twotone.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.itzik.hotelapp.R
import com.itzik.hotelapp.ui.theme.project.ui.navigation.ScreenContainer
import com.itzik.hotelapp.ui.theme.project.ui.semantics.CustomButton
import com.itzik.hotelapp.ui.theme.project.ui.semantics.CustomOutlinedTextField
import com.itzik.hotelapp.ui.theme.project.viewmodels.UserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun RegistrationScreen(
    coroutineScope: CoroutineScope,
    navController: NavHostController,
    userViewModel: UserViewModel
) {
    val nameText = stringResource(id = R.string.full_name)
    val nameLabelMessage by remember { mutableStateOf(nameText) }
    var name by remember { mutableStateOf("") }
    val nameError by remember { mutableStateOf(false) }

    var createEmail by remember { mutableStateOf("") }
    val createEmailText = stringResource(id = R.string.create_email)
    var createEmailLabelMessage by remember { mutableStateOf(createEmailText) }


    var isNewEmailError by remember { mutableStateOf(false) }
    val createdPasswordText = stringResource(id = R.string.create_password)
    var createPassword by remember { mutableStateOf("") }
    var createPasswordLabelMessage by remember { mutableStateOf(createdPasswordText) }
    var isCreatePasswordError by remember { mutableStateOf(false) }

    var createPhoneNumber by remember { mutableStateOf("") }
    val createPhoneNumberText = stringResource(id = R.string.enter_phone_number)
    val createPhoneNumberLabelMessage by remember { mutableStateOf(createPhoneNumberText) }

    var isCreatedPasswordVisible by remember { mutableStateOf(false) }
    var isButtonEnabled by remember { mutableStateOf(false) }

    fun updateButtonState(name: String, email: String, password: String) {
        isButtonEnabled = name.isNotBlank() && email.isNotBlank() && password.isNotBlank()
    }

    ConstraintLayout(
        modifier = Modifier.fillMaxSize(),
    ) {
        val (title, nameTF, emailTF, passwordTF, phoneNumberTF, signUpBtn) = createRefs()

        Text(
            text = stringResource(id = R.string.registration), modifier = Modifier
                .padding(8.dp)
                .constrainAs(title) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                },
            fontSize = 32.sp,
            color = colorResource(id = R.color.dark_gray),
            fontWeight = FontWeight.Bold
        )

        CustomOutlinedTextField(
            value = name,
            thisValueChange = {
                name = it
                updateButtonState(name, createEmail, createPassword)
            },
            label = nameLabelMessage,
            modifier = Modifier
                .constrainAs(nameTF) {
                    top.linkTo(title.bottom)
                }
                .fillMaxWidth()
                .padding(8.dp),
            imageVector = Icons.Default.Person,
            isError = nameError, visualTransformation = VisualTransformation.None,
            tint = Color.Black,
            contentColor = Color.Black
        )

        CustomOutlinedTextField(
            value = createEmail,
            thisValueChange = {
                createEmail = it
                updateButtonState(name, createEmail, createPassword)
            },
            label = createEmailLabelMessage,
            modifier = Modifier
                .constrainAs(emailTF) {
                    top.linkTo(nameTF.bottom)
                }
                .fillMaxWidth()
                .padding(8.dp),
            imageVector = Icons.Default.Email,
            isError = isNewEmailError,
            visualTransformation = VisualTransformation.None,
            tint = Color.Black,
            contentColor = Color.Black
        )

        CustomOutlinedTextField(
            value = createPassword,
            thisValueChange = {
                createPassword = it
                updateButtonState(name, createEmail, createPassword)
            },
            label = createPasswordLabelMessage,
            modifier = Modifier
                .constrainAs(passwordTF) {
                    top.linkTo(emailTF.bottom)
                }
                .fillMaxWidth()
                .padding(8.dp),
            imageVector = if (isCreatedPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
            isError = isCreatePasswordError,
            isKeyboardPasswordType = true,
            isIconClickableParam = true,
            isPasswordToggleClicked = isCreatedPasswordVisible,
            isPasswordIconShowing = {
                isCreatedPasswordVisible = !isCreatedPasswordVisible
            },
            visualTransformation = if (isCreatedPasswordVisible) VisualTransformation.None
            else PasswordVisualTransformation(),
            tint = Color.Black,
            contentColor = Color.Black
        )

        Card(
            modifier = Modifier
                .padding(8.dp)
                .constrainAs(phoneNumberTF) {
                    top.linkTo(passwordTF.bottom)
                }
                .fillMaxWidth(),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
        ) {
            OutlinedTextField(
                value = createPhoneNumber,
                onValueChange = {
                    createPhoneNumber = it
                },
                label = {
                    Text(
                        text = createPhoneNumberLabelMessage,
                        fontSize = 18.sp,
                        color = Color.DarkGray,
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = Color.White,
                    focusedBorderColor = Color.White,
                    textColor = Color.Black,
                    placeholderColor = Color.Black,
                    unfocusedBorderColor = Color.White,
                    cursorColor = Color.Black,

                    ),
                modifier = Modifier,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.TwoTone.Phone,
                        contentDescription = null,
                        tint = Color.Black
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
            )
        }

        CustomButton(
            text = stringResource(id = R.string.create_user),
            modifier = Modifier
                .constrainAs(signUpBtn) {
                    bottom.linkTo(parent.bottom)
                }.padding(8.dp),
            onButtonClick = {
                if (!userViewModel.isValidEmail(createEmail)) {
                    isNewEmailError = true
                    createEmailLabelMessage = "Invalid username / email format"
                } else {
                    isNewEmailError = false
                    createEmailLabelMessage = createEmailText
                }

                if (!userViewModel.isValidPassword(createPassword)) {
                    isCreatePasswordError = true
                    createPasswordLabelMessage = "Enter symbols of type format X, x, $ , 1"

                } else {
                    isCreatePasswordError = false
                    createPasswordLabelMessage = createdPasswordText
                }

                if (userViewModel.isValidEmail(createEmail) && userViewModel.isValidPassword(
                        createPassword
                    )
                ) {
                    val user = userViewModel.createUser(
                        name,
                        createEmail,
                        createPassword,
                        createPhoneNumber.toLong(),
                        profileImage = ""
                    )
                    coroutineScope.launch {
                        userViewModel.insertUser(user)
                    }
                    navController.navigate(ScreenContainer.Home.route)
                }
            },
            isEnabled = isButtonEnabled,
            fontSize = 20.sp,
            containerColor = colorResource(id = R.color.dark_blue),
            contentColor = colorResource(id = R.color.white),
            roundedShape = 8.dp
        )
    }
}