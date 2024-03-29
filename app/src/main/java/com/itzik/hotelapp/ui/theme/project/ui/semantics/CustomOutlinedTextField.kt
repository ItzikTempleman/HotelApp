package com.itzik.hotelapp.ui.theme.project.ui.semantics

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun CustomOutlinedTextField(
    invokedFunction: (() -> Unit)? = null,
    tint: Color,
    isTrailingIconExist: Boolean = false,
    value: String,
    thisValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier,
    imageVector: ImageVector,
    trailingImage: ImageVector? = null,
    isError: Boolean = false,
    isKeyboardPasswordType: Boolean = false,
    isIconClickableParam: Boolean = false,
    visualTransformation: VisualTransformation,
    isPasswordIconShowing: ((Boolean) -> Unit)? = null,
    isPasswordToggleClicked: Boolean? = null,
    contentColor: Color
) {

    val isIconClickableValue by remember {
        mutableStateOf(isIconClickableParam)
    }
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = {
                thisValueChange(it)
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(
                    text = label,
                    fontSize = 18.sp,
                    color = Color.DarkGray,
                )
            },
            leadingIcon = {
                if (!isIconClickableValue) {
                    Icon(
                        imageVector = imageVector,
                        contentDescription = null,
                        tint = tint
                    )
                } else {
                    IconButton(
                        onClick = {
                            if (isPasswordIconShowing != null) {
                                isPasswordToggleClicked?.let {
                                    isPasswordIconShowing(it)
                                }
                            }
                        }) {
                        Icon(
                            imageVector = imageVector,
                            contentDescription = null,
                            tint = tint
                        )
                    }
                }
            },
            trailingIcon = {
                if (isTrailingIconExist) {
                    IconButton(
                        onClick = {
                            if (invokedFunction != null) {
                                invokedFunction()
                            }
                        }) {
                        if (trailingImage != null) {
                            Icon(
                                imageVector = trailingImage,
                                contentDescription = null,
                                tint = Color.Black,
                            )
                        }
                    }
                }
            },
            singleLine = true,
            visualTransformation = visualTransformation,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.White,
                textColor = contentColor,
                placeholderColor = contentColor,
                unfocusedBorderColor = Color.White,
                backgroundColor = Color.White,
                cursorColor = Color.Black,
            ),
            isError = isError,
            keyboardOptions = if (isKeyboardPasswordType) {
                KeyboardOptions(keyboardType = KeyboardType.Password)
            } else KeyboardOptions(keyboardType = KeyboardType.Text)
        )
    }
}

@Composable
fun CustomTextField(
    placeholder: String,
    invokedFunction: (() -> Unit),
    value: String,
    thisValueChange: (String) -> Unit,
    modifier: Modifier,
    leadingIcon: ImageVector,
    trailingIcon: ImageVector,
    contentColor: Color
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            disabledContainerColor = Color.White,
        ),
    ) {
        TextField(
            textStyle = TextStyle(fontSize = 20.sp),
            value = value,
            onValueChange = {
                thisValueChange(it)
            },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = {
                Icon(
                    imageVector = leadingIcon, contentDescription = null
                )
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        invokedFunction()
                    }
                ) {
                    Icon(
                        imageVector = trailingIcon, contentDescription = null
                    )
                }
            },
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                textColor = contentColor,
                focusedLabelColor = Color.White,
                unfocusedLabelColor = Color.White,
                placeholderColor = contentColor,
                cursorColor = Color.Black,
                focusedIndicatorColor = Color.White,
            ),
            placeholder = {
                Text(
                    text = placeholder,
                    fontSize = 20.sp
                )
            }
        )
    }
}





