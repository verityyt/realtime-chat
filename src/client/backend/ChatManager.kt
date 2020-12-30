package client.backend

import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket
import java.util.*

private lateinit var clientSocket: Socket
private lateinit var output: PrintWriter
private lateinit var input: BufferedReader

object ChatManager {

    fun startChat(port: Int, username: String) {

        clientSocket = Socket("192.168.155.40", port)
        output = PrintWriter(clientSocket.getOutputStream(), true)
        input = BufferedReader(InputStreamReader(clientSocket.getInputStream()))

        sendPaket("IDENTIFY", username)

        println("Erfolgreich mit ChatServer $port verbunden! (Tippen + Enter um eine Nachricht zusenden) \n")

        awaitMessages()

        while (true) {
            val input = BufferedReader(InputStreamReader(System.`in`)).readLine()
            sendPaket("MESSAGE", input)
        }

    }

    private fun awaitMessages() {
        Thread {
            while (true) {
                val input = input.readLine()

                handleInput(input)
            }
        }.start()
    }

    private fun handleInput(input: String) {

        val json = JSONParser().parse(input) as JSONObject
        val action = json["action"].toString()
        val extra = json["extra"].toString()
        val extra2 = json["extra2"].toString()

        if (action == "MESSAGE") {
            println("$extra2: $extra")
        }
    }

    private fun sendPaket(action: String, extra: String) {
        val json = JSONObject()
        json["action"] = action
        json["extra"] = extra

        output.println(json.toJSONString())
    }

}