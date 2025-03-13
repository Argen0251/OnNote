package com.example.onnote.view.fragments.note

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.onnote.R
import com.example.onnote.databinding.FragmentNoteDetailBinding
import com.example.onnote.view.utils.App
import com.example.onnote.model.data.models.NoteModels
import com.example.onnote.presenter.detail.DetailNoteContract
import com.example.onnote.presenter.detail.DetailNotePresenter

class NoteDetailFragment : Fragment(),  DetailNoteContract.View {

    private lateinit var bidind: FragmentNoteDetailBinding
    private var noteId = -1
    private var selectedBackgroundColor: Int = Color.WHITE
    private val presenter by lazy { DetailNotePresenter(this) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bidind = FragmentNoteDetailBinding.inflate(inflater, container, false)
        return bidind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListener()
        bidind.txtTime.text = NoteModels.currentTime()
        setupTextWatcher()
        updateNote()
    }

    private fun updateNote() {
        arguments?.let {
            noteId = it.getInt("noteId", -1)
        }
        if (noteId != -1) {
            presenter.updateNoteId(noteId)
        }
    }



    private fun setupListener() = with(bidind) {
        btnGotovo.setOnClickListener {
            val edTitle: String = txtTitle.text.toString()
            val edDescription: String = txtDescription.text.toString()
            val currentTime = NoteModels.currentTime()
            if (noteId != -1) {
                val updateNote = NoteModels(edTitle, edDescription, currentTime, selectedBackgroundColor)
                updateNote.id = noteId
                presenter.updateNote(updateNote)
            } else {
                val newNote = NoteModels(edTitle, edDescription, currentTime, selectedBackgroundColor)
                presenter.saveNote(newNote)
            }

            findNavController().navigateUp()
        }

        back.setOnClickListener {
            findNavController().navigateUp()
        }
        btnChangeColor.setOnClickListener {
            showColorPickerDialog()
        }
    }

    //цвет
    private fun showColorPickerDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.diolog_color, null)
        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()

        dialogView.findViewById<View>(R.id.color1).setOnClickListener {
            updateBackgroundColor(ContextCompat.getColor(requireContext(), R.color.first))
            dialog.dismiss()
        }
        dialogView.findViewById<View>(R.id.color2).setOnClickListener {
            updateBackgroundColor(ContextCompat.getColor(requireContext(), R.color.second))
            dialog.dismiss()
        }
        dialogView.findViewById<View>(R.id.color3).setOnClickListener {
            updateBackgroundColor(ContextCompat.getColor(requireContext(), R.color.third))
            dialog.dismiss()
        }
        dialogView.findViewById<View>(R.id.color4).setOnClickListener {
            updateBackgroundColor(ContextCompat.getColor(requireContext(), R.color.four))
            dialog.dismiss()
        }
        dialogView.findViewById<View>(R.id.color5).setOnClickListener {
            updateBackgroundColor(ContextCompat.getColor(requireContext(), R.color.five))
            dialog.dismiss()
        }
        dialogView.findViewById<View>(R.id.color6).setOnClickListener {
            updateBackgroundColor(ContextCompat.getColor(requireContext(), R.color.six))
            dialog.dismiss()
        }

        dialog.show()
// настройка параметров которые были по умолчанию
        val window = dialog.window
        if (window != null) {
            val layoutParams = WindowManager.LayoutParams()
            layoutParams.copyFrom(window.attributes)
            layoutParams.width = 200.dpToPx(requireContext())
            val location = IntArray(2)
            bidind.btnChangeColor.getLocationOnScreen(location)
            layoutParams.gravity = Gravity.TOP or Gravity.START
            layoutParams.x = location[0] - (layoutParams.width - bidind.btnChangeColor.width) / 2
            layoutParams.y = location[1] + bidind.btnChangeColor.height

            window.attributes = layoutParams
        }
    }

    // конвертация для настр размера
    fun Int.dpToPx(context: Context): Int {
        return (this * context.resources.displayMetrics.density).toInt()
    }

    private fun updateBackgroundColor(color: Int) {
        selectedBackgroundColor = color
        if (noteId != -1) {
            App.appDatabase1?.noteDao()?.updateBackgroundColor(noteId, color)
        }
    }



    private fun setupTextWatcher() {
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val title = bidind.txtTitle.text.toString().trim()
                val description = bidind.txtDescription.text.toString().trim()
                if (title.isNotEmpty() && description.isNotEmpty()) {
                    bidind.btnGotovo.visibility = View.VISIBLE
                } else {
                    bidind.btnGotovo.visibility = View.GONE
                }
            }
        }
        bidind.txtTitle.addTextChangedListener(textWatcher)
        bidind.txtDescription.addTextChangedListener(textWatcher)
        bidind.btnGotovo.visibility = View.GONE
    }


    override fun onDestroyView() {
        super.onDestroyView()
        presenter.removeObserver(noteId)
    }


    override fun showError(message: String) {

    }

    override fun noteSaved() {
        Toast.makeText(requireContext(), "NoteSaved", Toast.LENGTH_SHORT).show()
    }

    override fun noteUpdated() {
        Toast.makeText(requireContext(), "NoteUpdated", Toast.LENGTH_SHORT).show()
    }

    override fun showNote(note: NoteModels) {
        val safeContext = context ?: return
        bidind.txtTitle.setText(note.title)
        bidind.txtDescription.setText(note.description)
        selectedBackgroundColor = note.backgroundColor
        Toast.makeText(safeContext, "NoteUpdatedIDDD", Toast.LENGTH_SHORT).show()
    }
}