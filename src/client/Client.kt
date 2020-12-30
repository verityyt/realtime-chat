package client

import client.backend.ChatManager
import client.frontend.Window
import client.frontend.utils.FontRenderer
import java.io.BufferedReader
import java.io.InputStreamReader

object Client {

    val messages = HashMap<Int, HashMap<String, String>>()

    @JvmStatic
    fun main(args: Array<String>) {

        /*while (true) {
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
        }*/

        FontRenderer.renderAll()
        Window.build()

        val msg1 = HashMap<String, String>()
        msg1["Joshua"] = "Hallo du da, können wir das später mal testen bitte? Aber soweit wie ich jtz bin denke ich das ist schon nicht schlecht oder was meinst du?"
        messages[0] = msg1

        val msg2 = HashMap<String, String>()
        msg2["Christian"] = "Klar, können wir das später testen! Wann willst du denn?"
        messages[1] = msg2

    }

}

