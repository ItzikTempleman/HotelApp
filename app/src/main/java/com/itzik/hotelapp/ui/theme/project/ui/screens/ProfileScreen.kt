package com.itzik.hotelapp.ui.theme.project.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material.icons.outlined.Email
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.itzik.hotelapp.R
import com.itzik.hotelapp.ui.theme.project.model.User
import com.itzik.hotelapp.ui.theme.project.ui.navigation.ScreenContainer
import com.itzik.hotelapp.ui.theme.project.viewmodels.UserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun ProfileScreen(
    modifier: Modifier,
    coroutineScope: CoroutineScope,
    navController: NavHostController,
    userViewModel: UserViewModel,
    user: User
) {


    ConstraintLayout(
        modifier = modifier.fillMaxSize(),
    ) {
        val (icon, name, email, signOut) = createRefs()

        Image(
            imageVector = Icons.Default.Person,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .constrainAs(icon) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
                .padding(start = 24.dp, top = 48.dp)
                .size(80.dp)
                .clip(CircleShape)
                .border(1.dp, Color.Gray, CircleShape)
        )



        Text(
            text = user.userName,
            modifier = Modifier
                .constrainAs(name) {
                    top.linkTo(icon.top)
                    bottom.linkTo(icon.bottom)
                    start.linkTo(icon.end)
                }
                .padding(start = 16.dp),
            color = colorResource(id = R.color.dark_blue),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Row(
            modifier = Modifier
                .constrainAs(email) {
                    top.linkTo(icon.bottom)
                }
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.Email, contentDescription = null, tint = colorResource(
                    id = R.color.dark_blue
                )
            )
            Text(
                modifier= Modifier.padding(start=8.dp),
                color = colorResource(id = R.color.dark_blue),
                text = user.email
            )
        }
        Button(
            modifier = Modifier
                .constrainAs(signOut) {
                    bottom.linkTo(parent.bottom)
                }
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
            onClick = {
                coroutineScope.launch {
                    user.isLoggedIn = false
                    userViewModel.updateIsLoggedIn(user)
                }
                navController.navigate(ScreenContainer.Login.route)
            },
            elevation = ButtonDefaults.elevation(
                defaultElevation = 8.dp
            )
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.PowerSettingsNew,
                    tint = Color.Red,
                    contentDescription = null
                )
                Text(
                    modifier = Modifier.padding(start = 4.dp),
                    text = stringResource(id = R.string.log_out),
                    color = Color.Red
                )
            }
        }
    }
}