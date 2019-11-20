package ru.nic11.edu.simplenotes

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

const val ID_KEY = "note_id"

class DetailsActivity : AppCompatActivity() {
    companion object {
        fun buildIntent(context: Context, id: String): Intent {
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra(ID_KEY, id)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val id = intent.getStringExtra(ID_KEY)!!
        val note = NoteRepository.getNoteWithId(id)!!

        title = note.title
        findViewById<ImageView>(R.id.note_image).setImageResource(note.drawableIdRes)
        findViewById<TextView>(R.id.note_text).text = note.text
    }
}
