package com.itzik.hotelapp.ui.theme.project.ui.screens


import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowCircleLeft
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.itzik.hotelapp.ui.theme.project.model.properties.PropertyInfoResponse
import com.itzik.hotelapp.ui.theme.project.utils.getEmptyMockHotel
import com.itzik.hotelapp.ui.theme.project.viewmodels.PropertyViewModel
import kotlinx.coroutines.CoroutineScope

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailsScreen(
    navController: NavHostController,
    propertyViewModel: PropertyViewModel,
    coroutineScope: CoroutineScope,
    propertyInfo: PropertyInfoResponse
) {


    val listState = rememberLazyListState()


    var hotel by remember { mutableStateOf(getEmptyMockHotel()) }
    for (hotelItem in propertyInfo.infoData.hotels) {
        hotel = hotelItem
    }

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    backgroundColor = Color.White,
                    contentColor = Color.Black,

                    title = {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start
                        ) {

                            Icon(
                                imageVector = Icons.Outlined.ArrowCircleLeft,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(28.dp)
                                    .clickable {
                                        var a = 0
                                    }
                            )

                            Text(
                                text = hotel.name,
                                fontSize = 24.sp,
                                fontStyle = FontStyle.Italic,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(start = 20.dp)
                            )
                        }
                    }
                )
            },

            content = {

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    state = listState
                ) {
                    item {
                        Image(
                            painter = rememberAsyncImagePainter(model = propertyInfo.infoData.hotels.first().images.first()),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f),
                            contentScale = ContentScale.Crop,
                        )
//                        Box(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
//                                .padding(8.dp)
//                        ) {
//                            LazyVerticalGrid(
//                                modifier = Modifier
//                                    .fillMaxWidth(),
//                                columns = GridCells.Fixed(4)
//                            ) {
//                                items(propertyInfo.infoData.hotels) { hotel ->
//                                    hotel.images.forEach { imageUrl ->
//                                        val image = rememberAsyncImagePainter(model = imageUrl)
//                                        Image(
//                                            painter = image,
//                                            contentDescription = null,
//                                            modifier = Modifier
//                                                .aspectRatio(1f),
//                                            contentScale = ContentScale.Crop
//                                        )
//                                    }
//                                }
//                            }
//                        }
                    }
                }
            }
        )
    }
}


