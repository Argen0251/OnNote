package com.example.onnote.view.adapters

import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.onnote.databinding.ItemNoteBinding
import com.example.onnote.databinding.ItemNotegridBinding
import com.example.onnote.model.data.models.NoteModels
import com.example.onnote.view.interfaces.OnClickIten

class NoteAdapter(
    private val onLongClick: OnClickIten,
    private val onClick: OnClickIten,
    private var isLinearLayout: Boolean
) : ListAdapter<NoteModels, NoteAdapter.ViewHolder>(DiffCallback()) {

    class ViewHolder(private val binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: NoteModels) {
            when (binding) {
                is ItemNoteBinding -> {
                    binding.txtTitle.text = "${item.title}:"
                    binding.txtDescription.text = item.description
                    binding.txtDate.text = item.time
                }
                is ItemNotegridBinding -> {
                    binding.txtTitle.text = "${item.title}:"
                    binding.txtDescription.text = item.description
                    binding.txtDate.text = item.time
                }
            }
            val background = binding.root.background as GradientDrawable
            if (item.backgroundColor != -1) {
                background.setColor(item.backgroundColor)
            } else {
                background.setColor(ContextCompat.getColor(binding.root.context, android.R.color.holo_orange_light))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = if (isLinearLayout) {
            ItemNoteBinding.inflate(layoutInflater, parent, false)
        } else {
            ItemNotegridBinding.inflate(layoutInflater, parent, false)
        }
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)

        holder.itemView.setOnLongClickListener {
            onLongClick.onLongClick(item)
            true
        }
        holder.itemView.setOnClickListener {
            onClick.onClick(item)
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<NoteModels>() {
        override fun areItemsTheSame(oldItem: NoteModels, newItem: NoteModels): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: NoteModels, newItem: NoteModels): Boolean {
            return oldItem == newItem
        }
    }

    fun setLayout(isLinearLayout: Boolean) {
        this.isLinearLayout = isLinearLayout
        notifyDataSetChanged()
    }
}