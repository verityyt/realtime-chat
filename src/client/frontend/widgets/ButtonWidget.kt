package client.frontend.widgets

import client.frontend.utils.FontRenderer
import java.awt.BasicStroke
import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.event.KeyEvent
import java.awt.geom.RoundRectangle2D

class ButtonWidget(
    private val color: Color,
    private val stroke: Float,
    private val x: Int,
    private val y: Int,
    private val arc: Float,
    private val text: String,
    private val action: () -> Unit
) {

    fun draw(
        g: Graphics,
        g2: Graphics2D
    ) {

        g2.paint = color
        g2.stroke = BasicStroke(stroke)
        g2.fill(RoundRectangle2D.Float(x.toFloat(), y.toFloat(), 400f, 50f, arc, arc))

        g.color = Color.black
        g.font = FontRenderer.regular.deriveFont(30f)
        g.drawString(text, x + 15, y + 35)

    }

    fun onClick(x: Int, y: Int) {
        if (x > this.x && x < this.x + 400 && y > this.y + 20 && y < this.y + 50 + 20) {
            action()
        }
    }

}