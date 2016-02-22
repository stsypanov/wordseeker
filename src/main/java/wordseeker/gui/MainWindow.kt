package wordseeker.gui

import javax.swing.*
import java.awt.*
import java.awt.event.ActionEvent
import java.awt.event.KeyEvent

/**
 * @author stsypanov
 */
class MainWindow @Throws(HeadlessException::class)
constructor() : JFrame("WordSeeker: поиск в текстовых документах") {

    init {
        try {
            for (info in UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus" == info.name) {
                    UIManager.setLookAndFeel(info.className)
                    break
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        frame = this
        add(MainPanel())
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        preferredSize = Dimension(800, 600)
        pack()
        extendedState = Frame.MAXIMIZED_BOTH

        val im = getRootPane().inputMap
        val am = getRootPane().actionMap
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "exit")
        am.put("exit", object : AbstractAction() {
            override fun actionPerformed(e: ActionEvent) {
                frame.dispose()
                System.exit(0)
            }
        })

        getRootPane().setInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT, im)
    }

    companion object {

        var frame: JFrame = JFrame()
    }
}
