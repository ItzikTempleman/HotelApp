package com.itzik.hotelapp.ui.theme.project.ui.screens


import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.itzik.hotelapp.R
import com.itzik.hotelapp.ui.theme.project.model.User
import com.itzik.hotelapp.ui.theme.project.ui.navigation.Graph.AUTHENTICATION
import com.itzik.hotelapp.ui.theme.project.ui.navigation.Graph.HOME
import com.itzik.hotelapp.ui.theme.project.viewmodels.UserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("MutableCollectionMutableState")
@Composable
fun SplashScreen(
    navController: NavHostController,
    coroutineScope: CoroutineScope,
    userViewModel: UserViewModel
) {
    var userList by remember {
        mutableStateOf(mutableListOf<User>())
    }

    LaunchedEffect(key1 = true) {
        delay(1500)
        coroutineScope.launch {
            userViewModel.fetchLoggedInUsers().collect {
                userList = it
            }

            if (userList.isNotEmpty() && userList.first().isLoggedIn) {
                navController.popBackStack()
                navController.navigate(HOME)

            } else {
                navController.popBackStack()
                navController.navigate(AUTHENTICATION)
            }
        }
    }

    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (title, circleProgressBar) = createRefs()

        Text(
            text = stringResource(id = R.string.properties),
            modifier = Modifier
                .constrainAs(title) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
                .padding(8.dp),
            fontSize = 26.sp,
        )

        CircularProgressIndicator(
            modifier = Modifier.constrainAs(circleProgressBar) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            color = colorResource(id = R.color.light_blue)
        )
    }


}