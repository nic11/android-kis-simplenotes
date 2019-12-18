package ru.nic11.edu.simplenotes

import android.app.Application
import android.content.Context
import ru.nic11.edu.simplenotes.db.DatabaseHolder
import ru.nic11.edu.simplenotes.db.NoteRepository

class MyApp : Application() {
    companion object {
        lateinit var context: Context
            private set

        lateinit var dbHolder: DatabaseHolder

        val noteRepository by lazy {
            NoteRepository(context, dbHolder)
        }
    }

    init {
        context = this
        dbHolder = DatabaseHolder(context)
    }
}
