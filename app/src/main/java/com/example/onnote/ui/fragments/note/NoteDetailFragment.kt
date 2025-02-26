    package com.example.onnote.ui.fragments.note

    import android.os.Bundle
    import android.text.Editable
    import android.text.TextWatcher
    import androidx.fragment.app.Fragment
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import androidx.navigation.fragment.findNavController
    import androidx.recyclerview.widget.LinearLayoutManager
    import androidx.recyclerview.widget.RecyclerView.LayoutManager
    import com.airbnb.lottie.Lottie.initialize
    import com.example.onnote.R
    import com.example.onnote.databinding.FragmentNoteDetailBinding
    import com.example.onnote.ui.App
    import com.example.onnote.ui.adapters.NoteAdapter
    import com.example.onnote.ui.data.models.NoteModels
    import com.example.onnote.ui.interfaces.OnClickIten

    class NoteDetailFragment : Fragment(), OnClickIten {

        private lateinit var bidind:FragmentNoteDetailBinding
        private var noteId = -1
        private val noteAdapter = NoteAdapter(this, this    )

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
            bidind= FragmentNoteDetailBinding.inflate(inflater,container,false)
            return bidind.root
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            setupListener()
            val currentTime = NoteModels.currentTime()
            bidind.txtTime.text = NoteModels.currentTime()
            updateNote()
            setupTextWatcher()
        }

        private fun updateNote() {
            arguments?.let{
                noteId = it.getInt("noteId", -1)
            }
            if (noteId != -1){
                val id = App.appDatabase1?.noteDao()?.getById(noteId)
                id?.let {model->
                    bidind.txtTitle.setText(model.title)
                    bidind.txtDescription.setText(model.description)
                }
            }
        }

        private fun setupListener() = with(bidind) {
            btnAdd.setOnClickListener {
                val edTitle: String = txtTitle.text.toString()
                val edDescription: String = txtDescription.text.toString()

                if (noteId != -1) {
                    val updateNote = NoteModels(edTitle, edDescription)
                    updateNote.id = noteId
                    App.appDatabase1?.noteDao()?.updateNote(updateNote)
                } else {
                    App.appDatabase1?.noteDao()?.insert(NoteModels(edTitle, edDescription))
                }

                findNavController().navigateUp()
            }

            back.setOnClickListener {
                findNavController().navigateUp()
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
            bidind.btnAdd.visibility = View.GONE        }

        override fun onLongClick(noteModels: NoteModels) {
            TODO("Not yet implemented")
        }

        override fun onClick(noteModels: NoteModels) {
            TODO("Not yet implemented")
        }

    }
