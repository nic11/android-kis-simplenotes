package ru.nic11.edu.simplenotes.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import ru.nic11.edu.simplenotes.R
import java.util.*

class NoteRepository(
    private val context: Context,
    private val dbHolder: DatabaseHolder
) {
    val notes: List<Note>
        get() = loadAll()

    init {
        if (notes.isEmpty()) {
            fillDbDefault()
        }
    }

    private fun fillDbDefault() {
        var cnt = 0L
        val date = GregorianCalendar(2077, 3, 16, 13, 37).time
        create(Note(
            cnt, date, "[$cnt]" + context.getString(R.string.lipsum),
            context.getString(R.string.somebody) + "/$cnt", R.drawable.my_kek2
        ))
        cnt++
        create(Note(
            cnt, date, "[$cnt]" + context.getString(R.string.genius),
            "Что на этот раз сказал гений? /$cnt", R.drawable.genius
        ))
        cnt++
        create(Note(
            cnt, date, "[$cnt]" + context.getString(R.string.portal2),
            "Предложили сыграть в Portal 2 /$cnt", R.drawable.geralt
        ))
        cnt++
        create(Note(
            cnt, date, "[$cnt]" + context.getString(R.string.running),
            "Не придумал шутку /$cnt", R.drawable.running_in_the_90s
        ))
        cnt++
        create(Note(
            cnt, date, "[$cnt]" + context.getString(R.string.hat),
            "Нашлась шляпа! /$cnt", R.drawable.smug
        ))
    }

    fun getNoteWithId(id: Long): Note? {
        return notes.find {note: Note -> note.id == id}
    }

    fun create(note: Note) {
        // notes.add(note)
        try {
            val db = dbHolder.open()
            val contentValues = ContentValues()
            contentValues.put(NoteContractColumns.DATE, note.date.time)
            contentValues.put(NoteContractColumns.TEXT, note.text)
            contentValues.put(NoteContractColumns.TITLE, note.title)
            contentValues.put(NoteContractColumns.DRAWABLE_ID, note.drawableIdRes)
            db.insert(NoteContract.TABLE_NAME, null, contentValues)
        } finally {
            dbHolder.close()
        }
    }

    private fun loadAll(): List<Note> {
        val noteList: MutableList<Note> = ArrayList()
        var cur: Cursor? = null
        try {
            val database: SQLiteDatabase = dbHolder.open()
            cur = database.query(
                NoteContract.TABLE_NAME,
                arrayOf(
                    NoteContractColumns._ID,
                    NoteContractColumns.DATE,
                    NoteContractColumns.TEXT,
                    NoteContractColumns.TITLE,
                    NoteContractColumns.DRAWABLE_ID
                ),
                null,
                null,
                null,
                null,
                null
            )
            while (cur.moveToNext()) {
                val note = Note(
                    id = cur.getLong(cur.getColumnIndex(NoteContractColumns._ID)),
                    date = Date(cur.getLong(cur.getColumnIndex(NoteContractColumns.DATE))),
                    text = cur.getString(cur.getColumnIndex(NoteContractColumns.TEXT)),
                    title = cur.getString(cur.getColumnIndex(NoteContractColumns.TITLE)),
                    drawableIdRes = cur.getInt(cur.getColumnIndex(NoteContractColumns.DRAWABLE_ID))
                )
                noteList.add(note)
            }
        } finally {
            cur?.close()
            dbHolder.close()
        }

        return noteList
    }
}
