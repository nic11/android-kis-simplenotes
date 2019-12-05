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

class DetailsFragment : Fragment() {
    companion object {
        const val ID_KEY = "note_id"
        const val TAG = "DetailsFragment"

        fun build(id: String): Fragment {
            val fragment = DetailsFragment()

            val args = Bundle()
            args.putString(ID_KEY, id)

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

        val id = arguments!!.getString(ID_KEY)!!
        val note = NoteRepository.getNoteWithId(id)!!

        activity!!.title = note.title
        view.findViewById<ImageView>(R.id.note_image).setImageResource(note.drawableIdRes)
        view.findViewById<TextView>(R.id.note_text).text = note.text
    }
}
