package ru.nic11.edu.simplenotes.db

import java.util.*

class Note (
    var id: Long,
    var date: Date,
    var text: String,
    var title: String,
    var drawableIdRes: Int
)