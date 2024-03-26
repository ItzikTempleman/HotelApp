package com.itzik.hotelapp.ui.theme.project.model.properties

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PropertyIdResponse(
    val status: Boolean,
    var data: MutableList<Data>
) : Parcelable

@Parcelize
data class Data(
    val entityName:String,
    val entityId: String,
    val entityType:String
) : Parcelable
