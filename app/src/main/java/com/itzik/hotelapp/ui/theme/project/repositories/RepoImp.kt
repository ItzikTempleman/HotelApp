package com.itzik.hotelapp.ui.theme.project.repositories

import com.itzik.hotelapp.ui.theme.project.data.UserDao
import com.itzik.hotelapp.ui.theme.project.model.User
import com.itzik.hotelapp.ui.theme.project.model.properties.Hotel
import com.itzik.hotelapp.ui.theme.project.model.properties.PropertyIdResponse
import com.itzik.hotelapp.ui.theme.project.model.properties.PropertyInfoResponse
import com.itzik.hotelapp.ui.theme.project.requests.PropertyService
import com.itzik.hotelapp.ui.theme.project.requests.UserService
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

class RepoImp @Inject constructor(
    @Singleton
    private val userDao: UserDao,

    @Singleton
    @Named("Properties") private val propertyService: PropertyService,

    @Singleton
    @Named("Users") private val userService: UserService
) : IRepo {
    override suspend fun insertUser(user: User) = userDao.insertUser(user)

    override suspend fun fetchLoggedInUsers(): MutableList<User> = userDao.fetchLoggedInUsers()
    override suspend fun getUserFromEmailAndPassword(email: String, password: String): User =
        userDao.getUserFromEmailAndPassword(email, password)

    override suspend fun updateIsLoggedIn(user: User) = userDao.updateIsLoggedIn(user)


    override suspend fun postUser(id: String, user: User) = userService.postUser(id, user)

    override suspend fun getUserList(): Response<MutableList<User>> = userService.getUserList()

    override suspend fun getUserById(id: String): Response<User> = userService.getUserById(id)


    override suspend fun getPropertyIdByQuery(query: String): Response<PropertyIdResponse> =
        propertyService.getPropertyIdByQuery(query)

    override suspend fun getPropertyInfo(
        entityId: String,
        checkin: String,
        checkout: String,
        adults: Int,
        rooms: Int,
        limit: Int,
        currency: String
    ): Response<PropertyInfoResponse> = propertyService.getPropertyInfo(
        entityId,
        checkin,
        checkout,
        adults,
        rooms,
        limit,
        currency
    )

//    override suspend fun updateIsLiked(user: User, likedHotel: Hotel) = userDao.updateIsLiked(user, likedHotel)
//
//    override suspend fun getLikedList(user:User): MutableList<Hotel> = userDao.getLikedList(user)
//
//    override suspend fun clearLikedList() = userDao.clearLikedList()
}