package com.itzik.hotelapp.ui.theme.project.model.properties

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.itzik.hotelapp.ui.theme.project.utils.Constants.USER_TABLE
import kotlinx.parcelize.Parcelize

@Parcelize
data class PropertyInfoResponse(
    @SerializedName("data")
    val infoData: InfoData,
) : Parcelable

@Parcelize
data class InfoData(
    val hotels: List<Hotel>,
    val entity: EntityTypeAndName,
    val localCurrency: String
) : Parcelable


@Parcelize
data class Hotel(
    val images: List<String>,
    val distance: String,
    var rating: Rating?,
    val stars: Int,
    val coordinates: List<Double>,
    var price: String?,
    val name: String,
    val reviewSummary: ReviewSummary,
    var isLiked: Boolean = false
) : Parcelable {
    init {
        if (price == null||price=="null") price = "Price not available"
        if (rating == null) rating = Rating(
            0,
            "Rating not available",
            "0"
        )
    }
}




@Parcelize
 data class EntityTypeAndName(
    @SerializedName("entity_type")
    val entityType: String,
    val name: String,
) : Parcelable


@Parcelize
data class ReviewSummary(
    val count: Int,
    val description: String,
    val value: String,
) : Parcelable


@Parcelize
data class Rating(
    val count: Int?,
    val description: String?,
    val value: String?
) : Parcelable

