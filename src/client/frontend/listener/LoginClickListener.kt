package client.frontend.listener

import client.Client
import java.awt.event.MouseEvent
import java.awt.event.MouseListener

class LoginClickListener : MouseListener {

    override fun mouseClicked(e: MouseEvent?) {
        if (e != null) {
            Client.loginWindow.usernameInput.onClick(e.x, e.y)
            Client.loginWindow.chatIdInput.onClick(e.x, e.y)
            Client.loginWindow.chatPwInput.onClick(e.x, e.y)
        }
    }

    override fun mousePressed(e: MouseEvent?) {}

    override fun mouseReleased(e: MouseEvent?) {}

    override fun mouseEntered(e: MouseEvent?) {}

    override fun mouseExited(e: MouseEvent?) {}

}