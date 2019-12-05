package ru.nic11.edu.simplenotes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.note_list_item.view.*
import java.text.DateFormat
import java.text.SimpleDateFormat

class NoteListAdapter : RecyclerView.Adapter<NoteListAdapter.MyViewHolder>() {
    class MyViewHolder(val cardView: CardView) : RecyclerView.ViewHolder(cardView) {
        lateinit var noteId : String
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MyViewHolder {
        val context = parent.context
        val cardView = LayoutInflater.from(context)
            .inflate(R.layout.note_list_item, parent, false) as CardView

        val holder = MyViewHolder(cardView)

//        cardView.setOnClickListener {
//            val intent = DetailsActivity.buildIntent(context, holder.noteId)
//            context.startActivity(intent)
//        }

        return holder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val note = NoteRepository.notes[position]
        val cardView = holder.cardView
        holder.noteId = note.id
        cardView.card_note_title.text = note.title
        cardView.card_note_date.text = SimpleDateFormat.getDateInstance(DateFormat.MEDIUM).format(note.date)
        cardView.card_note_text.text = note.text
        cardView.card_note_image.setImageResource(note.drawableIdRes)
    }

    override fun getItemCount() = NoteRepository.notes.size
}