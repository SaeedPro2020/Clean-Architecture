package com.example.flowerApplication.framework.dbModelAbout

import android.content.Context

class ReadLocalJson {
    companion object {
        fun getTextFromAssets(context: Context, fileName: String): String {
            return context.assets.open(fileName).use {
                it.bufferedReader().use {
                    it.readText()
                }
            }
        }
    }
}