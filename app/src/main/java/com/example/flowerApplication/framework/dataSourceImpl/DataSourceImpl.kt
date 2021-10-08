package com.example.flowerApplication.framework.dataSourceImpl

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast
import androidx.annotation.WorkerThread
import androidx.lifecycle.MutableLiveData
import com.example.data.repository.Repository
import com.example.entities.AboutDataClass
import com.example.entities.DataClass
import com.example.entities.LOCAL_JSON_FOR_ABOUT_TAB
import com.example.flowerApplication.framework.dbModelAbout.ReadLocalJson
import com.example.flowerApplication.framework.dbModelFlowers.FlowerDatabase
import com.example.flowerApplication.framework.dbModelFlowers.FlowerEntityDb
import com.example.flowerApplication.framework.dbShopList.ShopListDatabase
import com.example.flowerApplication.framework.dbShopList.ShopListEntityDb
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class DataSourceImpl(val app: Application) {

    private val listType = Types.newParameterizedType(
        List::class.java, AboutDataClass::class.java
    )

    val flowerData = MutableLiveData<List<FlowerEntityDb>>()
    val aboutData = MutableLiveData<List<AboutDataClass>>()
    val shopList = MutableLiveData<List<ShopListEntityDb>>()
    private val flowerDao = FlowerDatabase.getDatabase(app).flowerDao()
    private val itemListDao = ShopListDatabase.getDatabase(app).flowerDaoShopList()
    private val repository = Repository()

    init {

        CoroutineScope(Dispatchers.IO).launch {
            val data = flowerDao.getAll()

            if (data.isEmpty()) {
                refreshDataFromWeb()
            } else {
                flowerData.postValue(data)
            }

        }
        refreshDataShopList()
        getAboutData()
    }

    fun refreshDataShopList() {
        CoroutineScope(Dispatchers.IO).launch {
            val dataShopList = itemListDao.getAll()
            if (dataShopList.isNotEmpty()) {
                shopList.postValue(dataShopList)
            }
        }
    }

    @WorkerThread
    suspend fun getDataFromRepo() {
        if (networkAvailable()) {
            val dataFromWeb: ArrayList<FlowerEntityDb> =
                convertDataFromWeb(repository.callWebService())
            flowerData.postValue(dataFromWeb)
            flowerDao.deleteAll()
            flowerDao.insertFlowers(dataFromWeb)
        } else {
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    app, "We don't have an access to Internet",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    @WorkerThread
    fun convertDataFromWeb(callWebService: List<DataClass>): ArrayList<FlowerEntityDb> {

        val convertedDataFromWeb = ArrayList<FlowerEntityDb>()

        callWebService.forEach { jsonItem ->
            val oneItem = FlowerEntityDb(
                jsonItem.flowerId,
                jsonItem.imageFile,
                jsonItem.flowerName,
                jsonItem.description,
                jsonItem.price,
                jsonItem.popularity
            )

            convertedDataFromWeb.add(oneItem)
        }
        return convertedDataFromWeb
    }

    fun refreshDataFromWeb() {
        CoroutineScope(Dispatchers.IO).launch {
            getDataFromRepo()
        }
    }

    private fun getAboutData() {
        val text = ReadLocalJson.getTextFromAssets(app, LOCAL_JSON_FOR_ABOUT_TAB)
        val moshi = Moshi.Builder().build()
        val adapter: JsonAdapter<List<AboutDataClass>> =
            moshi.adapter(listType)
        aboutData.value = adapter.fromJson(text) ?: emptyList()
    }

    @Suppress("DEPRECATION")
    private fun networkAvailable(): Boolean {
        val connectivityManager = app.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo?.isConnectedOrConnecting ?: false
    }

    fun insertItemList(itemName: ShopListEntityDb) {
        CoroutineScope(Dispatchers.IO).launch {
            val itemNameList = ArrayList<ShopListEntityDb>()
            itemNameList.add(itemName)
            itemListDao.insertFlowerId(itemNameList)
        }
    }

    fun deleteAllItems() {
        CoroutineScope(Dispatchers.IO).launch {
            itemListDao.deleteAll()
        }
    }

    fun removeFromDb(item: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            itemListDao.deleteItem(item)
        }
    }
}