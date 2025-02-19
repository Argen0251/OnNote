package com.example.onnote.ui.utils

import android.content.Context
import android.content.SharedPreferences

class PreferenceHelper {
    private lateinit var sharedPreferences: SharedPreferences

    fun unit(context: Context) {
        sharedPreferences = context.getSharedPreferences("shared", Context.MODE_PRIVATE)
    }
    var text:String?
        get()=sharedPreferences.getString("text", "")
        set(value:String?)= sharedPreferences.edit().putString("text",value).apply()

    var onBoardShown :Boolean
        get()= sharedPreferences.getBoolean("onboard",false)
        set(value:Boolean)= sharedPreferences.edit().putBoolean("onboard",value).apply()
}