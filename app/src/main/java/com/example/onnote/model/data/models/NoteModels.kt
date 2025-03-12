package com.example.onnote.model.data.models
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Entity(tableName = "noteModels")
data class NoteModels(
    val title: String,
    val description: String,
    val time: String = currentTime(),
    var backgroundColor: Int = -1
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    companion object {
        fun currentTime(): String {
            val dateFormat = SimpleDateFormat("dd MMMM 'в' HH:mm", Locale("ru"))
            return dateFormat.format(Date())
        }
    }
}

