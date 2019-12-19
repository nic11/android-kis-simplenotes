package ru.nic11.edu.simplenotes

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.note_list_item.view.*
import ru.nic11.edu.simplenotes.db.Note
import ru.nic11.edu.simplenotes.db.NoteRepository
import java.text.DateFormat
import java.text.SimpleDateFormat

class NoteListAdapter(
    private val noteRepository: NoteRepository,
    private val activity: HostActivity
) : RecyclerView.Adapter<NoteListAdapter.MyViewHolder>() {

    class MyViewHolder(val cardView: CardView) : RecyclerView.ViewHolder(cardView) {
        lateinit var note : Note
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MyViewHolder {
        val context = parent.context
        val cardView = LayoutInflater.from(context)
            .inflate(R.layout.note_list_item, parent, false) as CardView

        val holder = MyViewHolder(cardView)

        cardView.setOnClickListener {
            activity.onNoteSelected(holder.note.id)
        }

        cardView.card_note_dots.setOnClickListener {
            val popupMenu = PopupMenu(context, it)
            popupMenu.inflate(R.menu.popup_menu)
            popupMenu.setOnMenuItemClickListener { item ->
                when (item?.itemId) {
                    R.id.menu_share -> {
                        val sendIntent: Intent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_TEXT, holder.note.text)
                            type = "text/plain"
                        }

                        val shareIntent = Intent.createChooser(sendIntent, null)
                        ContextCompat.startActivity(context, shareIntent, null)
                        true
                    }
                    R.id.menu_delete -> {
                        MyApp.noteRepository.deleteNodeById(holder.note.id)
                        notifyDataSetChanged()  // TODO: notifyRemoved
                        Toast.makeText(MyApp.context, "deleted note id=${holder.note.id}", Toast.LENGTH_SHORT).show()
                        true
                    }
                    else -> false
                }
            }
            popupMenu.show()
        }

        return holder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val note = noteRepository.notes[position]
        val cardView = holder.cardView
        holder.note = note
        cardView.card_note_date.text = SimpleDateFormat.getDateInstance(DateFormat.MEDIUM).format(note.date)
        cardView.card_note_text.text = note.text
        cardView.card_note_image.setImageResource(note.drawableIdRes)
    }

    override fun getItemCount() = noteRepository.notes.size
}
