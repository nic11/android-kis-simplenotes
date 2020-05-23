package ru.nic11.edu.simplenotes.api

import java.util.*

class NoteResponse(
    val _id: String,
    val text: String,
    val userId: String,
    val createdAt: Date,
    val updatedAt: Date,
    val archived: Boolean,
    val pictureB64: String?
)