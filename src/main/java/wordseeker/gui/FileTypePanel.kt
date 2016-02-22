package wordseeker.gui

import javax.swing.*
import javax.swing.border.*
import java.awt.*

/**
 * User: rad1kal
 * Date: 19.03.13
 */
internal class FileTypePanel : JPanel(FlowLayout(FlowLayout.LEFT)) {
    private val docx: JCheckBox
    private val doc: JCheckBox
    private val txt: JCheckBox
    private val rtf: JCheckBox

    init {
        background = Color.LIGHT_GRAY
        val d = BorderFactory.createLineBorder(Color.DARK_GRAY)
        border = BorderFactory.createTitledBorder(d,
                "Расширения просматриваемых файлов", TitledBorder.LEFT, TitledBorder.CENTER,
                Font("Verdana", Font.PLAIN, 12))

        docx = CheckBox("docx")
        doc = CheckBox("doc")
        txt = CheckBox("txt")
        rtf = CheckBox("rtf")

        add(docx)
        add(doc)
        add(txt)
        add(rtf)

    }

    val isDocxSelected: Boolean
        get() = docx.isSelected

    val isDocSelected: Boolean
        get() = doc.isSelected

    val isRtfSelected: Boolean
        get() = rtf.isSelected

    val isTxtSelected: Boolean
        get() = txt.isSelected

    private class CheckBox internal constructor(text: String) : JCheckBox(text) {
        init {
            isFocusable = false
            background = Color.LIGHT_GRAY
        }
    }
}
