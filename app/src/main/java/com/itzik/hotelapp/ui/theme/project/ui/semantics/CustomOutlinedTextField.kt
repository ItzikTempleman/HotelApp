package com.itzik.hotelapp.ui.theme.project.ui.semantics

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.itzik.hotelapp.R


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
    contentColor:Color
) {

    val isIconClickableValue by remember {
        mutableStateOf(isIconClickableParam)
    }

    OutlinedTextField(
        shape = RoundedCornerShape(60.dp),
        value = value,
        onValueChange = {
            thisValueChange(it)
        },
        modifier = modifier,
        label = {
            Text(
                text = label,
                fontSize = 18.sp,
                color = colorResource(id = R.color.light_turquoise),
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
                            tint = colorResource(id = R.color.light_turquoise),
                        )
                    }
                }
            }
        },
        singleLine = true,
        visualTransformation = visualTransformation,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = tint,
            textColor = contentColor,
            placeholderColor = contentColor,
            unfocusedBorderColor = contentColor,
            backgroundColor = colorResource(id = R.color.white),
            cursorColor = Color.Black,
        ),
        isError = isError,
        keyboardOptions = if (isKeyboardPasswordType) {
            KeyboardOptions(keyboardType = KeyboardType.Password)
        } else KeyboardOptions(keyboardType = KeyboardType.Text)
    )
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
    contentColor:Color
) {
    TextField(
        textStyle = TextStyle(fontSize = 20.sp),
        value = value,
        onValueChange = {
            thisValueChange(it)
        },
        modifier = modifier,
        leadingIcon = {
            Icon(
                modifier = Modifier.scale(scaleY = 1f, scaleX = 0.8f),
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
                    modifier = Modifier.scale(scaleY = 1f, scaleX = 0.8f),
                    imageVector = trailingIcon, contentDescription = null
                )
            }
        },
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = colorResource(id = R.color.white),
            textColor = contentColor,
            placeholderColor = contentColor,
            cursorColor = Color.Black,
            focusedIndicatorColor = Color.White
        ),
        placeholder = {
            Text(
                modifier = Modifier.scale(scaleY = 1f, scaleX = 0.8f),
                text = placeholder,
                fontSize = 20.sp
            )
        }
    )
}





