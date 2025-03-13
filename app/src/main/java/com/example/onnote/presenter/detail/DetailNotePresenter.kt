package com.example.onnote.presenter.detail

import androidx.lifecycle.Observer
import com.example.onnote.model.data.models.NoteModels
import com.example.onnote.view.utils.App

class DetailNotePresenter(private val view: DetailNoteContract.View) : DetailNoteContract.Presenter {
    private var noteObserver: Observer<NoteModels?>? = null

    override fun saveNote(noteModels: NoteModels) {
        App.appDatabase1?.noteDao()?.insert(noteModels)
        view.noteSaved()
    }

    override fun updateNote(noteModels: NoteModels) {
        App.appDatabase1?.noteDao()?.updateNote(noteModels)
        view.noteUpdated()
    }

    override fun updateNoteId(noteId: Int) {
        noteObserver?.let { oldObserver ->
            App.appDatabase1?.noteDao()?.getById(noteId)?.removeObserver(oldObserver)
        }
        noteObserver = Observer { model: NoteModels? ->
            model?.let {
                view.showNote(it)
            }
        }
        App.appDatabase1?.noteDao()?.getById(noteId)?.observeForever(noteObserver!!)
    }

    fun removeObserver(noteId: Int) {
        noteObserver?.let { currentObserver ->
            App.appDatabase1?.noteDao()?.getById(noteId)?.removeObserver(currentObserver)
        }
        noteObserver = null
    }
}
