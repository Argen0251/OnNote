package com.example.onnote.ui.data.db.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.onnote.ui.data.models.NoteModels

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(noteModel: NoteModels)

    @Query("SELECT*FROM noteModels")
    fun getAll():LiveData<List<NoteModels>>

    @Delete
    fun deletNote (noteModel: NoteModels)
    @Update
    fun updateNote(noteModel: NoteModels)

    @Query("SELECT * FROM notemodels WHERE id = :id")
    fun getById(id:Int):NoteModels?

}
