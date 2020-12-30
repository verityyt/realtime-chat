package client

import client.frontend.Window
import client.frontend.utils.FontRenderer

object Client {

    val messages = ArrayList<Pair<String, String>>()

    @JvmStatic
    fun main(args: Array<String>) {

        FontRenderer.renderAll()
        Window.build()

    }

    fun addMessage(username: String, content: String) {

        if(messages.size == 5) {
            messages.removeAt(0)
        }

        messages.add(Pair(username, content))

    }

    fun onConnectExp(port: Int) {
        // No server on port $port running
    }

}

