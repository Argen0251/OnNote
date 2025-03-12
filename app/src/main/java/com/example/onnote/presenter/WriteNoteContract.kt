package com.example.onnote.presenter

import com.example.onnote.model.data.models.NoteModels

interface WriteNoteContract {
    interface View{
        fun showError(message: String)
        fun noteSaved()
        fun noteUpdated()
    }
    interface Presenter{
        fun saveNote(noteModels: NoteModels)
        fun updateNote(noteModels: NoteModels)
    }

}