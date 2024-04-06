package com.itzik.hotelapp.ui.theme.project.ui.screen_sections

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Nightlight
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberAsyncImagePainter
import com.itzik.hotelapp.R
import com.itzik.hotelapp.ui.theme.project.model.User
import com.itzik.hotelapp.ui.theme.project.model.properties.Hotel
import com.itzik.hotelapp.ui.theme.project.viewmodels.UserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun HotelCard(
    modifier: Modifier,
    hotel: Hotel,
    userViewModel: UserViewModel,
    user: User,
    coroutineScope: CoroutineScope
) {
    var isPropertyLiked by remember {
        mutableStateOf(false)
    }

    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Transparent)
            .height(120.dp)
            .padding(4.dp),
    ) {
        val image = rememberAsyncImagePainter(model = hotel.images.first())

        val (imageRef) = createRefs()

        Image(
            modifier = Modifier
                .height(90.dp)
                .width(140.dp)
                .constrainAs(imageRef) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                }
                .padding(4.dp)
                .zIndex(3f)
                .clip(RoundedCornerShape(8.dp)),
            painter = image, contentDescription = null,
            contentScale = ContentScale.FillWidth
        )



        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, top = 20.dp),
            colors = CardDefaults.cardColors(
                containerColor = colorResource(id = R.color.white)
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            ),
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(0.5.dp, Color.DarkGray)
        ) {
            ConstraintLayout(
                modifier = modifier.fillMaxSize()
            ) {
                val (propertyNameText, nightIcon, priceText, officialStarsIcon, officialStarValue, likeBtn, bookBtn) = createRefs()

                Text(
                    text = hotel.name,
                    modifier = Modifier
                        .constrainAs(propertyNameText) {
                            start.linkTo(parent.start)
                            top.linkTo(parent.top)
                            end.linkTo(parent.end)
                        }
                        .padding(4.dp)
                )

                Icon(
                    modifier= Modifier
                        .size(22.dp)
                        .constrainAs(nightIcon) {
                            start.linkTo(parent.start)
                            bottom.linkTo(parent.bottom)
                        }
                        .padding(4.dp)
                        .rotate(315f),
                    imageVector = Icons.Filled.Nightlight,
                    tint= colorResource(id = R.color.deep_purple),
                    contentDescription =null
                )

                hotel.price?.let {
                    Text(
                        text = it,
                        modifier = Modifier
                            .constrainAs(priceText) {
                                start.linkTo(nightIcon.end)
                                bottom.linkTo(parent.bottom)
                            }
                            .padding(4.dp),
                        fontSize = 12.sp
                    )
                }

                Icon(
                    modifier= Modifier
                        .constrainAs(officialStarsIcon) {
                            end.linkTo(bookBtn.start)
                            bottom.linkTo(parent.bottom)
                        }
                        .size(46.dp)
                        .padding(4.dp),
                    imageVector = Icons.Default.Star,
                    tint= colorResource(id = R.color.yellow),
                    contentDescription = null
                )

                Text(
                    modifier= Modifier
                        .constrainAs(officialStarValue) {
                            end.linkTo(bookBtn.start)
                            bottom.linkTo(parent.bottom)
                        }
                        .padding(bottom = 10.dp, end = 20.dp),
                    text = hotel.stars.toString(),
                    color = Color.White,
                    fontSize = 18.sp
                )

                IconButton(
                    modifier = Modifier
                        .constrainAs(likeBtn) {
                            end.linkTo(parent.end)
                            bottom.linkTo(bookBtn.top)
                        }
                        .padding(end = 12.dp)
                        .size(30.dp),
                    onClick = {
                        isPropertyLiked = !isPropertyLiked
                        hotel.isLiked = !hotel.isLiked
                        coroutineScope.launch {

                        }
                    }
                ) {
                    Icon(
                        painter = if (!isPropertyLiked) painterResource(id = R.drawable.disliked) else painterResource(
                            id = R.drawable.liked
                        ),
                        tint = Color.Yellow,
                        contentDescription = null
                    )
                }

                Button(
                    modifier = Modifier
                        .width(100.dp)
                        .height(50.dp)
                        .constrainAs(bookBtn) {
                            end.linkTo(likeBtn.end)
                            bottom.linkTo(parent.bottom)
                        }
                        .clip(RoundedCornerShape(4.dp))
                        .padding(8.dp),
                    colors = ButtonDefaults.buttonColors(Color.White),
                    onClick = {

                    }
                ) {
                    Text(
                        text = stringResource(id = R.string.book),
                        fontSize = 16.sp,
                    )
                }
            }
        }
    }
}