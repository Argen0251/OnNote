package com.example.onnote.ui.interfaces

import com.example.onnote.ui.data.models.NoteModels

interface OnClickIten    {
    fun onLongClick(noteModels: NoteModels)
    fun onClick(noteModels: NoteModels)
}