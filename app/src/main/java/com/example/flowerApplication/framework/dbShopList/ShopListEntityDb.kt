package com.example.flowerApplication.framework.dbShopList

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class ShopListEntityDb(
    @PrimaryKey(autoGenerate = true)
    val itemId: Int,
    val itemName: String,
    val imageFile: String,
    val price: Double
)
