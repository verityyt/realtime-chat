package client.frontend.listener

import client.frontend.Window
import java.awt.event.KeyEvent
import java.awt.event.KeyListener

class KeyListener : KeyListener {

    override fun keyTyped(e: KeyEvent?) {}

    override fun keyPressed(e: KeyEvent?) {}

    override fun keyReleased(e: KeyEvent?) {
        if (e != null) {
            Window.usernameInput.onRelease(e)
            Window.chatIdInput.onRelease(e)
            Window.chatTextInput.onRelease(e)
        }
    }

}