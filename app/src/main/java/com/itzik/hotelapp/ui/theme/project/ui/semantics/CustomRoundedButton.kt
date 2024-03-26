package com.itzik.hotelapp.ui.theme.project.ui.semantics

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CustomRoundedButton(
    modifier: Modifier,
    painter: Painter? = null,
    imageVector: ImageVector? = null,
    onClickFunction: () -> Unit,
    outerTint: Color,
    iconTint: Color,
    innerIconColor: Color,
    borderWidth: Dp
) {
    OutlinedButton(
        border = BorderStroke(borderWidth, outerTint),
        shape = CircleShape,
        colors = ButtonDefaults.outlinedButtonColors(backgroundColor = innerIconColor),
        contentPadding = PaddingValues(0.dp),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 4.dp
        ),
        onClick = {
            onClickFunction()
        },
        modifier = modifier
            .size(36.dp),
    ) {
        if (imageVector != null) {
            Icon(
                imageVector = imageVector,
                contentDescription = null,
                tint = iconTint
            )
        } else {
            if (painter != null) {
                Icon(
                    painter = painter, contentDescription = null,
                    tint = Color.Black
                )
            }
        }
    }
}
