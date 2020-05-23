package ru.nic11.edu.simplenotes

import android.app.Application
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
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

        fun toast(text: String, duration: Int) {
            Handler(Looper.getMainLooper()).post {
                Toast.makeText(context, text, duration).show()
            }
        }

        fun toastShort(text: String) {
            toast(text, Toast.LENGTH_SHORT)
        }

        fun toastLong(text: String) {
            toast(text, Toast.LENGTH_LONG)
        }
    }

    init {
        context = this
        dbHolder = DatabaseHolder(context)
    }
}
