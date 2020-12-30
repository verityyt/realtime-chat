package client.frontend

import client.backend.ChatManager
import client.frontend.Window
import client.frontend.utils.FontRenderer
import java.io.BufferedReader
import java.io.InputStreamReader

fun main() {

    while (true) {
        val input = BufferedReader(InputStreamReader(System.`in`)).readLine()
        val args = input.split(" ")

        if(args.size != 3) {
            println("Fehler: Bitte verwende 'connect CHAT-ID BENUTZERNAME'!")
        }else {
            if(args[0] == "connect") {
                val id = args[1].toInt()
                val un = args[2]
                println("Verbinde mit ChatServer #$id...")
                ChatManager.startChat(id, un)
            }else {
                println("Fehler: Bitte verwende 'connect CHAT-ID BENUTZERNAME'!")
            }
        }
    }

}