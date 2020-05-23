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
import ru.nic11.edu.simplenotes.MyApp
import ru.nic11.edu.simplenotes.api.*

class NoteRepository(
    private val context: Context,
    private val dbHolder: DatabaseHolder
) {
    var notes: List<Note> = listOf()

    private var token: String? = null

    init {
        FuelManager.instance.basePath = API_BASE
        FuelManager.instance.addRequestInterceptor { next ->
            { request ->
                if (token != null) {
                    request.appendHeader("authorization", "bearer $token")
                }
                next(request)
            }
        }
        loadToken()
    }

    fun getNoteWithId(id: String): Note? {
        return notes.find {it.id == id}
    }

    fun deleteNoteById(id: String, successCallback: () -> Unit) {
//        if (!loggedIn()) {
//            login {
//                deleteNoteById(id, successCallback)
//            }
//            return
//        }
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
//        if (!loggedIn()) {
//            login {
//                changeNoteText(id, newText, successCallback)
//            }
//            return
//        }
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

    fun create(text: String, imageB64: String, successCallback: (String) -> Unit) {
//        if (!loggedIn()) {
//            login {
//                create(text, imageB64, successCallback)
//            }
//            return
//        }
        Fuel.post("notes")
            .jsonBody(NotePostRequest(text=text, imageB64=imageB64, archived=false))
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
//        if (!loggedIn()) {
//            login {
//                update(successCallback)
//            }
//            return
//        }
        Fuel.get("notes").responseObject<NotesResponse> { request, response, result ->
            when (result) {
                is Result.Failure -> {
                    Log.e(LOG_TAG, "failed to fetch notes", result.error)
                    MyApp.toastLong("failed to fetch notes")
                }
                is Result.Success -> {
                    notes = result.value.notes.map {
                        Note(id=it._id, date=it.updatedAt, text=it.text, pictureB64=it.pictureB64)
                    }
                    successCallback()
                }
            }
        }
    }

    fun login(username: String, password: String, successCallback: () -> Unit,
              failCallback: () -> Unit) {
        Fuel.post("login", listOf("username" to username, "password" to password))
            .responseObject<TokenResponse> { request, response, result ->
                when (result) {
                    is Result.Failure -> {
                        Log.e(LOG_TAG, "Login error", result.error)
                        MyApp.toastLong("login error, maybe login or password is incorrect")
                        failCallback()
                    }
                    is Result.Success -> {
                        Log.i(LOG_TAG, "Logged in as $username")
                        MyApp.toastShort("logged in as $username")
                        token = result.value.token
                        saveToken()
                        successCallback()
                    }
                }
            }
    }

    fun register(username: String, password: String, successCallback: () -> Unit,
              failCallback: () -> Unit) {
        Fuel.post("register", listOf("username" to username, "password" to password))
            .responseObject<TokenResponse> { request, response, result ->
                when (result) {
                    is Result.Failure -> {
                        Log.e(LOG_TAG, "Register", result.error)
                        MyApp.toastLong("could not register: probably user already exists")
                        failCallback()
                    }
                    is Result.Success -> {
                        Log.i(LOG_TAG, "Logged in as $username")
                        MyApp.toastShort("logged in as $username")
                        token = result.value.token
                        saveToken()
                        successCallback()
                    }
                }
            }
    }

    fun logout() {
        token = null
        saveToken()
    }

    fun loggedIn(): Boolean {
        return token != null
    }

    private fun loadToken() {
        val sp = MyApp.context.getSharedPreferences("login", Context.MODE_PRIVATE);
        val token = sp.getString("token", null)
        Log.i(LOG_TAG, "Loaded token: $token; ${token?.length}")
        if (token != null) {
            this.token = token
        }
    }

    private fun saveToken() {
        val sp = MyApp.context.getSharedPreferences("login", Context.MODE_PRIVATE);
        val ed = sp.edit()
        ed.putString("token", token)
        ed.apply()
        Log.i(LOG_TAG, "Saving token: $token; ${token?.length}")
    }
}
