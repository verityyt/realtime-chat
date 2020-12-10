package server

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

class Database {

    var messages = java.util.HashMap<String, HashMap<Long, String>>()

    val file = File("message.history")

    fun import() {
        if(!file.exists()) {
            file.writeText("[]")
        }

        val typeToken = object : TypeToken<HashMap<String, HashMap<Long, String>>>() {}.type
        messages = Gson().fromJson(file.readText(), typeToken)

    }

    fun export() = file.writeText(Gson().toJson(messages))

    fun add(user: String, message: String) {

        if(messages.containsKey(user)) {
            val hashmap = messages[user]!!
            hashmap[System.currentTimeMillis()] = message

            messages[user] = hashmap
        }else {
            val hashmap = HashMap<Long, String>()
            hashmap[System.currentTimeMillis()] = message

            messages[user] = hashmap
        }

    }

}
RAW Paste Dat