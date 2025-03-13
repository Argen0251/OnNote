package com.example.onnote.presenter.note

import com.example.onnote.model.data.models.NoteModels
import com.example.onnote.view.utils.App

class NotePresenter(private val view: NoteContract.View, )
    :NoteContract.Presenter  {

    override fun getData() {
        App.appDatabase1?.noteDao()?.getAll()?.observeForever{model ->
            view.showNotes(model)
        }
    }

    override fun deleteNote(noteModels: NoteModels) {
        App.appDatabase1?.noteDao()?.deletNote(noteModels)

    }

    override fun loadNotes () {
        App.appDatabase1?.noteDao()?.getAll()?.observeForever { notes ->
            view.showNotes(notes)
        }
    }
}