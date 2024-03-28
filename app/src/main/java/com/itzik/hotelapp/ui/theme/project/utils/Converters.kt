package com.itzik.hotelapp.ui.theme.project.utils

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.itzik.hotelapp.ui.theme.project.model.User
import com.itzik.hotelapp.ui.theme.project.model.properties.Hotel
import com.itzik.hotelapp.ui.theme.project.model.properties.Rating
import com.itzik.hotelapp.ui.theme.project.model.properties.ReviewSummary

@ProvidedTypeConverter
class Converters {
    @TypeConverter
    fun fromUser(user: User): String = Gson().toJson(user)

    @TypeConverter
    fun toUser(userString: String): User = Gson().fromJson(userString, object : TypeToken<User>() {}.type)


//    @TypeConverter
//    fun fromUri(uri: Uri?): String? = uri?.toString()
//
//    @TypeConverter
//    fun toUri(uriString: String?): Uri? = uriString?.let { Uri.parse(it) }



    @TypeConverter
    fun fromHotelList(hotels: MutableList<Hotel>): String = Gson().toJson(hotels)

    @TypeConverter
    fun toHotelList(hotelListString: String): MutableList<Hotel> = Gson().fromJson(hotelListString, object : TypeToken<User>() {}.type)


    @TypeConverter
    fun fromStringToListOfImages(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromListOfImagesToString(list: List<String>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromRating(rating: Rating?): String? {
        return rating?.let { Gson().toJson(it) }
    }

    @TypeConverter
    fun toRating(value: String?): Rating? {
        return value?.let { Gson().fromJson(it, Rating::class.java) }
    }

    @TypeConverter
    fun fromCoordinates(coordinates: List<Double>?): String? {
        return coordinates?.let { Gson().toJson(it) }
    }

    @TypeConverter
    fun toCoordinates(value: String?): List<Double>? {
        return value?.let { Gson().fromJson(it, object : TypeToken<List<Double>>() {}.type) }
    }
    @TypeConverter
    fun fromReviewSummary(reviewSummary: ReviewSummary?): String? {
        return reviewSummary?.let { Gson().toJson(it) }
    }

    @TypeConverter
    fun toReviewSummary(value: String?): ReviewSummary? {
        return value?.let { Gson().fromJson(it, ReviewSummary::class.java) }
    }
}