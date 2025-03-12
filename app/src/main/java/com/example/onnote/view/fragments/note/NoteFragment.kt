package com.example.onnote.view.fragments.note

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.onnote.R
import com.example.onnote.databinding.FragmentNoteBinding
import com.example.onnote.view.utils.App
import com.example.onnote.view.utils.GridSpacingItemDecoration
import com.example.onnote.view.adapters.NoteAdapter
import com.example.onnote.model.data.models.NoteModels
import com.example.onnote.view.interfaces.OnClickIten
import com.example.onnote.model.PreferenceHelper
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class NoteFragment : Fragment(), OnClickIten {

    private lateinit var binding: FragmentNoteBinding
    private val shared = PreferenceHelper()
    private val noteAdapter = NoteAdapter(this, this, true)
    private var isLinearLayout = true
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private lateinit var allNotes: List<NoteModels>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        drawerLayout = binding.drawerLayout
        navView = binding.navView
        navView.itemIconTintList = null

        setupDrawer()
        setupListener()
        initialize()
        loadNotes()
        getData()
        isLinearLayout = shared.getLayoutState(requireContext())
        updateHeader()
        setupSearch()
    }

    override fun onResume() {
        super.onResume()
        loadNotes()
    }
// draw navig
    private fun setupDrawer() {
        binding.drawNavi.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
            binding.navView.bringToFront()
            binding.navView.requestLayout()
        }

        navView.setNavigationItemSelectedListener { menuItem ->
            val navController = binding.root.findNavController()

            when (menuItem.itemId) {
                R.id.nav_home -> navController.navigate(R.id.noteFragment)
                R.id.nav_onboard -> {
                    val bundle = Bundle()
                    bundle.putBoolean("fromMenu", true)
                    navController.navigate(R.id.onBoardFragment, bundle)
                }
                R.id.nav_auth -> {
                    val bundle = Bundle()
                    bundle.putBoolean("fromMenu", true)
                    navController.navigate(R.id.authFragment, bundle)
                }
            }

            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }


    private fun setupListener() {
        binding.btnAction.setOnClickListener {
            findNavController().navigate(R.id.action_noteFragment_to_noteDetailFragment)
        }

        binding.btnChangeLayout.setOnClickListener {
            isLinearLayout = !isLinearLayout
            noteAdapter.setLayout(isLinearLayout)
            shared.saveLayoutState(requireContext(), isLinearLayout)

            if (isLinearLayout) {
                binding.rvNote.layoutManager = LinearLayoutManager(requireContext())
                binding.btnChangeLayout.setImageResource(R.drawable.grid)
                while (binding.rvNote.itemDecorationCount > 0) {
                    binding.rvNote.removeItemDecorationAt(0)
                }
            } else {
                binding.rvNote.layoutManager = GridLayoutManager(requireContext(), 2)
                binding.btnChangeLayout.setImageResource(R.drawable.linear)
                binding.rvNote.addItemDecoration(
                    GridSpacingItemDecoration(
                        2,
                        8.dpToPx(requireContext()),
                        20.dpToPx(requireContext()),
                        0.dpToPx(requireContext())
                    )
                )
            }
            binding.rvNote.adapter = noteAdapter
            noteAdapter.notifyDataSetChanged()
        }
    }

    fun Int.dpToPx(context: Context): Int {
        return (this * context.resources.displayMetrics.density).toInt()
    }

    private fun initialize() {
        if (isLinearLayout) {
            binding.rvNote.layoutManager = LinearLayoutManager(requireContext())
            binding.btnChangeLayout.setImageResource(R.drawable.grid)
        } else {
            binding.rvNote.layoutManager = GridLayoutManager(requireContext(), 2)
            binding.btnChangeLayout.setImageResource(R.drawable.linear)
            binding.rvNote.addItemDecoration(
                GridSpacingItemDecoration(
                    2,
                    8.dpToPx(requireContext()),
                    20.dpToPx(requireContext()),
                    0.dpToPx(requireContext())
                )
            )
        }
        binding.rvNote.adapter = noteAdapter
    }

    private fun loadNotes() {
        App.appDatabase1?.noteDao()?.getAll()?.observe(viewLifecycleOwner) { notes ->
            noteAdapter.submitList(notes)
            allNotes = notes
        }
    }

    private fun getData() {
        App.appDatabase1?.noteDao()?.getAll()?.observe(viewLifecycleOwner) { listModel: List<NoteModels> ->
            noteAdapter.submitList(listModel)
        }
    }

    override fun onLongClick(noteModels: NoteModels) {
        AlertDialog.Builder(requireContext())
            .setTitle("Удалить заметку")
            .setPositiveButton("Удалить") { _, _ ->
                App.appDatabase1?.noteDao()?.deletNote(noteModels)
            }
            .setNegativeButton("Отмена") { dialog, _ ->
                dialog.cancel()
            }
            .show()
    }

    override fun onClick(noteModels: NoteModels) {
        val action = NoteFragmentDirections.actionNoteFragmentToNoteDetailFragment(noteModels.id)
        findNavController().navigate(action)
    }
    //для получение емейла и вставление в хидер
    private fun updateHeader() {
        val headerView = navView.getHeaderView(0)
        val emailTextView = headerView.findViewById<TextView>(R.id.textViewEmail)

        val user = FirebaseAuth.getInstance().currentUser
        emailTextView.text = user?.email ?: "user@example.com"
    }
    //поиск
    private fun setupSearch() {
        binding.searchEditText.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun afterTextChanged(s: Editable?) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val notes = s.toString().trim()

                if (notes.isNotEmpty()) {
                    val filteredList = allNotes.filter { note ->
                        note.title.contains(notes, ignoreCase = true) ||
                                note.description.contains(notes, ignoreCase = true)
                    }
                    noteAdapter.submitList(filteredList)
                } else {
                    noteAdapter.submitList(allNotes)
                }
            }
        })
    }


}
