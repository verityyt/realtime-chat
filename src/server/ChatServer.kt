package server

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket
import java.net.Socket
import java.net.SocketException

class ChatServer(val port: Int) {

    private lateinit var serverSocket: ServerSocket
    private var clientSocket1: Socket? = null
    private var clientSocket2: Socket? = null

    private var input1: BufferedReader? = null
    private  var output1: PrintWriter? = null

    private var input2: BufferedReader? = null
    private var output2: PrintWriter? = null

    fun start() {
        println("Starting chat server on port $port...")

        serverSocket = ServerSocket(port)

        println("Chat server on port $port successfully started!")

        awaitUsers()
    }

    private fun awaitUsers() {
        println("Awaiting users...")

        while(clientSocket1 == null || clientSocket2 == null) {
            if(clientSocket1 == null) {
                clientSocket1 = serverSocket.accept()
                input1 = BufferedReader(InputStreamReader(clientSocket1!!.getInputStream()))
                output1 = PrintWriter(clientSocket1!!.getOutputStream(), true)

                println("User 1 connected! ${clientSocket1!!.inetAddress}")
                awaitMessages1()

            }else if(clientSocket2 == null) {
                clientSocket2 = serverSocket.accept()
                input2 = BufferedReader(InputStreamReader(clientSocket2!!.getInputStream()))
                output2 = PrintWriter(clientSocket2!!.getOutputStream(), true)

                println("User 2 connected! ${clientSocket1!!.inetAddress}")
                awaitMessages2()
            }
        }
    }

    private fun awaitMessages1() {
        Thread {
            while(true) {
                try {
                    val input = input1!!.readLine()

                    println("New message from User 1: $input")
                }catch (e: SocketException) {
                    if(e.message == "Connection reset") {
                        println("User 1 disconnected! ${clientSocket1!!.inetAddress}")
                        clientSocket1 = null
                        input1 = null
                        output1 = null
                        awaitUsers()
                        Thread.currentThread().interrupt()
                    }else {
                        e.printStackTrace()
                    }
                }
            }
        }.start()
    }
    private fun awaitMessages2() {
        Thread {
            while(true) {
                try {
                    val input = input2!!.readLine()

                    println("New message from User 2: $input")
                }catch (e: SocketException) {
                    if(e.message == "Connection reset") {
                        println("User 2 disconnected!")
                        clientSocket2 = null
                        input2 = null
                        output2 = null
                        awaitUsers()
                        Thread.currentThread().interrupt()
                    }else {
                        e.printStackTrace()
                    }
                }
            }
        }.start()
    }

}