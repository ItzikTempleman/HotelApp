package com.itzik.hotelapp.ui.theme.project.utils

import android.location.Geocoder
import android.os.Build
import androidx.annotation.RequiresApi
import com.itzik.hotelapp.ui.theme.project.main.BaseApp
import com.itzik.hotelapp.ui.theme.project.model.properties.EntityTypeAndName
import com.itzik.hotelapp.ui.theme.project.model.properties.Hotel
import com.itzik.hotelapp.ui.theme.project.model.properties.InfoData
import com.itzik.hotelapp.ui.theme.project.model.properties.PropertyInfoResponse
import com.itzik.hotelapp.ui.theme.project.model.properties.Rating
import com.itzik.hotelapp.ui.theme.project.model.properties.ReviewSummary
import java.util.Locale


fun getEmptyMockData() = PropertyInfoResponse(
    InfoData(
        hotels = mutableListOf(),
        entity = EntityTypeAndName("", ""),
        localCurrency = "",
    )
)
fun getEmptyMockHotel()= Hotel(
    images= listOf(""),
    distance="",
    rating= Rating(0,"",""),
    stars=0,
    coordinates= listOf(0.0),
    price="",
    name="",
    reviewSummary= ReviewSummary(0,"",""),

)

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun getLocationName(lat: Double, long: Double): Pair<String,String> {
    var cityName = ""
    var countryName = ""
    val geocoder = Geocoder(BaseApp.getInstance(), Locale.getDefault())
    val addressList = geocoder.getFromLocation(lat, long, 1)
    if (addressList != null) {
        cityName=addressList.first().adminArea ?: ""
        countryName = addressList.first().countryName ?: ""
    }
    return Pair(countryName,cityName)
}


