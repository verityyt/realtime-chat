package client

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket
import java.util.*

private lateinit var clientSocket: Socket
private lateinit var output: PrintWriter
private lateinit var input: BufferedReader

fun main() {

    clientSocket = Socket("0.0.0.0",8080)
    output = PrintWriter(clientSocket.getOutputStream(),true )
    input = BufferedReader(InputStreamReader(clientSocket.getInputStream()))

    while(true) { }

    /*Timer().schedule(object : TimerTask() {
        override fun run() {
            sendMessage("General kenobi!")
        }

    }, 25000)*/

}

private fun sendMessage(message: String) {

    output.println(message)
    val answer = input.readLine()
    println(answer)

}