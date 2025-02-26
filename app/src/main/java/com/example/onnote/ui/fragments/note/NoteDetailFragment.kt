import android.content.Context
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
import androidx.core.util.TypedValueCompat.dpToPx
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

    private lateinit var bidind: FragmentNoteDetailBinding
    private var noteId = -1

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
        updateNote()
        setupTextWatcher()
    }

    private fun updateNote() {
        arguments?.let {
            noteId = it.getInt("noteId", -1)
        }
        if (noteId != -1) {
            val id = App.appDatabase1?.noteDao()?.getById(noteId)
            id?.let { model ->
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

        btnChangeColor.setOnClickListener {
            showColorPickerDialog(it)
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

    private fun showColorPickerDialog(anchorView: View) {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.diolog_color, null)
        val dialog = AlertDialog.Builder(requireContext(), R.style.CustomAlertDialog).create()
        dialog.setView(dialogView)
        dialog.setCancelable(true)

        fun selectColor(colorRes: Int) {
            updateBackgroundColor(ContextCompat.getColor(requireContext(), colorRes))
            dialog.dismiss()
        }

        dialogView.findViewById<View>(R.id.color1).setOnClickListener { selectColor(R.color.orange) }
        dialogView.findViewById<View>(R.id.color2).setOnClickListener { selectColor(R.color.red) }
        dialogView.findViewById<View>(R.id.color3).setOnClickListener { selectColor(R.color.green) }
        dialogView.findViewById<View>(R.id.color4).setOnClickListener { selectColor(R.color.purple) }
        dialogView.findViewById<View>(R.id.color5).setOnClickListener { selectColor(R.color.yellaw) }
        dialogView.findViewById<View>(R.id.color6).setOnClickListener { selectColor(R.color.blue) }

        dialog.show()


        val window = dialog.window
        if (window != null) {
            val layoutParams = WindowManager.LayoutParams()
            layoutParams.copyFrom(window.attributes)

            layoutParams.width = 158.dpToPx(requireContext()) // Ширина диалога
            layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT // Высота диалога

            val location = IntArray(2)
            anchorView.getLocationOnScreen(location)
            layoutParams.gravity = Gravity.TOP or Gravity.START
            layoutParams.x = location[0] - (layoutParams.width - anchorView.width) / 2
            layoutParams.y = location[1] + anchorView.height

            window.attributes = layoutParams
        }
    }

    private fun updateBackgroundColor(color: Int) {
        bidind.root.setBackgroundColor(color)

        if (noteId != -1) {
            App.appDatabase1?.noteDao()?.updateBackgroundColor(noteId, color)
        }
    }

    // конвертация
    fun Int.dpToPx(context: Context): Int {
        return (this * context.resources.displayMetrics.density).toInt()
    }

    override fun onLongClick(noteModels: NoteModels) {
        TODO("Not yet implemented")
    }

    override fun onClick(noteModels: NoteModels) {
        TODO("Not yet implemented")
    }
}