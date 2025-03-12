package com.example.onnote.view.interfaces

import com.example.onnote.model.data.models.NoteModels

interface OnClickIten    {
    fun onLongClick(noteModels: NoteModels)
    fun onClick(noteModels: NoteModels)
}