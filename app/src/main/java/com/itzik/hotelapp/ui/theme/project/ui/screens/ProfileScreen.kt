package com.itzik.hotelapp.ui.theme.project.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
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
        val (icon, text, signOut, name, nameValue, email, emailValue, id, idValue) = createRefs()

        Icon(
            imageVector = Icons.Outlined.Person,
            contentDescription = null,
            modifier = Modifier
                .constrainAs(icon) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }

        )
        Text(
            text = stringResource(id = R.string.user_info),
            modifier = Modifier
                .constrainAs(text) {
                    top.linkTo(parent.top)
                    start.linkTo(icon.end)
                }
        )

        TextButton(
            onClick = {
                coroutineScope.launch {
                    user.isLoggedIn = false
                    userViewModel.updateIsLoggedIn(user)
                }
                navController.navigate(ScreenContainer.Login.route)
            },
            modifier = Modifier
                .constrainAs(signOut) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                }
        ) {
            Text(
                text = stringResource(id = R.string.log_out),
            )
        }

        Text(
            text = stringResource(id = R.string.full_name),
            modifier = Modifier
                .constrainAs(name) {
                    top.linkTo(text.bottom)
                    start.linkTo(parent.start)
                }
        )

        Text(
            text = user.userName,
            modifier = Modifier
                .constrainAs(nameValue) {
                    top.linkTo(text.bottom)
                    start.linkTo(name.end)
                }
        )

        Text(
            text = stringResource(id = R.string.user_email),
            modifier = Modifier
                .constrainAs(email) {
                    top.linkTo(name.bottom)
                    start.linkTo(parent.start)
                }
        )

        Text(
            text = user.email,
            modifier = Modifier
                .constrainAs(emailValue) {
                    top.linkTo(name.bottom)
                    start.linkTo(email.end)
                }
        )

        Text(
            text = stringResource(id = R.string.user_id),
            modifier = Modifier
                .constrainAs(id) {
                    top.linkTo(email.bottom)
                    start.linkTo(parent.start)
                }
        )

        Text(
            text = user.id.toString(),
            modifier = Modifier
                .constrainAs(idValue) {
                    top.linkTo(email.bottom)
                    start.linkTo(id.end)
                }
        )
    }
}