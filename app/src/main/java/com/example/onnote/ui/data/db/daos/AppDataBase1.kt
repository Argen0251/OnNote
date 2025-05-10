package com.example.onnote.ui.data.db.daos

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.onnote.ui.data.db.daos.NoteDao
import com.example.onnote.ui.data.models.NoteModels

@Database(entities = [NoteModels::class], version = 4)
abstract class AppDataBase1:RoomDatabase() {
    abstract fun noteDao(): NoteDao
}
