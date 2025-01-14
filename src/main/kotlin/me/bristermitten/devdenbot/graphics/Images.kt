package me.bristermitten.devdenbot.graphics

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.awt.*
import java.awt.geom.Rectangle2D
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO

/**
 * @author AlexL
 */

private const val DEFAULT_FONT_NAME = "Horta"

fun BufferedImage.toPNG(): ByteArray {
    val out = ByteArrayOutputStream()
    ImageIO.write(this, "png", out)

    return out.toByteArray()
}

fun pickOptimalFontSize(g: Graphics2D, name: String, text: String, width: Int, height: Int): Int {
    var rect: Rectangle2D
    var fontSize = 100
    do {
        fontSize--
        val font = Font(name, Font.PLAIN, fontSize)
        rect = getStringBoundsRectangle2D(g, text, font)
    } while (rect.width >= width || rect.height >= height)
    return fontSize
}

private fun getStringBoundsRectangle2D(g: Graphics, title: String, font: Font): Rectangle2D {
    g.font = font
    val fm = g.fontMetrics
    return fm.getStringBounds(title, g)
}

suspend fun createTextImage(
    text: String,
    width: Int = 200,
    height: Int = 100,
    backgroundColor: Paint,
    fontColor: Paint,
    fontName: String = DEFAULT_FONT_NAME,
    fontSize: Int? = null
): ByteArray =
    withContext(Dispatchers.IO) {
        val img = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)

        val g2d = img.createGraphics()
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON)
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON)

        g2d.paint = backgroundColor
        g2d.fillRect(0, 0, img.width, img.height)

        val bestSize = fontSize ?: pickOptimalFontSize(g2d, fontName, text, img.width, img.height)
        val font = Font(fontName, Font.PLAIN, bestSize)

        g2d.paint = fontColor

        g2d.font = font

        //Get center
        val fm = g2d.fontMetrics
        val x = (img.width - fm.stringWidth(text)) / 2
        val y = (img.height - fm.height) / 2 + fm.ascent

        g2d.drawString(text, x, y)
        g2d.dispose()

        img.toPNG()
    }
