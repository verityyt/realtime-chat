package client.backend

import client.Client
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ConnectException
import java.net.Socket
import java.util.*

private lateinit var clientSocket: Socket
private lateinit var output: PrintWriter
private lateinit var input: BufferedReader

object ChatManager {

    fun startChat(port: Int, username: String) {
        Thread {
            try {
                clientSocket = Socket("192.168.155.40", port)
                output = PrintWriter(clientSocket.getOutputStream(), true)
                input = BufferedReader(InputStreamReader(clientSocket.getInputStream()))

                sendPaket("IDENTIFY", username)

                awaitMessages()
            }catch(e: ConnectException) {
                Client.onConnectExp(port)
            }
        }.start()
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
            Client.addMessage(extra2, extra)
        }
    }

    fun sendPaket(action: String, extra: String) {
        val json = JSONObject()
        json["action"] = action
        json["extra"] = extra

        output.println(json.toJSONString())
    }

}