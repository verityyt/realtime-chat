package client.frontend.widgets

import client.Client
import client.frontend.Window
import client.frontend.utils.FontRenderer
import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.geom.RoundRectangle2D

class MessageWidget(
    private val username: String,
    private val message: String,
    private val number: Int
) {

    fun draw(
        g: Graphics,
        g2: Graphics2D
    ) {

        var x = 0
        var h = 100
        var color = Color.white
        val w = 400

        val y = if(number == 1) {
            38
        }else {
            38 + (number * 75)
        }

        if(username == Window.username) {
            x = 500
            color = Color.decode("#159958")
        }else {
            x = 27
            color = Color.decode("#0066FF")
        }

        g.font = FontRenderer.regular.deriveFont(15f)
        val draws = mutableListOf<String>()
        val parts = message.split(" ")
        var result = ""

        for (part in parts) {
            var theory = result
            theory += "$part "
            if (g.fontMetrics.stringWidth(theory) <= (w - 20)) {
                if(part == parts[parts.size - 1]) {
                    result += "$part "
                    draws.add(result)
                    break
                }else {
                    result += "$part "
                }
            } else {
                draws.add(result)
                result = ""
                result += "$part "
            }
        }

        val drawsLasts = draws.last().split(" ")
        if(drawsLasts[drawsLasts.size - 2] != parts.last()) {
            draws.add(parts[parts.size - 1])
        }

        h = 30 + (draws.size * 15) + 5

        g2.paint = color
        g2.fill(RoundRectangle2D.Float(x.toFloat(), y.toFloat(), w.toFloat(), h.toFloat(), 20f, 20f))

        g.color = Color.white
        g.font = FontRenderer.bold.deriveFont(15f)
        g.drawString(username, x + 10, y + 20)

        g.font = FontRenderer.regular.deriveFont(15f)

        var nr = 1
        for(draw in draws) {
            g.drawString(draw, x + 10, y + 25 + (nr * 15))
            nr++
        }

    }

}