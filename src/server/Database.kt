package server

class Database {

    var messages = java.util.HashMap<String, HashMap<Long, String>>()

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