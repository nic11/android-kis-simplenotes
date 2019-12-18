package ru.nic11.edu.simplenotes.db

import android.content.Context
import ru.nic11.edu.simplenotes.R
import java.util.*

class NoteRepository(
    private val context: Context,
    private val dbHolder: DatabaseHolder
) {
    var notes: MutableList<Note> = mutableListOf()
        private set

    fun fillDbDefault() {
        var cnt = 0
        var cs = (cnt++).toString()
        notes.add(Note(
            cs, Date(), "[$cs]" + context.getString(R.string.lipsum),
            context.getString(R.string.somebody) + "/$cs", R.drawable.my_kek2
        ))
        cs = (cnt++).toString()
        notes.add(Note(
            cs, Date(), "[$cs]" + context.getString(R.string.genius),
            "Что на этот раз сказал гений? /$cs", R.drawable.genius
        ))
        cs = (cnt++).toString()
        notes.add(Note(
            cs, Date(), "[$cs]" + context.getString(R.string.portal2),
            "Предложили сыграть в Portal 2 /$cs", R.drawable.geralt
        ))
        cs = (cnt++).toString()
        notes.add(Note(
            cs, Date(), "[$cs]" + context.getString(R.string.running),
            "Не придумал шутку /$cs", R.drawable.running_in_the_90s
        ))
        cs = (cnt++).toString()
        notes.add(Note(
            cs, Date(), "[$cs]" + context.getString(R.string.hat),
            "Нашлась шляпа! /$cs", R.drawable.smug
        ))
    }

    fun getNoteWithId(id: String): Note? {
        return notes.find {note: Note -> note.id == id}
    }
}
