package server

import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.NumberFormatException

val servers = mutableListOf<ChatServer>()

object Server {

    fun awaitCommand() {

        val input = BufferedReader(InputStreamReader(System.`in`)).readLine()

        val args = input.split(" ")

        when(args[0]) {
            "list" -> {
                if(servers.size == 0) {
                    println("[Server Manager] No server are currently running")
                }else {
                    println("[Server Manager] ${servers.size} servers are running")
                    for(server in servers) {
                        println("   - ChatServer #${server.port} || Port: ${server.port} || Display: ${server.display}")
                    }
                }
            }
            "start" -> {
                if(args.size != 2) {
                    println("[Server Manager] Wrong usage, try 'start PORT'")
                }else {
                    try {
                        val port = args[1].toInt()

                        if(port.toString().length != 4) {
                            println("[Server Manager] Port must be 4 digits long")
                        }else {

                            var available = true
                            for(server in servers) {
                                if(server.port == port) {
                                    available = false
                                }
                            }

                            if(!available) {
                                println("[Server Manager] There is already a server running on port $port")
                            }else {
                                val server = ChatServer(port)
                                servers.add(server)
                                server.start()

                                if(!server.serverSocket.isClosed) {
                                    println("[Server Manager] Server on port $port successfully started")
                                }else {
                                    println("[Server Manager] Error while starting server on port $port")
                                }
                            }
                        }
                    } catch (e: NumberFormatException) {
                        println("[Server Manager] Port must be numeric")
                    }
                }
            }
            "stop" -> {
                if(args.size != 2) {
                    println("[Server Manager] Wrong usage, try 'stop PORT'")
                }else {
                    var found: ChatServer? = null
                    for(server in servers) {
                        if(server.port == args[1].toInt()) {
                            found = server
                        }
                    }

                    if(found == null) {
                        println("[Server Manager] No server on port ${args[1]} is currently running")
                    }else {
                        servers.remove(found)
                        found.serverSocket.close()
                        println("[Server Manager] Server on port ${found.port} successfully closed")
                    }

                }
            }
            "display" -> {
                if(args.size != 2) {
                    println("[Server Manager] Wrong usage, try 'display PORT'")
                }else {
                    var found: ChatServer? = null
                    for(server in servers) {
                        if(server.port == args[1].toInt()) {
                            found = server
                        }
                    }

                    if(found == null) {
                        println("[Server Manager] No server on port ${args[1]} is currently running")
                    }else {
                        found.display = !found.display
                        println("[Server Manager] Toggled prints for server on port ${found.port} (${found.display})")
                    }

                }
            }
            else -> {
                println("[Server Manager] Wrong usage, try 'list/(start)/(stop)/(display) (PORT)'")
            }
        }
        awaitCommand()
    }

}

fun main() {
    Server.awaitCommand()
}