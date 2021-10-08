package com.example.flowerApplication.framework.dbModelFlowers

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "flowers")
data class FlowerEntityDb(
        @PrimaryKey(autoGenerate = true)
        val flowerId: Int,
        val imageFile: String,
        val flowerName: String,
        val description: String,
        val price: Double,
        val popularity: Float
)