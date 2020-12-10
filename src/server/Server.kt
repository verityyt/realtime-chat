package net.verity

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import server.Database

fun main() {

    val database = Database()
    database.import()

    val server = embeddedServer(Netty, port = 8080) {

        routing {
            get("/") {
                call.respondText("Ready!")
            }
        }

    }

    server.start(wait = true)

}
