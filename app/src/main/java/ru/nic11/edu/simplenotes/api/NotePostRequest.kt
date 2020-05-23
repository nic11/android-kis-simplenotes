package ru.nic11.edu.simplenotes.api

class NotePostRequest(
    val text: String,
    val imageB64: String,
    val archived: Boolean
)