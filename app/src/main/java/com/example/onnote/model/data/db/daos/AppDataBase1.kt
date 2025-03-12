package com.example.onnote.model.data.db.daos

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.onnote.model.data.db.daos.NoteDao
import com.example.onnote.model.data.models.NoteModels

@Database(entities = [NoteModels::class], version = 4)
abstract class AppDataBase1:RoomDatabase() {
    abstract fun noteDao(): NoteDao
}
