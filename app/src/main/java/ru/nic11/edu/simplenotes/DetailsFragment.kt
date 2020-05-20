package ru.nic11.edu.simplenotes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.transition.Visibility

class DetailsFragment : Fragment() {
    companion object {
        private const val ID_KEY = "note_id"
        const val TAG = "DetailsFragment"

        fun build(id: String): Fragment {
            val fragment = DetailsFragment()

            val args = Bundle()
            args.putString(ID_KEY, id)

            fragment.arguments = args

            return fragment
        }
    }

    private lateinit var hostActivity: HostActivity
    private lateinit var id: String
    private lateinit var noteText: EditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onPause() {
        MyApp.noteRepository.changeNoteText(id, noteText.text.toString()) {
            hostActivity.runOnUiThread {
                hostActivity.notifyNotesUpdated()
            }
        }

        super.onPause()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hostActivity = activity!! as HostActivity
        id = arguments?.getString(ID_KEY) ?:
            throw IllegalArgumentException("use build")
        val note = MyApp.noteRepository.getNoteWithId(id) ?:
            throw IllegalArgumentException("no such note")

        val drawableIdRes = note.drawableIdRes
        val noteImage = view.findViewById<ImageView>(R.id.note_image)
        if (drawableIdRes != null) {
            noteImage.setImageResource(drawableIdRes)
        } else {
            noteImage.visibility = ImageView.GONE
        }
        noteText = view.findViewById<EditText>(R.id.note_text)
//        noteText.text = note.text
        noteText.setText(note.text)
    }
}
