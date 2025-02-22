package com.example.onnote.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.onnote.databinding.FragmentNoteBinding
import com.example.onnote.databinding.ItemNoteBinding
import com.example.onnote.ui.data.models.NoteModels

class NoteAdapter:ListAdapter<NoteModels,NoteAdapter.ViewHolder>(DiffCallback()) {

    class ViewHolder(private val binding: ItemNoteBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(item: NoteModels) {
            binding.txtTitle.text= item.title
            binding.txtDescription.text =item.description
            binding.txtDate.text = item.time

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemNoteBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,false
            ))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class DiffCallback:DiffUtil.ItemCallback<NoteModels>(){
        override fun areItemsTheSame(oldItem: NoteModels, newItem: NoteModels): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: NoteModels, newItem: NoteModels): Boolean {
            return oldItem ==newItem
        }


    }
}
