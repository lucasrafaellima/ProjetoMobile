package com.example.myapplication.data

import android.content.Context
import androidx.room.Room

// Singleton para não recriar o banco várias vezes
object DatabaseModule {

    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "app_database"
            ).build()

            INSTANCE = instance
            instance
        }
    }
}
