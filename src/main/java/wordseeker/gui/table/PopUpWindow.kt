package wordseeker.gui.table

import javax.swing.*
import java.awt.*
import java.awt.event.ActionEvent
import java.awt.event.ActionListener

/**
 * @author rad1kal
 */
internal class PopUpWindow(owner: JFrame, text: String, x: Int, y: Int) : JWindow(owner) {

    init {
        val jta = JTextArea(text)
        jta.lineWrap = true
        jta.font = Font("Verdana", Font.PLAIN, 12)
        val jsp = JScrollPane(jta)
        jsp.horizontalScrollBarPolicy = JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        val btn = JButton("close")
        btn.isFocusable = false
        btn.addActionListener { dispose() }
        val d = Toolkit.getDefaultToolkit().screenSize
        if (!((x + WINDOW_WIDTH > d.width) or (y + WINDOW_HEIGHT > d.height))) {
            setBounds(x, y, WINDOW_WIDTH, WINDOW_HEIGHT)
        } else
            setBounds(x - WINDOW_WIDTH, y - WINDOW_HEIGHT, WINDOW_WIDTH, WINDOW_HEIGHT)
        contentPane.layout = BorderLayout()
        contentPane.add(jsp, BorderLayout.CENTER)
        contentPane.add(btn, BorderLayout.SOUTH)
    }

    companion object {
        private val WINDOW_WIDTH = 250
        private val WINDOW_HEIGHT = 150
    }
}
