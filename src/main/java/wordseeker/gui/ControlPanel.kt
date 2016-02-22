package wordseeker.gui

import javax.swing.*
import javax.swing.border.*
import java.awt.*
import java.awt.event.*

/**
 * User: rad1kal
 * Date: 17.03.13
 */
internal class ControlPanel : JPanel(FlowLayout(FlowLayout.LEFT)) {
    private val findText = RadioButton("Искать текст")
    private val findRegExp = RadioButton("Искать регулярное выражение")
    private val isCaseSensitive = RadioButton("Учитывать регистр")


    init {
        background = Color.LIGHT_GRAY
        val d = BorderFactory.createLineBorder(Color.DARK_GRAY)
        border = BorderFactory.createTitledBorder(d,
                "Условия поиска", TitledBorder.LEFT, TitledBorder.CENTER,
                Font(FONT_NAME, Font.PLAIN, 12))
        add(findText, FlowLayout.LEFT)
        add(findRegExp, FlowLayout.LEFT)
        add(isCaseSensitive)
        isCaseSensitive.isVisible = false
        val bg = ButtonGroup()
        bg.add(findRegExp)
        bg.add(findText)
        findRegExp.isSelected = true
        findText.addActionListener(Listener())
        findRegExp.addActionListener(Listener())
    }

    private inner class Listener : ActionListener {
        override fun actionPerformed(e: ActionEvent) {
            if (findText.isSelected)
                isCaseSensitive.isVisible = true
            else
                isCaseSensitive.isVisible = false
        }
    }

    private class RadioButton internal constructor(text: String) : JRadioButton(text) {
        init {
            isFocusable = false
            font = Font(FONT_NAME, Font.PLAIN, 14)
            background = Color.LIGHT_GRAY
            foreground = Color.DARK_GRAY
        }
    }

    fun isCaseSensitive(): Boolean {
        return isCaseSensitive.isSelected
    }

    val isRegExpUsed: Boolean
        get() = findRegExp.isSelected

    companion object {
        val FONT_NAME = "Verdana"
    }
}
/**



 */
