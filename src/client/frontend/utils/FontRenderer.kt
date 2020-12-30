package client.frontend.utils

import java.awt.Font
import java.awt.GraphicsEnvironment
import java.io.File

object FontRenderer {

    lateinit var regular: Font
    lateinit var bold: Font

    fun renderAll() {
        renderRegular()
        renderBold()
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

    private fun renderBold() {
        try {

            bold = Font.createFont(Font.TRUETYPE_FONT, File("assets/fonts/rubik/bold.ttf"))
            val ge = GraphicsEnvironment.getLocalGraphicsEnvironment()
            ge.registerFont(bold)

        }catch (e: Exception) {
            e.printStackTrace()
        }
    }

}