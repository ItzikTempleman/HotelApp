package com.itzik.hotelapp.ui.theme.project.ui.screens


import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowCircleLeft
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.itzik.hotelapp.R
import com.itzik.hotelapp.ui.theme.project.model.properties.PropertyInfoResponse
import com.itzik.hotelapp.ui.theme.project.ui.navigation.ScreenContainer
import com.itzik.hotelapp.ui.theme.project.ui.semantics.Rating
import com.itzik.hotelapp.ui.theme.project.utils.getEmptyMockHotel
import com.itzik.hotelapp.ui.theme.project.viewmodels.PropertyViewModel
import kotlinx.coroutines.CoroutineScope
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailsScreen(
    navController: NavHostController,
    propertyViewModel: PropertyViewModel,
    coroutineScope: CoroutineScope,
    propertyInfo: PropertyInfoResponse
) {

    val state = rememberCollapsingToolbarScaffoldState()

    var hotel by remember { mutableStateOf(getEmptyMockHotel()) }

    for (hotelItem in propertyInfo.infoData.hotels) {
        hotel = hotelItem
    }

    val rating = hotel.rating?.value?.toDouble()?.toInt()


    navController.currentBackStackEntry?.savedStateHandle?.set(
        key = "propertyList",
        value = propertyInfo.infoData.hotels
    )

    CollapsingToolbarScaffold(
        modifier = Modifier.fillMaxSize(),
        state = state,
        scrollStrategy = ScrollStrategy.EnterAlways,
        toolbar = {
            ConstraintLayout(
                modifier = Modifier.fillMaxSize()
            ) {
                val (image, icon, text) = createRefs()

                Image(
                    painter = rememberAsyncImagePainter(model = propertyInfo.infoData.hotels.first().images.first()),
                    contentDescription = null,
                    modifier = Modifier
                        .constrainAs(image) {
                            top.linkTo(parent.top)
                        }
                        .fillMaxWidth()
                        .height(330.dp),
                    contentScale = ContentScale.Crop,
                )

                IconButton(
                    onClick = {
                        navController.navigate(ScreenContainer.Home.route)
                    },
                    modifier = Modifier.padding(top=16.dp)
                        .constrainAs(icon) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                        }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ArrowCircleLeft,
                        contentDescription = null,
                    )
                }

                Text(
                    text = hotel.name,
                    fontSize = 28.sp,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .constrainAs(text) {
                            top.linkTo(parent.top)
                            start.linkTo(icon.end)
                        }
                        .padding(16.dp)
                )
            }
        },
        body = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                item {
                    Card(
                        modifier = Modifier
                            .height(900.dp)
                            .fillMaxWidth()
                    ) {
                        ConstraintLayout(
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            val (imageGrid,starRating, ratingText, ratingValue )=createRefs()
                            LazyVerticalGrid(
                                modifier = Modifier.constrainAs(imageGrid){
                                 top.linkTo(parent.top)
                                }.fillMaxWidth()
                                    .wrapContentHeight(),
                                columns = GridCells.Fixed(5)
                            ) {
                                items(propertyInfo.infoData.hotels) { hotel ->
                                    hotel.images.forEach { imageUrl ->
                                        val image = rememberAsyncImagePainter(model = imageUrl)
                                        Image(
                                            painter = image,
                                            contentDescription = null,
                                            modifier = Modifier.aspectRatio(1f).padding(4.dp),
                                            contentScale = ContentScale.Crop
                                        )
                                    }
                                }
                            }

                            hotel.rating?.value?.toDouble()?.let {
                                Rating(
                                    modifier = Modifier
                                        .constrainAs(starRating) {
                                            start.linkTo(parent.start)
                                            top.linkTo(imageGrid.bottom)
                                        }
                                        .padding(horizontal = 4.dp),
                                    rating = it
                                )
                            }

                            Text(
                                text = "${rating.toString()}/10",
                                modifier = Modifier
                                    .constrainAs(ratingValue) {
                                        start.linkTo(starRating.end)
                                        top.linkTo(imageGrid.bottom)
                                    }
                                    .padding(start = 2.dp, bottom = 2.dp),
                                fontSize = 14.sp,
                                color = colorResource(id = R.color.yellow),
                                fontWeight = FontWeight.Bold
                            )

                            Text(
                                text = "(${hotel.rating?.count.toString()} votes)",
                                modifier = Modifier
                                    .constrainAs(ratingText) {
                                        start.linkTo(ratingValue.end)
                                        top.linkTo(imageGrid.bottom)
                                    }
                                    .padding(start = 2.dp, bottom = 2.dp),
                                fontSize = 12.sp,
                            )


                        }
                    }
                }
            }
        }
    )
}
