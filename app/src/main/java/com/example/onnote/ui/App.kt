package com.example.onnote.ui

import android.app.Application
import com.example.onnote.ui.utils.PreferenceHelper

class App:Application() {
    override fun onCreate() {
        super.onCreate()
        val shared = PreferenceHelper()
        shared.unit(this)
    }
}