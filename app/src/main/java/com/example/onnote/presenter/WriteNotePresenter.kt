package com.example.onnote.presenter

import com.example.onnote.model.data.models.NoteModels
import com.example.onnote.view.utils.App

class WriteNotePresenter(private val view: WriteNoteContract.View)
    :WriteNoteContract.Presenter {
    override fun saveNote(noteModels: NoteModels) {
    }

    override fun updateNote(noteModels: NoteModels) {
        App.appDatabase1?.noteDao()?.updateNote(noteModels)
        view.noteUpdated()
    }

}