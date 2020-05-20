package ru.nic11.edu.simplenotes.db

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import java.util.*

class Note (
    var id: String,
    var date: Date,
    var text: String,
    var drawableIdRes: Int?
)
//) {
//    class Deserializer : ResponseDeserializable<Array<Note>> {
//        override fun deserialize(content: String): Array<Note>
//                = Gson().fromJson(content, Array<Note>::class.java)
//    }
//}