package com.itzik.hotelapp.ui.theme.project.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.itzik.hotelapp.ui.theme.project.model.properties.Hotel
import com.itzik.hotelapp.ui.theme.project.utils.Constants.USER_TABLE
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = USER_TABLE)
data class User(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val userName: String,
    val email: String,
    val password: String,
    var isLoggedIn: Boolean = false,
    var isItemLiked: Boolean,
    //var savedHotels: MutableList<Hotel>
) : Parcelable