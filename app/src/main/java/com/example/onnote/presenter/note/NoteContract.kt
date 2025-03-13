package com.example.onnote.presenter.note

import com.example.onnote.model.data.models.NoteModels

interface NoteContract {
    interface View{
        fun showError(message: String)
        fun showNotes(notes: List<NoteModels>)
    }
    interface Presenter{
        fun getData()
        fun deleteNote(noteModels: NoteModels)
        fun loadNotes()
    }
}