package ru.nic11.edu.simplenotes.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class AppSqliteOpenHelper(context: Context?) : SQLiteOpenHelper(
    context,
    DATABASE_NAME,
    null,
    VERSION
) {
    override fun onCreate(db: SQLiteDatabase) {
//        PersonContract.createTable(db)
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {
    }

    companion object {
        private const val DATABASE_NAME = "SampleDatabase.db"
        private const val VERSION = 1
    }
}