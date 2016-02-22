package wordseeker.gui

import javax.swing.*
import java.awt.*

/**
 * User: rad1kal
 * Date: 15.03.13
 */
internal class InputField : JTextField() {

    init {
        background = Color.DARK_GRAY
        font = Font("Verdana", Font.BOLD, 14)
        foreground = Color.LIGHT_GRAY
        val d = Dimension(200, 30)
        preferredSize = d
        minimumSize = d
        border = null
        caretColor = Color.white
    }
}
