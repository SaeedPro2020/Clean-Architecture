package com.example.flowerApplication.framework.dbModelFlowers

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

//Data access objects (Dao)
@Dao
interface FlowerDao {

    @Query("SELECT * from flowers")
    fun getAll(): List<FlowerEntityDb>

    @Query("select * from flowers WHERE flowerName = :itemName")
    suspend fun getItem(itemName: String): FlowerEntityDb

    @Insert
    suspend fun insertFlower(flower: FlowerEntityDb)

    @Insert
    suspend fun insertFlowers(flowers: List<FlowerEntityDb>)

    @Query("DELETE from flowers")
    suspend fun deleteAll()

}