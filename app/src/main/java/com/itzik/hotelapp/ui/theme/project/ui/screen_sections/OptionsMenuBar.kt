package com.itzik.hotelapp.ui.theme.project.ui.screen_sections

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.itzik.hotelapp.R
import com.itzik.hotelapp.ui.theme.project.ui.navigation.items

@Composable
fun OptionsMenuBar(
    modifier: Modifier,
    onLimitChange: (Int) -> Unit,
    onSortSelect: (String) -> Unit
) {

    var isExpanded by remember {
        mutableStateOf(false)
    }
    val expansionIcon =
        if (isExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown

    var sortOptionsIndexItem by remember { mutableStateOf(items.first()) }


    var isLimitSelectorExpanded by remember {
        mutableStateOf(false)
    }
    val limitExpansionIcon =
        if (isLimitSelectorExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown

    val numberOptionsList by remember { mutableStateOf(listOf(10, 20, 30)) }
    var numberIndexItem by remember { mutableIntStateOf(numberOptionsList.first()) }

    ConstraintLayout(
        modifier = modifier
    ) {
        val (sortResultText, sortResultBox, sortResultDropDownMenu, sortResultIcon, limitResultText, limitResultBox, limitResultDropDownMenu, limitResultIcon) = createRefs()

        Text(
            modifier = Modifier.constrainAs(sortResultText){
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
            }.padding(4.dp),
            text = stringResource(id = R.string.sort_by),
            fontFamily = FontFamily.Monospace,
            fontSize = 16.sp,
        )

        Column(
            modifier = Modifier.constrainAs(sortResultBox){
                top.linkTo(parent.top)
                start.linkTo(sortResultText.end)
            }.padding(4.dp),
        ) {
            DropdownMenu(
                modifier = Modifier.width(100.dp),
                expanded = isExpanded,
                onDismissRequest = {
                    isExpanded = false
                }
            ) {
                items.forEach {
                    DropdownMenuItem(
                        onClick = {
                            sortOptionsIndexItem = it
                            isExpanded = false
                            onSortSelect(sortOptionsIndexItem.title)
                        }
                    ) {
                        Text(text = it.title)
                    }
                }
            }
        }


        Box(
            modifier = Modifier.constrainAs(sortResultDropDownMenu){
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(sortResultBox.end)
            }.height(30.dp).width(70.dp).border(
                    0.7.dp,
                    Color.Black,
                    shape = RoundedCornerShape(8.dp))
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White)
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = sortOptionsIndexItem.title,
                color = Color.Black,
                fontSize = 16.sp
            )
        }

        IconButton(
            modifier = Modifier.constrainAs(sortResultIcon){
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(sortResultDropDownMenu.end)
            }.padding(4.dp),
            onClick = {
                isExpanded = !isExpanded
            }
        ) {
            Icon(
                imageVector = expansionIcon,
                contentDescription = null
            )
        }

        Text(
            modifier = Modifier
                .constrainAs(limitResultText) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(sortResultIcon.end)
                }
                .padding(4.dp),
            text = stringResource(id = R.string.limit_results),
            fontFamily = FontFamily.Monospace,
            fontSize = 16.sp,
        )

        Column(
            modifier = Modifier
                .constrainAs(limitResultDropDownMenu) {
                    start.linkTo(limitResultText.end)
                    top.linkTo(limitResultBox.bottom)
                }
        ) {
            DropdownMenu(
                modifier = Modifier.width(60.dp),
                expanded = isLimitSelectorExpanded,
                onDismissRequest = {
                    isLimitSelectorExpanded = false
                }
            ) {
                numberOptionsList.forEach {
                    DropdownMenuItem(
                        onClick = {
                            numberIndexItem =it
                            isLimitSelectorExpanded = false
                            onLimitChange(numberIndexItem)
                        }
                    ) {
                        Text(text = it.toString())
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .size(30.dp)
                .constrainAs(limitResultBox) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(limitResultDropDownMenu.end)
                }
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White)
                .border(
                    0.7.dp,
                    Color.Black,
                    shape = RoundedCornerShape(8.dp),
                )
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = numberIndexItem.toString(),
                color = Color.Black,
                fontSize = 16.sp
            )
        }


        IconButton(
            modifier = Modifier.constrainAs(limitResultIcon) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(limitResultBox.end)
            },
            onClick = {
                isLimitSelectorExpanded = !isLimitSelectorExpanded
            }
        ) {
            Icon(
                imageVector = limitExpansionIcon,
                contentDescription = null
            )
        }
    }
}