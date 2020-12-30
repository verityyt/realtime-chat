package client

import client.backend.ChatManager
import java.io.BufferedReader
import java.io.InputStreamReader

object Client {

    @JvmStatic
    fun main(args: Array<String>) {

        while (true) {
            val input = BufferedReader(InputStreamReader(System.`in`)).readLine()
            val args2 = input.split(" ")

            if(args2.size != 3) {
                println("Fehler: Bitte verwende 'connect CHAT-ID BENUTZERNAME'!")
            }else {
                if(args2[0] == "connect") {
                    val id = args2[1].toInt()
                    val un = args2[2]
                    println("Verbinde mit ChatServer #$id...")
                    ChatManager.startChat(id, un)
                }else {
                    println("Fehler: Bitte verwende 'connect CHAT-ID BENUTZERNAME'!")
                }
            }
        }

    }

}

