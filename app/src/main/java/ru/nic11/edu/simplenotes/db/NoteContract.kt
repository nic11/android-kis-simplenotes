package ru.nic11.edu.simplenotes.db

import android.database.sqlite.SQLiteDatabase
import ru.nic11.edu.simplenotes.db.NoteContractColumns as Columns


object NoteContract {
    const val TABLE_NAME = "notes"

    fun createTable(db: SQLiteDatabase) {
        db.execSQL(
            """
                CREATE TABLE $TABLE_NAME (
                    ${Columns._ID} INTEGER PRIMARY KEY AUTOINCREMENT,
                    ${Columns.DATE} TIMESTAMP NOT NULL,
                    ${Columns.TEXT} TEXT NOT NULL,
                    ${Columns.DRAWABLE_ID} INTEGER NOT NULL
                )
            """.trimIndent()
        )
    }

    fun migrate(
        db: SQLiteDatabase,
        @SuppressWarnings("UNUSED_PARAMETER")
        oldVersion: Int,
        @SuppressWarnings("UNUSED_PARAMETER")
        newVersion: Int
    ) {
        db.execSQL(
            """
                DROP TABLE $TABLE_NAME
            """.trimIndent()
        )
        createTable(db)
    }
}
