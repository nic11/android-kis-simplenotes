package ru.nic11.edu.simplenotes

import android.content.Context
import java.util.*

object NoteRepository {
    private var initialized : Boolean = false
    private var _notes: MutableList<Note> = mutableListOf()

    val notes: List<Note>
        get() {
            return _notes
        }

    // init не использовал, т.к. нужен контекст
    fun initialize(context: Context) {
        if (initialized) {
            return
        }
        initialized = true

        var cnt = 0
        for (i in 0 until 50) {
            var cs = (cnt++).toString()
            _notes.add(Note(
                cs, Date(), "[$cs]" + context.getString(R.string.lipsum),
                context.getString(R.string.somebody) + "/$cs", R.drawable.my_kek2
            ))
            cs = (cnt++).toString()
            _notes.add(Note(
                cs, Date(), "[$cs]" + context.getString(R.string.genius),
                "Что на этот раз сказал гений? /$cs", R.drawable.genius
            ))
            cs = (cnt++).toString()
            _notes.add(Note(
                cs, Date(), "[$cs]" + context.getString(R.string.portal2),
                "Предложили сыграть в Portal 2 /$cs", R.drawable.geralt
            ))
            cs = (cnt++).toString()
            _notes.add(Note(
                cs, Date(), "[$cs]" + context.getString(R.string.running),
                "Не придумал шутку /$cs", R.drawable.running_in_the_90s
            ))
            cs = (cnt++).toString()
            _notes.add(Note(
                cs, Date(), "[$cs]" + context.getString(R.string.hat),
                "Нашлась шляпа! /$cs", R.drawable.smug
            ))
        }
    }

    fun getNoteWithId(id: String): Note? {
        return _notes.find {note: Note -> note.id == id}
    }
}