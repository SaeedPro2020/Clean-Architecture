package com.example.flowerApplication.framework.dbShopList

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.entities.SHOP_LIST_LOCAL_DB_NAME


@Database(entities = [ShopListEntityDb::class], version = 1, exportSchema = false)
abstract class ShopListDatabase: RoomDatabase() {

    abstract fun flowerDaoShopList(): ItemListDao

    companion object {
        @Volatile
        private var INSTANCE: ShopListDatabase? = null

        fun getDatabase(context: Context): ShopListDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        ShopListDatabase::class.java,
                        SHOP_LIST_LOCAL_DB_NAME,
                    ).fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE!!
        }
    }
}