package client.frontend.widgets

import client.Client
import client.backend.ChatManager
import client.frontend.Window
import client.frontend.utils.FontRenderer
import java.awt.BasicStroke
import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.event.KeyEvent
import java.awt.geom.RoundRectangle2D
import java.awt.image.ImageObserver
import java.io.File
import javax.imageio.ImageIO

class CleanTextFieldWidget(
    private var color: Color,
    private val focusedColor: Color,
    private var x: Int,
    private var y: Int,
    private val arc: Float,
    private val preview: String,
    private val allowedKeys: String = "ALL",
    private val maxLength: Int = 16
) {

    var isFocused = false
    var text = ""

    var y2 = 0
    var sendAdding = 0

    fun draw(
        g: Graphics,
        g2: Graphics2D,
        observer: ImageObserver
    ) {

        if (text == "" && !isFocused) {
            g2.paint = if (isFocused) {
                focusedColor
            } else {
                color
            }
            g2.fill(RoundRectangle2D.Float(x.toFloat(), y.toFloat() - 5f, 900f, 55f, arc, arc))

            val send = ImageIO.read(File("assets/images/send.png"))
            g.drawImage(send, x + 850, y + 8, observer)

            g.color = Color.decode("#323637")
            g.font = FontRenderer.regular.deriveFont(25f)
            g.drawString(preview, x + 15, y + 35 - 2)
        } else {
            g.font = FontRenderer.regular.deriveFont(25f)

            val draws = mutableListOf<String>()
            val parts = text.split(" ")
            var result = ""

            for (part in parts) {
                var theory = result
                theory += "$part "
                if (g.fontMetrics.stringWidth(theory) <= (800)) {
                    if (part == parts[parts.size - 1]) {
                        result += "$part "
                        draws.add(result)
                        break
                    } else {
                        result += "$part "
                    }
                } else {
                    draws.add(result)
                    result = ""
                    result += "$part "
                }
            }

            val drawsLasts = draws.last().split(" ")
            if (drawsLasts[drawsLasts.size - 2] != parts.last()) {
                draws.add(parts[parts.size - 1])
            }

            val h = 35 + (draws.size * 15) + 5
            y2 = (y + 15) - (draws.size * 15) - 5

            g2.paint = if (isFocused) {
                focusedColor
            } else {
                color
            }
            g2.fill(RoundRectangle2D.Float(x.toFloat(), y2.toFloat(), 900f, h.toFloat(), arc, arc))

            val send = ImageIO.read(File("assets/images/send.png"))
            g.drawImage(send, x + 850, y2 + 12, observer)

            var nr = 0
            for (draw in draws) {
                g.color = Color.decode("#323637")
                sendAdding = if (draws.size > 1) {
                    32
                } else {
                    35
                }
                g.drawString(draw, x + 15, y2 + sendAdding + (nr * 25))
                nr++
            }

        }

    }

    fun onClick(x: Int, y: Int) {
        isFocused = x > this.x && x < this.x + 400 && y > this.y && y < this.y + 50

        if (x >= (this.x + 850) && x <= ((this.x + 850) + 40) && y >= (y2 + 12) && y <= ((y2 + 12) + 40)) {
            if (text != "") {
                text = text.replace("ä", "#ae#")
                text = text.replace("ö", "#oe#")
                text = text.replace("ü", "#ue#")
                ChatManager.sendPaket("MESSAGE", text)
                Client.addMessage(Window.username, text)
                text = ""
            }
        }

    }

    fun onRelease(e: KeyEvent) {
        if (isFocused) {
            if (e.keyCode == 27) {
                isFocused = false
            } else if (e.keyCode == 8 && text != "") {
                text = text.substring(0, text.length - 1)
            }else if(e.keyCode == 10) {
                if (text != "") {
                    text = text.replace("ä", "#ae#")
                    text = text.replace("ö", "#oe#")
                    text = text.replace("ü", "#ue#")
                    ChatManager.sendPaket("MESSAGE", text)
                    Client.addMessage(Window.username, text)
                    text = ""
                    isFocused = false
                }
            } else {
                if (text.length < maxLength && isAllowed(e.keyCode, e.keyChar)) {
                    text += e.keyChar.toString()
                }
            }
        }
    }

    fun error() {
        Thread {
            val originalColor = color
            color = Color.red
            x -= 10
            Thread.sleep(50)
            x += 20
            Thread.sleep(50)
            x -= 20
            Thread.sleep(50)
            x += 20
            Thread.sleep(50)
            x -= 20
            Thread.sleep(50)
            x += 10
            color = originalColor
        }.start()
    }

    private fun isAllowed(keyCode: Int, keyChar: Char): Boolean {
        if (allowedKeys == "ALL") {
            return (keyCode in 44..111) || (keyCode in 160..222) || (keyCode in 515..517) || (keyCode == 153) || (keyCode == 521) || (keyCode == 520) || (keyCode == 129) || (keyCode == 524) || (keyCode == 32) || (keyChar == '?')
        } else if (allowedKeys == "LETTERSNUMBERS") {
            return (keyCode in 44..111)
        } else if (allowedKeys == "NUMBERS") {
            return (keyCode in 48..57) || (keyCode in 96..105)
        }
        return false
    }

}