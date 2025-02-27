package com.example.onnote.ui.fragments.note

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
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.onnote.R
import com.example.onnote.databinding.FragmentNoteDetailBinding
import com.example.onnote.ui.App
import com.example.onnote.ui.adapters.NoteAdapter
import com.example.onnote.ui.data.models.NoteModels
import com.example.onnote.ui.interfaces.OnClickIten

class NoteDetailFragment : Fragment(), OnClickIten {

    private lateinit var bidind: FragmentNoteDetailBinding
    private var noteId = -1
    private val noteAdapter = NoteAdapter(this, this)
    private var selectedBackgroundColor: Int = Color.WHITE

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
        val currentTime = NoteModels.currentTime()
        bidind.txtTime.text = NoteModels.currentTime()
        setupTextWatcher()
        updateNote()
    }

    private fun updateNote() {
        arguments?.let {
            noteId = it.getInt("noteId", -1)
        }
        if (noteId != -1) {
            App.appDatabase1?.noteDao()?.getById(noteId)?.observe(viewLifecycleOwner) { model ->
                model?.let {
                    bidind.txtTitle.setText(it.title)
                    bidind.txtDescription.setText(it.description)
                    selectedBackgroundColor = it.backgroundColor
                }
            }
        }
    }


    private fun setupListener() = with(bidind) {
        btnAdd.setOnClickListener {
            val edTitle: String = txtTitle.text.toString()
            val edDescription: String = txtDescription.text.toString()
            val currentTime = NoteModels.currentTime()
            if (noteId != -1) {
                val updateNote = NoteModels(edTitle, edDescription, currentTime, selectedBackgroundColor)
                updateNote.id = noteId
                App.appDatabase1?.noteDao()?.updateNote(updateNote)
            } else {
                App.appDatabase1?.noteDao()?.insert(NoteModels(edTitle, edDescription, currentTime, selectedBackgroundColor))
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
                    bidind.btnAdd.visibility = View.VISIBLE
                } else {
                    bidind.btnAdd.visibility = View.GONE
                }
            }
        }
        bidind.txtTitle.addTextChangedListener(textWatcher)
        bidind.txtDescription.addTextChangedListener(textWatcher)
        bidind.btnAdd.visibility = View.GONE
    }

    override fun onLongClick(noteModels: NoteModels) {
        TODO("Not yet implemented")
    }

    override fun onClick(noteModels: NoteModels) {
        TODO("Not yet implemented")
    }}