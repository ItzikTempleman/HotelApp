package com.itzik.hotelapp.ui.theme.project.ui.semantics

import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

fun customShape() = object : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density,
    ): Outline {
        val roundRect = RoundRect(
            0f, 0f, 1000f, 800f,
            CornerRadius(0f),
            CornerRadius(0f),
            CornerRadius(0f),
            CornerRadius(0f)
        )
        return Outline.Rounded(roundRect)

    }
}