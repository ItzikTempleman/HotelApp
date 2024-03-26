package com.itzik.hotelapp.ui.theme.project.requests

import com.itzik.hotelapp.ui.theme.project.model.properties.PropertyIdResponse
import com.itzik.hotelapp.ui.theme.project.model.properties.PropertyInfoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PropertyService {
    @GET("api/v1/hotels/searchDestinationOrHotel?")
    suspend fun getPropertyIdByQuery(
        @Query("query") query: String
    ): Response<PropertyIdResponse>

    @GET("api/v1/hotels/searchHotels?")
    suspend fun getPropertyInfo(
        @Query("entityId") entityId: String,
        @Query("checkin") checkin: String,
        @Query("checkout") checkout: String,
        @Query("adults") adults: Int,
        @Query("rooms") rooms: Int,
        @Query("limit") limit: Int,
        @Query("currency") currency: String
    ): Response<PropertyInfoResponse>
}