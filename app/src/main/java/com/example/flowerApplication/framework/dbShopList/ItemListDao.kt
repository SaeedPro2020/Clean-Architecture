package com.example.flowerApplication.framework.dbShopList

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ItemListDao {

    @Query("SELECT * from items")
    fun getAll(): List<ShopListEntityDb>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFlowerId(itemName: List<ShopListEntityDb>)

    @Query("SELECT EXISTS (SELECT 1 FROM items WHERE itemId = :id)")
    suspend fun exists(id: Int): Boolean

    @Query("DELETE FROM items WHERE itemId = :id")
    suspend fun deleteItem(id: Int)

    @Query("DELETE from items")
    suspend fun deleteAll()

}