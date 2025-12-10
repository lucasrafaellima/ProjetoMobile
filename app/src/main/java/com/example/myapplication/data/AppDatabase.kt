package com.example.myapplication.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.model.User

@Database(entities = [User::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}


