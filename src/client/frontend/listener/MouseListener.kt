package client.frontend.listener

import client.frontend.Window
import java.awt.event.MouseEvent
import java.awt.event.MouseListener

class MouseListener : MouseListener {

    override fun mouseClicked(e: MouseEvent?) {
        if (e != null) {
            Window.usernameInput.onClick(e.x, e.y)
            Window.chatIdInput.onClick(e.x, e.y)
            Window.enterButton.onClick(e.x, e.y)
            Window.chatTextInput.onClick(e.x,e.y)
        }
    }

    override fun mousePressed(e: MouseEvent?) {}

    override fun mouseReleased(e: MouseEvent?) {}

    override fun mouseEntered(e: MouseEvent?) {}

    override fun mouseExited(e: MouseEvent?) {}

}