package client.frontend.utils

import java.awt.Font
import java.awt.GraphicsEnvironment
import java.io.File

object FontRenderer {

    lateinit var regular: Font

    fun renderAll() {
        renderRegular()
    }

    private fun renderRegular() {
        try {

            regular = Font.createFont(Font.TRUETYPE_FONT, File("assets/fonts/rubik/regular.ttf"))
            val ge = GraphicsEnvironment.getLocalGraphicsEnvironment()
            ge.registerFont(regular)

        }catch (e: Exception) {
            e.printStackTrace()
        }
    }

}