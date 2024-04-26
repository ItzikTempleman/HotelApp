package com.itzik.hotelapp.ui.theme.project.utils

object Constants {

    val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$".toRegex()
    val passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$".toRegex()

    const val BASE_URL = "https://sky-scrapper.p.rapidapi.com/"
    const val USER_BASE_URL="http://0.0.0.0:8080"


    const val API_KEY_NAME = "X-RapidAPI-Key"
    const val API_KEY_VALUE = "be8b987f5dmshf1dd12bfaeec996p1597b9jsn98ccd70b59ca"
    const val API_HOST_NAME = "X-RapidAPI-Host"
    const val API_HOST_VALUE = "sky-scrapper.p.rapidapi.com"


    const val USER_TABLE = "userTable"
    const val USER_DATABASE = "userDatabase"

}


