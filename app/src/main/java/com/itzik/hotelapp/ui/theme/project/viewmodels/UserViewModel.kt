package com.itzik.hotelapp.ui.theme.project.viewmodels

import androidx.lifecycle.ViewModel
import com.itzik.hotelapp.ui.theme.project.model.User
import com.itzik.hotelapp.ui.theme.project.repositories.IRepo
import com.itzik.hotelapp.ui.theme.project.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repo: IRepo
) : ViewModel() {

    fun createUser(name: String, email: String, password: String, phoneNumber:Int): User =
        User(
            userName = name,
            email = email,
            password = password,
            isLoggedIn = true,
            isItemLiked = false,
            phoneNumber = phoneNumber
            //savedHotels = mutableListOf()
        )

    suspend fun insertUser(user: User) = repo.insertUser(user)

    suspend fun fetchLoggedInUsers(): Flow<MutableList<User>> {
        val userList = flow {
            val updatedUserList = repo.fetchLoggedInUsers()
            if (updatedUserList.isNotEmpty()) {
                emit(updatedUserList)
            } else return@flow
        }
        return userList
    }

    suspend fun getUserFromUserNameAndPassword(userName: String, password: String): Flow<User?> {
        val user = flow {
            val updatedUser = repo.getUserFromEmailAndPassword(userName, password)
            emit(updatedUser)
        }
        return user
    }

    suspend fun updateIsLoggedIn(user: User) = repo.updateIsLoggedIn(user)

    suspend fun postUser(user: User, id: String) = repo.postUser(id, user)


//    suspend fun getLikedHotels(user:User) = repo.getLikedList(user)
//
//    suspend fun updateIsLiked(user: User, likedHotel: Hotel) = repo.updateIsLiked(user, likedHotel)
//
//    suspend fun clearLikedHotelList() = repo.clearLikedList()



    fun isValidEmail(email: String): Boolean =
        email.matches(Constants.emailRegex)


    fun isValidPassword(password: String): Boolean =
        password.matches(Constants.passwordRegex)


}