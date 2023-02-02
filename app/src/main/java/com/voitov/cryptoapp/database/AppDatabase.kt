package com.voitov.cryptoapp.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.voitov.cryptoapp.pojo.coinDetails.CoinPriceInfo

@Database(entities = [CoinPriceInfo::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        private const val DB_NAME = "main.db"
        private var instance: AppDatabase? = null
        private val MONITOR = Any()
        fun getInstance(application: Application): AppDatabase {
            synchronized(MONITOR) {
                instance?.let {
                    return it
                }

                val db = Room.databaseBuilder(application, AppDatabase::class.java, DB_NAME).build()
                instance = db
                return db
            }

        }

    }

    abstract fun coinDetailsDao(): CoinDetailsDao
}