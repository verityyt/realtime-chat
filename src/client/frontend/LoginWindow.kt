package client.frontend

import client.frontend.listener.LoginClickListener
import java.awt.*
import java.io.File
import javax.imageio.ImageIO
import javax.swing.JComponent
import javax.swing.JFrame
import javax.swing.WindowConstants

class LoginWindow {

    lateinit var window: JFrame
    lateinit var component: JComponent

    lateinit var usernameInput: client.frontend.widgets.TextField
    lateinit var chatIdInput: client.frontend.widgets.TextField
    lateinit var chatPwInput: client.frontend.widgets.TextField

    fun build() {

        component = object : JComponent() {
            override fun paint(g: Graphics) {
                val g2 = g as Graphics2D

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)

                val wallpaper = ImageIO.read(File("assets/images/wallpaper.png"))
                g.drawImage(wallpaper, 0, 0, 1200, 700, this)

                val avatar = ImageIO.read(File("assets/images/avatar.png"))
                g.drawImage(avatar, 525, 172, 150, 150, this)

                usernameInput = client.frontend.widgets.TextField(g, g2, Color.white, 3f, 400, 360, 25f,"Username")
                usernameInput.draw()

                chatIdInput = client.frontend.widgets.TextField(g, g2, Color.white, 3f, 400, 420, 25f,"Chat-ID")
                chatIdInput.draw()

                chatPwInput = client.frontend.widgets.TextField(g, g2, Color.white, 3f, 400, 480, 25f,"Chat-PW")
                chatPwInput.draw()

            }

            override fun update(g: Graphics) {
                paint(g)
            }
        }

        window = JFrame()
        window.add(component)

        window.defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE

        window.addMouseListener(LoginClickListener())

        window.isUndecorated = false
        window.setSize(1200, 700)
        window.isResizable = false
        window.isAlwaysOnTop = false
        window.title = "Realtime Chat | Login"

        window.isVisible = true

        Thread {
            while (true) {
                Thread.sleep(1000 / 60)
                window.repaint()
            }
        }.start()

    }

}