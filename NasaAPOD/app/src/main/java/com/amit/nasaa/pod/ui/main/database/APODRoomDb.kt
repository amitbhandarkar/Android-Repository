package com.amit.nasaa.pod.ui.main.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [APODEntity::class], version = 1, exportSchema = false)
abstract class APODRoomDb : RoomDatabase() {

    abstract fun dao(): APODDao

    companion object {
        @Volatile
        private var INSTANCE: APODRoomDb? = null
        fun getDatabase(context: Context): APODRoomDb {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    APODRoomDb::class.java,
                    "item_database")
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}