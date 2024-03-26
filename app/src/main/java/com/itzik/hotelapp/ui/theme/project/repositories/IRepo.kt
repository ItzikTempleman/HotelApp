package com.itzik.hotelapp.ui.theme.project.repositories

import com.itzik.hotelapp.ui.theme.project.model.User
import com.itzik.hotelapp.ui.theme.project.model.properties.Hotel
import com.itzik.hotelapp.ui.theme.project.model.properties.PropertyIdResponse
import com.itzik.hotelapp.ui.theme.project.model.properties.PropertyInfoResponse
import retrofit2.Response

interface IRepo {

    suspend fun insertUser(user: User)

    suspend fun fetchLoggedInUsers(): MutableList<User>

    suspend fun getUserFromEmailAndPassword(email: String, password: String): User

    suspend fun updateIsLoggedIn(user: User)
    
    suspend fun postUser(id: String,user: User)

    suspend fun getUserList(): Response<MutableList<User>>

    suspend fun getUserById(id:String):Response<User>

    suspend fun getPropertyIdByQuery(query: String): Response<PropertyIdResponse>

    suspend fun getPropertyInfo(
        entityId: String,
        checkin: String,
        checkout: String,
        adults: Int,
        rooms: Int,
        limit: Int,
        currency: String

    ):Response<PropertyInfoResponse>

//
//    suspend fun updateIsLiked(user: User, likedHotel: Hotel)
//
//    suspend fun getLikedList(user:User):MutableList<Hotel>
//
//    suspend fun clearLikedList()

}