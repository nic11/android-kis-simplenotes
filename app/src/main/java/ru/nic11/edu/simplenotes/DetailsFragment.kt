package ru.nic11.edu.simplenotes

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import ru.nic11.edu.simplenotes.db.NoteRepository

class DetailsFragment : Fragment() {
    companion object {
        private const val ID_KEY = "note_id"
        const val TAG = "DetailsFragment"

        fun build(id: Long): Fragment {
            val fragment = DetailsFragment()

            val args = Bundle()
            args.putLong(ID_KEY, id)

            fragment.arguments = args

            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = arguments?.getLong(ID_KEY) ?:
            throw IllegalArgumentException("use build(int)")
        val note = MyApp.noteRepository.getNoteWithId(id) ?:
            throw IllegalArgumentException("no such note")

        activity?.title = note.title
        view.findViewById<ImageView>(R.id.note_image).setImageResource(note.drawableIdRes)
        view.findViewById<TextView>(R.id.note_text).text = note.text
    }
}
