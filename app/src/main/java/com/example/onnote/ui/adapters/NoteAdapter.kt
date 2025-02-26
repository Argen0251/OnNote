
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.onnote.databinding.FragmentNoteBinding
import com.example.onnote.databinding.ItemNoteBinding
import com.example.onnote.ui.data.models.NoteModels
import com.example.onnote.ui.interfaces.OnClickIten

class NoteAdapter(
    private val onLongClick: OnClickIten,
    private val onClick: OnClickIten

)
    :ListAdapter<NoteModels,NoteAdapter.ViewHolder>(DiffCallback()) {

    class ViewHolder(private val binding: ItemNoteBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(item: NoteModels) {
            binding.txtTitle.text= item.title
            binding.txtDescription.text =item.description
            binding.txtDate.text = item.time

            val background = binding.root.background as GradientDrawable
            if (item.backgroundColor != -1) {
                background.setColor(item.backgroundColor)
            } else {
                background.setColor(ContextCompat.getColor(binding.root.context, android.R.color.holo_orange_light))
            }
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
        holder.itemView.setOnLongClickListener {
            onLongClick.onLongClick(getItem(position))
            true
        }
        holder.itemView.setOnClickListener{
            onClick.onClick(getItem(position))
        }

        val item = getItem(position)
        holder.bind(item)
        val background = holder.itemView.background as GradientDrawable
        if (item.backgroundColor != -1) {
            background.setColor(item.backgroundColor)
        } else {
            background.setColor(ContextCompat.getColor(holder.itemView.context, android.R.color.white)) // Цвет по умолчанию
        }

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