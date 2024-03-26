package com.itzik.hotelapp.ui.theme.project.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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
fun LoginScreen(
    coroutineScope: CoroutineScope,
    navController: NavHostController,
    userViewModel: UserViewModel
) {
    var email by remember { mutableStateOf("") }
    val emailText = stringResource(id = R.string.email)
    var emailLabelMessage by remember { mutableStateOf(emailText) }
    var isEmailError by remember { mutableStateOf(false) }
    val passwordText = stringResource(id = R.string.password)
    var passwordLabelMessage by remember { mutableStateOf(passwordText) }
    var password by remember { mutableStateOf("") }
    var isPasswordError by remember { mutableStateOf(false) }
    var isPasswordVisible by remember { mutableStateOf(false) }

    var isButtonEnabled by remember {
        mutableStateOf(false)
    }

    fun updateButtonState(email: String, password: String) {
        isButtonEnabled = email.isNotBlank() && password.isNotBlank()
    }

    ConstraintLayout(
        modifier = Modifier.fillMaxSize(),
    ) {
        val (title, emailTF, passwordTF, loginBtn, signUpBtn) = createRefs()

        Text(
            text = stringResource(id = R.string.log_in), modifier = Modifier
                .padding(8.dp)
                .constrainAs(title) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                },
            fontSize = 32.sp,
            color = colorResource(id = R.color.black),
            fontWeight = FontWeight.Bold
        )

        CustomOutlinedTextField(
            value = email,
            thisValueChange = {
                email = it
                updateButtonState(email, password)
            },
            label = emailLabelMessage,
            modifier = Modifier
                .constrainAs(emailTF) {
                    top.linkTo(title.bottom)
                }
                .fillMaxWidth()
                .padding(8.dp),
            imageVector = Icons.Default.Email,
            isError = isEmailError,
            visualTransformation = VisualTransformation.None,
            tint = colorResource(id = R.color.light_turquoise),
            contentColor = colorResource(id = R.color.light_turquoise)
        )

        CustomOutlinedTextField(
            value = password,
            thisValueChange = {
                password = it
                updateButtonState(email, password)
            },
            label = passwordLabelMessage,
            modifier = Modifier
                .constrainAs(passwordTF) {
                    top.linkTo(emailTF.bottom)
                }
                .fillMaxWidth()
                .padding(8.dp),
            imageVector = if (isPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
            isError = isPasswordError,
            isKeyboardPasswordType = true,
            isIconClickableParam = true,
            isPasswordToggleClicked = isPasswordVisible,
            isPasswordIconShowing = {
                isPasswordVisible = !isPasswordVisible
            },
            visualTransformation = if (isPasswordVisible) VisualTransformation.None
            else PasswordVisualTransformation(),
            tint = colorResource(id = R.color.light_turquoise),
            contentColor = colorResource(id = R.color.light_turquoise)
        )

        CustomButton(
            text = stringResource(id = R.string.log_in),
            modifier = Modifier
                .constrainAs(loginBtn) {
                    bottom.linkTo(signUpBtn.top)
                },
            onButtonClick = {
                if (!userViewModel.isValidEmail(email)) {
                    isEmailError = true
                    emailLabelMessage = "Invalid username / email format"
                } else {
                    isEmailError = false
                    emailLabelMessage = emailText
                }
                if (!userViewModel.isValidPassword(password)) {
                    isPasswordError = true
                    passwordLabelMessage = "Enter symbols of type format X, x, $ , 1"
                } else {
                    isPasswordError = false
                    passwordLabelMessage = passwordText
                }
                if (userViewModel.isValidEmail(email) && userViewModel.isValidPassword(password)) {

                    coroutineScope.launch {
                        userViewModel.getUserFromUserNameAndPassword(email, password).collect {
                            if (it != null) {
                                it.isLoggedIn = true
                                userViewModel.updateIsLoggedIn(it)
                                navController.popBackStack()
                                navController.navigate(ScreenContainer.Home.route)
                            }
                        }
                    }
                }
            },
            isEnabled = isButtonEnabled,
            fontSize = 22.sp,
            containerColor = colorResource(id = R.color.light_turquoise),
            contentColor = colorResource(id = R.color.white),
            roundedShape=60.dp
        )

        TextButton(
            onClick = {
                navController.navigate(ScreenContainer.Registration.route)
            },
            modifier = Modifier
                .constrainAs(signUpBtn) {
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                }
                .padding(8.dp)
        ) {
            Text(
                text = stringResource(id = R.string.register),
                color = colorResource(id = R.color.light_turquoise),
                fontSize = 23.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}