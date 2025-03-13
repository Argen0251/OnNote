    package com.example.onnote.presenter.detail

    import com.example.onnote.model.data.models.NoteModels

    interface DetailNoteContract {
        interface View{
            fun showError(message: String)
            fun noteSaved()
            fun noteUpdated()
            fun showNote(note: NoteModels)
        }
        interface Presenter{
            fun saveNote(noteModels: NoteModels)
            fun updateNote(noteModels: NoteModels)
            fun updateNoteId(noteId: Int)
        }

    }