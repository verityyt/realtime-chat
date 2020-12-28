package client.frontend.widgets

import client.frontend.utils.FontRenderer
import java.awt.BasicStroke
import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.geom.RoundRectangle2D

class TextField(
    val g: Graphics,
    val g2: Graphics2D,
    val color: Color,
    val stroke: Float,
    val x: Int,
    val y: Int,
    val arc: Float,
    val preview: String
) {

    private var isFocused = false

    fun draw() {

        g2.color = color
        g2.stroke = BasicStroke(stroke)
        g2.draw(RoundRectangle2D.Float(x.toFloat(), y.toFloat(), 400f, 50f, arc, arc))

        g.color = Color.white.darker()
        g.font = FontRenderer.regular.deriveFont(30f)
        g.drawString(preview, x + 15, y + 35)

    }

    fun onClick(x: Int, y: Int) {
        isFocused = x > this.x && x < this.x + 400 && y > this.y && y < this.y + 50
    }

}