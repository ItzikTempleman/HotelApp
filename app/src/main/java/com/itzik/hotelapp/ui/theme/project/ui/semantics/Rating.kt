package com.itzik.hotelapp.ui.theme.project.ui.semantics

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.itzik.hotelapp.R


@Composable
fun Rating(
    modifier: Modifier,
    rating: Double
) {
    Row(modifier = modifier) {
        val filledStars = (rating / 2).toInt()
        val halfStar = rating % 2 >= 1
        repeat(5) {index->

            val starIcon = when {
                index < filledStars -> R.drawable.star
                index == filledStars && halfStar -> R.drawable.star_half
                else -> R.drawable.star_outlined
            }

            Icon(
                painter = painterResource(id = starIcon),
                contentDescription = null,
                modifier = Modifier.padding(1.dp),
                tint= colorResource(id = R.color.yellow)
            )
        }
    }
}

