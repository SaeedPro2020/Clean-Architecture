package com.example.data.dataSource

import com.example.entities.DataClass
import com.example.entities.REST_API
import retrofit2.Response
import retrofit2.http.GET

interface DataSource {
    @GET(REST_API)
    suspend fun getFlowerData(): Response<List<DataClass>>
}