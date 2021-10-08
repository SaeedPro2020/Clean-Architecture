package com.example.data.repository

import androidx.annotation.WorkerThread
import com.example.data.dataSource.DataSource
import com.example.entities.DataClass
import com.example.entities.MYWEB_SERVICE_URL
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class Repository {

    init {
        CoroutineScope(Dispatchers.IO).launch {
            callWebService()
        }
    }

    @WorkerThread
    suspend fun callWebService(): List<DataClass> {
        val retrofit = Retrofit.Builder()
            .baseUrl(MYWEB_SERVICE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
        val service = retrofit.create(DataSource::class.java)
        return service.getFlowerData().body() ?: emptyList()
    }

}