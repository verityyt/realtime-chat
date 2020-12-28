package client.frontend.widgets

import client.frontend.utils.FontRenderer
import java.awt.BasicStroke
import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.event.KeyEvent
import java.awt.geom.RoundRectangle2D

class TextField(
    val color: Color,
    val stroke: Float,
    val x: Int,
    val y: Int,
    val arc: Float,
    val preview: String
) {

    private var isFocused = false
    var text = ""

    fun draw(
        g: Graphics,
        g2: Graphics2D
    ) {

        g2.color = color
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
        println(isFocused)
    }

    fun onRelease(e: KeyEvent) {
        if (isFocused) {
            if (e.keyCode == 27) {
                isFocused = false
            } else if (e.keyCode == 8 && text != "") {
                text = text.substring(0, text.length - 1)
            } else {
                if (text.length < 16) {
                    text += e.keyChar.toString()
                }
            }
        }
    }

}