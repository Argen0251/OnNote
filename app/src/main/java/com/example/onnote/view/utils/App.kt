package com.example.onnote.view.utils
import android.app.Application
import androidx.room.Room
import com.example.onnote.model.data.db.daos.AppDataBase1
import com.example.onnote.model.PreferenceHelper

class App:Application() {
    companion object{
        var appDatabase1: AppDataBase1? =null
    }
    override fun onCreate() {
        super.onCreate()
        val shared = PreferenceHelper()
        shared.unit(this)
        getInstance()
    }
    protected fun getInstance(): AppDataBase1?{
        if (appDatabase1 == null){
            appDatabase1 =applicationContext?.let {
                Room.databaseBuilder(
                    it,
                    AppDataBase1::class.java,
                    "note_database"
                ).fallbackToDestructiveMigration().allowMainThreadQueries().build()
            }
        }
        return appDatabase1
    }
}
