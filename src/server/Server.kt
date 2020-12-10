package net.verity

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import server.Database
import java.text.SimpleDateFormat

fun main() {

    val database = Database()
    database.import()

    val server = embeddedServer(Netty, port = 8080) {

        routing {
            get("/") {
                call.respondText("Ready!")
            }

            get("/send") {
                val user = call.request.queryParameters["user"].toString()
                val msg = call.request.queryParameters["message"].toString()

                database.add(user, msg)

                call.respondText("$user: $msg")
            }

            get("/messages") {

                var result = "No messages found!"

                for (message in database.messages) {
                    val user = message.key
                    val hashmap = database.messages[user] as java.util.HashMap<Long, String>
                    for (message2 in hashmap) {
                        val time = SimpleDateFormat("dd.MM.yy HH:mm:ss").format(message2.key)
                        val text = message2.value

                        if(result == "No messages found!") {
                            result = ""
                        }

                        result += "$time | $user: $text\n"
                    }
                }

                call.respondText(result)

            }

            get("/save") {
                database.export()
                call.respondText("Saved database!")
            }

        }

    }

    server.start(wait = true)

}
