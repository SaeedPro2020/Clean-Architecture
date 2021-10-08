package com.example.entities

import java.util.ArrayList

object DummyContent {

    var ITEMS: MutableList<DummyItem> = ArrayList()


    init {
        setDataItem()
    }

    data class DummyItem(var title: String, var date: String) {
        override fun toString(): String = date
    }

    //** Later we remove this part
    //region for list of items related to card view
    private fun setDataItem(){
        ITEMS.add(DummyItem("Valentine's day", "03/10/2020"))
        ITEMS.add(DummyItem("Happy birthday", "06/11/2020"))
    }

    //endregion

}