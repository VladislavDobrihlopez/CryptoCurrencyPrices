package com.voitov.cryptoapp.database

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.voitov.cryptoapp.pojo.coinDetails.CoinPriceInfo

@androidx.room.Database(entities = [CoinPriceInfo::class], version = 1, exportSchema = false)
abstract class Database : RoomDatabase() {
    companion object {
        private const val DB_NAME = "main.db"
        private var instance: Database? = null
        private val MONITOR = Any()
        fun getInstance(application: Application): Database {
            synchronized(MONITOR) {
                instance?.let {
                    return it
                }

                val db = Room.databaseBuilder(application, Database::class.java, DB_NAME).build()
                instance = db
                return db
            }

        }

    }

    abstract fun coinDetailsDao(): CoinDetailsDao
}