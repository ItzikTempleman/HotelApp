package com.itzik.hotelapp.ui.theme.project.viewmodels

import android.util.Log
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



    fun createUser(
        name: String,
        email: String,
        password: String,
        phoneNumber: Long,
        profileImage: String
    ): User {
        Log.d("TAG", "$phoneNumber")
        return User(
            userName = name,
            email = email,
            password = password,
            isLoggedIn = true,
            isItemLiked = false,
            phoneNumber = phoneNumber,
            profileImage = profileImage
        )
    }


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


    fun isValidEmail(email: String): Boolean =
        email.matches(Constants.emailRegex)


    fun isValidPassword(password: String): Boolean =
        password.matches(Constants.passwordRegex)



    suspend fun updateProfileImage(profileImageString: String) {
        val user = repo.fetchLoggedInUsers().firstOrNull()
        user?.let {
            val updatedUser: User = it.copy(profileImage = profileImageString)
            repo.updateProfileImage(updatedUser)
        }
    }
}
