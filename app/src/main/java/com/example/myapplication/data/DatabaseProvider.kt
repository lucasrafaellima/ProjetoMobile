package com.example.myapplication.data

import android.content.Context
import androidx.room.Room

object DatabaseProvider {
    val db: AppDatabase by lazy {
        Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "meu_banco.db"
        ).build()
    }

    lateinit var appContext: Context

    fun init(context: Context) {
        appContext = context
    }
}
