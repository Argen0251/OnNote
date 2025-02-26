package com.example.onnote.ui.fragments.note
import NoteAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.onnote.R
import com.example.onnote.databinding.FragmentNoteBinding
import com.example.onnote.ui.App
import com.example.onnote.ui.data.models.NoteModels
import com.example.onnote.ui.interfaces.OnClickIten
import com.example.onnote.ui.utils.PreferenceHelper


class NoteFragment : Fragment(),OnClickIten {
    private var isLinearLayout = true
    private lateinit var binding: FragmentNoteBinding
    private val shared = PreferenceHelper()
    private val noteAdapter = NoteAdapter(this, this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentNoteBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListener()
        initialize()
        loadNotes()
        getData()
    }

    private fun setupListener() {
        binding.btnAction.setOnClickListener{
            findNavController().navigate(R.id.action_noteFragment_to_noteDetailFragment)
        }
        binding.btnChangeLayout.setOnClickListener {
            if (isLinearLayout) {
                binding.rvNote.layoutManager = GridLayoutManager(requireContext(), 2)
                binding.btnChangeLayout.setImageResource(R.drawable.linear)
            } else {
                binding.rvNote.layoutManager = LinearLayoutManager(requireContext())
                binding.btnChangeLayout.setImageResource(R.drawable.grid)
            }
            isLinearLayout = !isLinearLayout
        }
    }
    private fun initialize() {
        binding.rvNote.apply {
            layoutManager= LinearLayoutManager(requireContext())
            adapter =noteAdapter
        }

    }
    private fun loadNotes() {
        App.appDatabase1?.noteDao()?.getAll()?.observe(viewLifecycleOwner) { notes ->
            noteAdapter.submitList(notes)
        }
    }
    private fun getData() {
        App.appDatabase1?.noteDao()?.getAll()?.observe(viewLifecycleOwner){ listModel:List<NoteModels>->
            noteAdapter.submitList(listModel)

        }}

    override fun onLongClick(noteModels: NoteModels) {
        val builder = AlertDialog.Builder(requireContext())
        with(builder){
            setTitle("Удалить заметку")
            setPositiveButton("Удалить"){dialog, _ ->
                App.appDatabase1?.noteDao()?.deletNote(noteModels)
            }
            setNegativeButton("Отмена"){dialog, _ ->
                dialog.cancel()
            }
            show()
        }
        builder.create()
    }

    override fun onClick(noteModels: NoteModels) {
        val action = NoteFragmentDirections.actionNoteFragmentToNoteDetailFragment(noteModels.id)
        findNavController().navigate(action)
    }

}
