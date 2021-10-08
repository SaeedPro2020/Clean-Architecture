package com.example.flowerApplication.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.flowerApplication.framework.dataSourceImpl.DataSourceImpl
import com.example.flowerApplication.framework.dbModelFlowers.FlowerEntityDb
import com.example.flowerApplication.framework.dbShopList.ShopListEntityDb
import com.example.flowerApplication.services.ServiceMusic

class SharedViewModel(app: Application) : AndroidViewModel(app) {

    private val dataSourceImpl = DataSourceImpl(app)
    private val serviceMusic = ServiceMusic(app)

    val flowerData = dataSourceImpl.flowerData
    val aboutData = dataSourceImpl.aboutData
    val shopListItems = dataSourceImpl.shopList

    val selectedFlower = MutableLiveData<FlowerEntityDb>()
    val buttonState = MutableLiveData<Boolean>()
    var newValue = MutableLiveData<Float>()


    fun refreshShopList(){
        dataSourceImpl.refreshDataShopList()
    }

    fun refreshData() {
        dataSourceImpl.refreshDataFromWeb()
    }

    fun shopListItem(itemName: ShopListEntityDb) {
        dataSourceImpl.insertItemList(itemName)
    }

    fun deleteDataBase() {
        dataSourceImpl.deleteAllItems()
    }

    fun removeItemFromDb(item: Int) {
        dataSourceImpl.removeFromDb(item)
    }

    fun removePrice(totalItemPrice: Float, fl: Float) {
        newValue.postValue(totalItemPrice - fl)
    }

    fun addPrice(totalItemPrice: Float, fl: Float) {
        newValue.postValue(totalItemPrice + fl)
    }

    fun playMusic() {
        serviceMusic.runServiceMusic()
    }

    fun stopMusic() {
        serviceMusic.stopServiceMusic()
    }

}