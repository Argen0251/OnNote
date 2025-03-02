package com.example.onnote.ui.fragments.note

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.onnote.R
import com.example.onnote.databinding.FragmentNoteBinding
import com.example.onnote.ui.App
import com.example.onnote.ui.GridSpacingItemDecoration
import com.example.onnote.ui.adapters.NoteAdapter
import com.example.onnote.ui.data.models.NoteModels
import com.example.onnote.ui.interfaces.OnClickIten
import com.example.onnote.ui.utils.PreferenceHelper
import com.google.android.material.navigation.NavigationView

class NoteFragment : Fragment(), OnClickIten {

    private lateinit var binding: FragmentNoteBinding
    private val shared = PreferenceHelper()
    private val noteAdapter = NoteAdapter(this, this, true)
    private var isLinearLayout = true
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView

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
                R.id.nav_home -> navController.navigate(R.id.action_noteFragment_to_noteDetailFragment)
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
                    GridSpacingItemDecoration(2, 8.dpToPx(requireContext()), 20.dpToPx(requireContext()), 0.dpToPx(requireContext()))
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
                GridSpacingItemDecoration(2, 8.dpToPx(requireContext()), 20.dpToPx(requireContext()), 0.dpToPx(requireContext()))
            )
        }
        binding.rvNote.adapter = noteAdapter
    }

    private fun loadNotes() {
        App.appDatabase1?.noteDao()?.getAll()?.observe(viewLifecycleOwner) { notes ->
            noteAdapter.submitList(notes)
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
}
