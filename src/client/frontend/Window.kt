package client.frontend

import client.Client
import client.backend.ChatManager
import client.frontend.listener.MouseListener
import client.frontend.listener.KeyListener
import client.frontend.utils.FontRenderer
import client.frontend.utils.WindowContent
import client.frontend.widgets.ButtonWidget
import client.frontend.widgets.CleanTextFieldWidget
import client.frontend.widgets.MessageWidget
import client.frontend.widgets.TextFieldWidget
import java.awt.*
import java.awt.geom.RoundRectangle2D
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import javax.imageio.ImageIO
import javax.swing.JComponent
import javax.swing.JFrame
import javax.swing.WindowConstants

object Window {

    var content = WindowContent.CHAT
    lateinit var frame: JFrame
    private lateinit var component: JComponent

    var username = ""
    var chatId = ""
    var state = "Login"

    var usernameInput = TextFieldWidget(Color.white, Color.gray, 3f, 400, 360, 25f, "Username", "LETTERSNUMBERS")
    var chatIdInput = TextFieldWidget(Color.white, Color.gray, 3f, 400, 420, 25f, "Chat-ID", "NUMBERS", 4)
    var enterButton = ButtonWidget(Color.white, 3f, 400, 480, 25f, "Enter") {
        if (usernameInput.text == "") {
            usernameInput.error()
        }
        if (chatIdInput.text == "") {
            chatIdInput.error()
        }
        if (usernameInput.text != "" && chatIdInput.text != "") {
            username = usernameInput.text
            chatId = chatIdInput.text
            ChatManager.startChat(chatId.toInt(), username)
            content = WindowContent.CHAT
        }
    }
    var chatTextInput =
        CleanTextFieldWidget(Color(255, 255, 255, 150), Color.white, 10, 120/*610*/, 20f, "Write text...", maxLength = 200)

    fun build() {
        FontRenderer.renderAll()

        component = object : JComponent() {
            override fun paint(g: Graphics) {
                val g2 = g as Graphics2D

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)

                if (content == WindowContent.LOGIN) {
                    val wallpaper = ImageIO.read(File("assets/images/login_wallpaper.png"))
                    g.drawImage(wallpaper, 0, 0, 1200, 700, this)

                    val avatar = ImageIO.read(File("assets/images/avatar.png"))
                    g.drawImage(avatar, 525, 172, 150, 150, this)

                    usernameInput.draw(g, g2)
                    chatIdInput.draw(g, g2)
                    enterButton.draw(g, g2)
                } else if(content == WindowContent.CHAT) {
                    val wallpaper = ImageIO.read(File("assets/images/chat_wallpaper.png"))
                    g.drawImage(wallpaper, 0, 0, 1200, 700, this)

                    g2.paint = Color(255, 255, 255, 150)
                    g2.fill(RoundRectangle2D.Float(940f, 0f, 270f, 675f, 15f, 15f))

                    g.color = Color.black
                    g.font = FontRenderer.regular.deriveFont(15f)
                    g.drawString("Username: $username", 950, 20)
                    g.drawString("Chat-ID: $chatId", 950, 40)

                    g.font = FontRenderer.bold.deriveFont(40f)
                    g.drawString(SimpleDateFormat("HH:mm").format(Date()), 1070, 630)

                    g.font = FontRenderer.regular.deriveFont(25f)
                    g.drawString(SimpleDateFormat("dd.MMMMM yyyy").format(Date()), 960, 655)

                    chatTextInput.draw(g, g2, this)

                    var number = 1
                    for (message in Client.messages) {
                        MessageWidget(message.first, message.second, number).draw(g, g2)
                        number++
                    }

                    state = "Chat #$chatId"
                }
            }

            override fun update(g: Graphics) {
                paint(g)
            }
        }

        frame = JFrame()
        frame.add(component)

        frame.defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE

        frame.addMouseListener(MouseListener())
        frame.addKeyListener(KeyListener())

        frame.isUndecorated = false
        frame.setSize(1200, 700)
        frame.isResizable = false
        frame.isAlwaysOnTop = false
        frame.title = "Realtime Chat | $state"

        frame.iconImage = ImageIO.read(File("assets/images/icon.png"))

        frame.isVisible = true

        Thread {
            while (true) {
                Thread.sleep(1000 / 60)
                frame.repaint()
            }
        }.start()

    }

}