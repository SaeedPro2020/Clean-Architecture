package com.example.flowerApplication.framework.dbModelFlowers

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.entities.FLOWER_ITEMS_LOCAL_DB_NAME

@Database(entities = [FlowerEntityDb::class], version = 1, exportSchema = false)
abstract class FlowerDatabase : RoomDatabase() {

    abstract fun flowerDao(): FlowerDao

    companion object {
        @Volatile
        private var INSTANCE: FlowerDatabase? = null

        fun getDatabase(context: Context): FlowerDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        FlowerDatabase::class.java,
                        FLOWER_ITEMS_LOCAL_DB_NAME,
                    ).build()
                }
            }
            return INSTANCE!!
        }
    }

}