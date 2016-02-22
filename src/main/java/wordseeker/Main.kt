package wordseeker

import wordseeker.gui.MainWindow

import javax.swing.*

internal object Main {

    @JvmStatic fun main(args: Array<String>) {

        SwingUtilities.invokeLater { MainWindow().isVisible = true }
    }
}
