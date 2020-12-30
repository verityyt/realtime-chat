package server

import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket
import java.net.Socket
import java.net.SocketException

class ChatServer(val port: Int) {

    lateinit var serverSocket: ServerSocket
    private var clientSocket1: Socket? = null
    private var clientSocket2: Socket? = null

    private var input1: BufferedReader? = null
    private  var output1: PrintWriter? = null

    private var input2: BufferedReader? = null
    private var output2: PrintWriter? = null

    private var username1: String? = null
    private var username2: String? = null

    var display = true

    fun start() {
        Thread {
            print("Starting chat server on port $port...")

            serverSocket = ServerSocket(port)

            print("Chat server on port $port successfully started!")

            awaitUsers()
        }.start()
        Thread.sleep(1000)
    }

    private fun awaitUsers() {
        print("Awaiting users...")

        try {
            while(clientSocket1 == null || clientSocket2 == null) {
                if(clientSocket1 == null) {

                    clientSocket1 = serverSocket.accept()
                    input1 = BufferedReader(InputStreamReader(clientSocket1!!.getInputStream()))
                    output1 = PrintWriter(clientSocket1!!.getOutputStream(), true)

                    print("User 1 connected! ${clientSocket1!!.inetAddress}")
                    awaitMessages1()

                }else if(clientSocket2 == null) {
                    clientSocket2 = serverSocket.accept()
                    input2 = BufferedReader(InputStreamReader(clientSocket2!!.getInputStream()))
                    output2 = PrintWriter(clientSocket2!!.getOutputStream(), true)

                    print("User 2 connected! ${clientSocket1!!.inetAddress}")
                    awaitMessages2()
                }
            }
        }catch(e: SocketException) {
            print("Socket closed")
        }
    }

    private fun awaitMessages1() {
        Thread {
            while(true) {
                try {
                    val input = input1!!.readLine()

                    print("New packet from user 1: $input")

                    handleInput(input, clientSocket1!!)
                }catch (e: SocketException) {
                    if(e.message == "Connection reset") {
                        print("User 1 disconnected! ${clientSocket1!!.inetAddress}")
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

                    print("New packet from user 2: $input")

                    handleInput(input, clientSocket2!!)
                }catch (e: SocketException) {
                    if(e.message == "Connection reset") {
                        print("User 2 disconnected!")
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

    private fun handleInput(input: String, client: Socket) {

        val json = JSONParser().parse(input) as JSONObject
        val action = json["action"].toString()
        val extra = json["extra"].toString()

        if(action == "IDENTIFY") {
            if(client == clientSocket1) {
                username1 = extra
            }else if(client == clientSocket2) {
                username2 = extra
            }
        }else if(action == "MESSAGE") {
            if(client == clientSocket1) {
                sendPaket(action, extra, username1!!, output2!!)
            }else if(client == clientSocket2) {
                sendPaket(action, extra, username2!!, output1!!)
            }
        }

    }

    private fun sendPaket(action: String, extra: String, extra2: String, output: PrintWriter) {
        val json = JSONObject()
        json["action"] = action
        json["extra"] = extra
        json["extra2"] = extra2

        val user = if(output == output1) {
            "1"
        }else {
            "2"
        }
        print("Sending packet to to user $user... (${json.toJSONString()})")

        output.println(json.toJSONString())
    }

    private fun print(text: String) {
        if(display) {
            println("[Server #$port] $text")
        }
    }

}