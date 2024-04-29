package com.itzik.hotelapp.ui.theme.project.main

import android.annotation.SuppressLint
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class BaseApp : Application(){
    companion object {
        private var instance: BaseApp? = null

        fun getInstance() : Context {
            return instance!!.applicationContext
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    override fun onCreate() {
        super.onCreate()
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            val channel= NotificationChannel(
                "location",
                "Location",
                NotificationManager.IMPORTANCE_LOW
            )
            val notificationManager=getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
        instance = this
    }
}