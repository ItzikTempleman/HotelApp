package com.itzik.hotelapp.ui.theme.project.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.itzik.hotelapp.ui.theme.project.model.User
import com.itzik.hotelapp.ui.theme.project.utils.Converters

@Database(entities = [User::class], version = 1, exportSchema = false )
@TypeConverters(Converters::class)
abstract class UserDatabase:RoomDatabase() {
    abstract fun getDao():UserDao

}