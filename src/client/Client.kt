package client

import client.frontend.LoginWindow
import client.frontend.utils.FontRenderer

object Client {

    lateinit var loginWindow: LoginWindow

    fun start() {

        FontRenderer.renderAll()
        loginWindow = LoginWindow()
        loginWindow.build()

    }

}

fun main() {
    Client.start()
}