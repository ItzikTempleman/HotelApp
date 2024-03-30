package com.itzik.hotelapp.ui.theme.project.ui.screens


import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.itzik.hotelapp.ui.theme.project.model.properties.PropertyInfoResponse
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


    CollapsingToolbarScaffold(
        modifier = Modifier.fillMaxSize(),
        state = state,
        scrollStrategy = ScrollStrategy.EnterAlways,
        toolbar = {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(330.dp)
            ) {
                val (icon, text) = createRefs()

                Image(
                    painter = rememberAsyncImagePainter(model = propertyInfo.infoData.hotels.first().images.first()),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.Crop,
                )

                IconButton(
                    onClick = {

                    },
                    modifier = Modifier.padding(16.dp)
                        .constrainAs(icon) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                        }
                        .size(36.dp)
                        .zIndex(1f)
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
                        }.padding(16.dp)
                        .zIndex(1f)
                )

            }

        },
        body = {
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                item {
                    Card(
                        modifier = Modifier
                            .height(900.dp)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(30.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.Top
                        ) {
                            LazyVerticalGrid(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight(),
                                columns = GridCells.Fixed(4)
                            ) {
                                items(propertyInfo.infoData.hotels) { hotel ->
                                    hotel.images.forEach { imageUrl ->
                                        val image = rememberAsyncImagePainter(model = imageUrl)
                                        Image(
                                            painter = image,
                                            contentDescription = null,
                                            modifier = Modifier.aspectRatio(1f),
                                            contentScale = ContentScale.Crop
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}
