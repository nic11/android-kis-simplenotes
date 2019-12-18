package ru.nic11.edu.simplenotes.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import java.util.concurrent.locks.ReentrantLock

class DatabaseHolder(context: Context) {
    private val appSqliteOpenHelper: AppSqliteOpenHelper = AppSqliteOpenHelper(context)
    private var sqLiteDatabase: SQLiteDatabase? = null
    private var databaseOpenCloseBalance = 0
    private val reentrantLock = ReentrantLock()

    fun open(): SQLiteDatabase? {
        return try {
            reentrantLock.lock()
            if (databaseOpenCloseBalance == 0) {
                sqLiteDatabase = appSqliteOpenHelper.writableDatabase
            }
            ++databaseOpenCloseBalance
            sqLiteDatabase
        } finally {
            reentrantLock.unlock()
        }
    }

    fun close() {
        try {
            reentrantLock.lock()
            --databaseOpenCloseBalance
            if (databaseOpenCloseBalance == 0) {
                sqLiteDatabase!!.close()
                sqLiteDatabase = null
            }
        } finally {
            reentrantLock.unlock()
        }
    }

}