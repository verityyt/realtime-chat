package client.frontend.listener

import client.Client
import java.awt.event.KeyEvent
import java.awt.event.KeyListener

class LoginKeyListener : KeyListener {

    override fun keyTyped(e: KeyEvent?) {}

    override fun keyPressed(e: KeyEvent?) {}

    override fun keyReleased(e: KeyEvent?) {
        if (e != null) {
            Client.loginWindow.usernameInput.onRelease(e)
            Client.loginWindow.chatIdInput.onRelease(e)
        }
    }

}