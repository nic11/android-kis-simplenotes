package ru.nic11.edu.simplenotes.db

import android.content.Context
import android.util.Log
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.gson.jsonBody
import com.github.kittinunf.fuel.gson.responseObject
import com.github.kittinunf.result.Result
import ru.nic11.edu.simplenotes.API_BASE
import ru.nic11.edu.simplenotes.LOG_TAG
import ru.nic11.edu.simplenotes.api.*

class NoteRepository(
    private val context: Context,
    private val dbHolder: DatabaseHolder
) {
    var notes: List<Note> = listOf()

    private lateinit var token: String

    init {
//        if (notes.isEmpty()) {
//            fillDbDefault()
//        }
        FuelManager.instance.basePath = API_BASE
    }

    fun getNoteWithId(id: String): Note? {
        return notes.find {it.id == id}
    }

    fun deleteNoteById(id: String, successCallback: () -> Unit) {
        if (!this::token.isInitialized) {
            login {
                deleteNoteById(id, successCallback)
            }
            return
        }
        Fuel.delete("notes/$id")
            .response { request, response, result ->
                when (result) {
                    is Result.Failure -> {
                        Log.e(LOG_TAG, "failed to post note", result.error)
                    }
                    is Result.Success -> {
                        Log.i(LOG_TAG, "note deleted: $id")
                        update {
                            successCallback()
                        }
                    }
                }
            }
    }

    fun changeNoteText(id: String, newText: String, successCallback: () -> Unit) {
        if (!this::token.isInitialized) {
            login {
                changeNoteText(id, newText, successCallback)
            }
            return
        }
        Fuel.put("notes/$id")
            .jsonBody(NotePutRequest(text=newText))
            .response { request, response, result ->
                when (result) {
                    is Result.Failure -> {
                        Log.e(LOG_TAG, "failed to put note", result.error)
                    }
                    is Result.Success -> {
                        Log.i(LOG_TAG, "note updated: $id")
                        update {
                            successCallback()
                        }
                    }
                }
            }
    }

    fun create(note: Note, successCallback: (String) -> Unit) {
        if (!this::token.isInitialized) {
            login {
                create(note, successCallback)
            }
            return
        }
        Fuel.post("notes")
            .jsonBody(NotePostRequest(text=note.text, archived=false))
            .responseObject<NotePostResponse> {request, response, result ->
                when (result) {
                    is Result.Failure -> {
                        Log.e(LOG_TAG, "failed to post note", result.error)
                    }
                    is Result.Success -> {
                        Log.i(LOG_TAG, "note added: ${result.value.noteId}")
                        update {
                            successCallback(result.value.noteId)
                        }
                    }
                }
            }
    }

    fun update(successCallback: () -> Unit) {
        if (!this::token.isInitialized) {
            login {
                update(successCallback)
            }
            return
        }
        Fuel.get("notes").responseObject<NotesResponse> { request, response, result ->
            when (result) {
                is Result.Failure -> {
                    Log.e(LOG_TAG, "failed to fetch notes", result.error)
//                    Toast.makeText(context, "failed to fetch notes", Toast.LENGTH_LONG).show()
                }
                is Result.Success -> {
                    notes = result.value.notes.map {
                        Note(id=it._id, date=it.updatedAt, text=it.text, drawableIdRes=null)
                    }
                    successCallback()
                }
            }
        }
    }

    private fun login(successCallback: () -> Unit) {
        Fuel.post("login", listOf("username" to "jill", "password" to "birthday"))
            .responseObject<TokenResponse> { request, response, result ->
                when (result) {
                    is Result.Failure -> {
                        Log.e(LOG_TAG, "Login error", result.error)
//                        Toast.makeText(context, "login error", Toast.LENGTH_LONG).show()
                    }
                    is Result.Success -> {
                        Log.i(LOG_TAG, "Logged in as jill")
//                        Toast.makeText(context, "logged in as jill", Toast.LENGTH_SHORT).show()
                        token = result.value.token
                        FuelManager.instance.addRequestInterceptor { next ->
                            { request ->
                                request.appendHeader("authorization", "bearer $token")
                                next(request)
                            }
                        }
                        successCallback()
                    }
                }
            }
    }
}
