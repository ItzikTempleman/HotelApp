package com.itzik.hotelapp.ui.theme.project.requests

import com.itzik.hotelapp.ui.theme.project.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface UserService {

    @POST("users/{id}")
    suspend fun postUser(
        @Path("id") userId: String,
        @Body user: User
    )

    @GET("/users")
    suspend fun getUserList(): Response<MutableList<User>>


    @GET("/users/{id}")
    suspend fun getUserById(
        @Path("id") id: String
    ): Response<User>
}