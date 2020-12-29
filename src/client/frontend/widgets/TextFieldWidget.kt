package client.frontend.widgets

import client.frontend.utils.FontRenderer
import java.awt.BasicStroke
import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.event.KeyEvent
import java.awt.geom.RoundRectangle2D

class TextFieldWidget(
    private var color: Color,
    private val focusedColor: Color,
    private val stroke: Float,
    private var x: Int,
    private var y: Int,
    private val arc: Float,
    private val preview: String,
    private val allowedKeys: String = "ALL",
    private val maxLength: Int = 16
) {

    var isFocused = false
    var text = ""

    fun draw(
        g: Graphics,
        g2: Graphics2D
    ) {

        g2.paint = if (isFocused) {
            focusedColor
        } else {
            color
        }
        g2.stroke = BasicStroke(stroke)
        g2.draw(RoundRectangle2D.Float(x.toFloat(), y.toFloat(), 400f, 50f, arc, arc))

        if (text == "" && !isFocused) {
            g.color = Color.white.darker()
            g.font = FontRenderer.regular.deriveFont(30f)
            g.drawString(preview, x + 15, y + 35)
        } else {
            g.color = Color.white
            g.font = FontRenderer.regular.deriveFont(30f)
            g.drawString(text, x + 15, y + 35)
        }

    }

    fun onClick(x: Int, y: Int) {
        isFocused = x > this.x && x < this.x + 400 && y > this.y && y < this.y + 50
    }

    fun onRelease(e: KeyEvent) {
        if (isFocused) {
            if (e.keyCode == 27) {
                isFocused = false
            } else if (e.keyCode == 8 && text != "") {
                text = text.substring(0, text.length - 1)
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
            return (keyCode in 44..111) || (keyCode in 160..222) || (keyCode in 515..517) || (keyCode == 153) || (keyCode == 521) || (keyCode == 520) || (keyCode == 129) || (keyCode == 524) || (keyChar == '?')
        } else if (allowedKeys == "LETTERSNUMBERS") {
            return (keyCode in 44..111)
        } else if (allowedKeys == "NUMBERS") {
            return (keyCode in 48..57) || (keyCode in 96..105)
        }
        return false
    }

}