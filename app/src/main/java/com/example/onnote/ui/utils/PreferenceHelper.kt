package com.example.onnote.ui.utils

import android.content.Context
import android.content.SharedPreferences

class PreferenceHelper {
    private lateinit var sharedPreferences: SharedPreferences
    private val LAYOUT_STATE_KEY = "layout_state"

    fun unit(context: Context) {
        sharedPreferences = context.getSharedPreferences("shared", Context.MODE_PRIVATE)
    }
    var text:String?
        get()=sharedPreferences.getString("text", "")
        set(value:String?)= sharedPreferences.edit().putString("text",value).apply()

    var onBoardShown :Boolean
        get()= sharedPreferences.getBoolean("onboard",false)
        set(value:Boolean)= sharedPreferences.edit().putBoolean("onboard",value).apply()

    var authShown: Boolean
        get() = sharedPreferences.getBoolean("authShown", false)
        set(value) = sharedPreferences.edit().putBoolean("authShown", value).apply()
    fun saveLayoutState(context: Context, isLinearLayout: Boolean) {
        val sharedPref = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        sharedPref.edit().putBoolean(LAYOUT_STATE_KEY, isLinearLayout).apply()
    }

    fun getLayoutState(context: Context): Boolean {
        val sharedPref = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        return sharedPref.getBoolean(LAYOUT_STATE_KEY, true) // По умолчанию LinearLayout
    }
}