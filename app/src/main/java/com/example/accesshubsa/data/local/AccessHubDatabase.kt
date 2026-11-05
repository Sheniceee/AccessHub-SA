package com.example.accesshubsa.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [ServiceRequest::class],
    version = 1,
    exportSchema = false
)
abstract class AccessHubDatabase : RoomDatabase() {
    abstract fun requestDao(): RequestDao

    companion object {
        @Volatile
        private var INSTANCE: AccessHubDatabase? = null

        fun getDatabase(context: Context): AccessHubDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AccessHubDatabase::class.java,
                    "accesshub_database"
                ).build().also { INSTANCE = it }
            }
        }
    }
}
