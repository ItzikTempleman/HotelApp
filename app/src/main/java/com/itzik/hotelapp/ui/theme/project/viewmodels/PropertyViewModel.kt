package com.itzik.hotelapp.ui.theme.project.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.itzik.hotelapp.ui.theme.project.model.properties.PropertyIdResponse
import com.itzik.hotelapp.ui.theme.project.model.properties.PropertyInfoResponse
import com.itzik.hotelapp.ui.theme.project.repositories.IRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class PropertyViewModel @Inject constructor(
    private val repo: IRepo
) : ViewModel() {

    private val _propertyList = MutableStateFlow<PropertyInfoResponse?>(null)
    val propertyList: StateFlow<PropertyInfoResponse?> = _propertyList


    private val _locationNameState = MutableStateFlow<Pair<String?, String?>>(Pair("",""))
    val locationNameState: StateFlow<Pair<String?, String?>> = _locationNameState


    fun updatePropertyList(propertyInfo: PropertyInfoResponse) {
        _propertyList.value = propertyInfo
    }

    fun updateCityAndCountry(locationName: Pair<String, String>) {
        Log.d("TAG", "Updating city and country: $locationName")
        _locationNameState.value = locationName
    }

    suspend fun getPropertyIdByQuery(query: String): Flow<PropertyIdResponse> {
        val idResponseList = flow {
            val response = repo.getPropertyIdByQuery(query)
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    emit(responseBody)
                } else Log.d("TAG", "Failure message: " + response.message())
                return@flow
            } else Log.d("TAG", "Failure message: " + response.message())
            return@flow
        }
        return idResponseList
    }

    suspend fun getPropertyInfo(
        entityId: String,
        checkin: String,
        checkout: String,
        adults: Int,
        rooms: Int,
        limit: Int,
        currency: String
    ): Flow<PropertyInfoResponse> {
        val propertyInfoObjList = flow {
            val response = repo.getPropertyInfo(
                entityId,
                checkin,
                checkout,
                adults,
                rooms,
                limit,
                currency
            )
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    emit(body)
                } else Log.d("TAG", "Failure message: " + response.message())
                return@flow
            } else Log.d("TAG", "Failure message: " + response.message())
            return@flow
        }
        return propertyInfoObjList
    }

    suspend fun sortPropertyList(
        sortedLabel: String,
        propertyInfo: PropertyInfoResponse
    ): Flow<PropertyInfoResponse> {
        return flow {
            val newHotelList = when (sortedLabel) {
                "Price" -> propertyInfo.infoData.copy(
                    hotels = propertyInfo.infoData.hotels.sortedBy {
                        it.price?.substring(1)?.toInt()
                    }
                )

                "User rating" -> propertyInfo.infoData.copy(
                    hotels = propertyInfo.infoData.hotels.sortedBy { it.rating?.value }
                )

                "Star rating" -> propertyInfo.infoData.copy(
                    hotels = propertyInfo.infoData.hotels.sortedBy { it.stars }
                )

                else -> propertyInfo.infoData.copy(
                    hotels = propertyInfo.infoData.hotels.sortedBy { it.name }
                )
            }
            emit(PropertyInfoResponse(newHotelList))
        }
    }

}