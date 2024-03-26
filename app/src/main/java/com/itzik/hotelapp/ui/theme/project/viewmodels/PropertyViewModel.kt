package com.itzik.hotelapp.ui.theme.project.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.itzik.hotelapp.ui.theme.project.model.properties.PropertyIdResponse
import com.itzik.hotelapp.ui.theme.project.model.properties.PropertyInfoResponse
import com.itzik.hotelapp.ui.theme.project.repositories.IRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class PropertyViewModel @Inject constructor(
    private val repo: IRepo
) : ViewModel() {


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

    private val _isDrawerOpened = MutableStateFlow(false)
    val isDrawerOpened: Flow<Boolean> = _isDrawerOpened

    fun openDrawer() {
        _isDrawerOpened.value = true
    }

    fun closeDrawer() {
        _isDrawerOpened.value = false
    }

}