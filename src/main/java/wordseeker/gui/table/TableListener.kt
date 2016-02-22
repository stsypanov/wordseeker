package wordseeker.gui.table

import wordseeker.gui.MainWindow

import javax.swing.*
import java.awt.*
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.io.File

/**
 * @author rad1kal
 */
internal class TableListener
/**
 * Class constructor.

 * @param table target table
 */
(private val table: JTable) : MouseAdapter() {

    /**
     * Here we describe what happens when user press mouse left button on a
     * cell.

     * @param e
     */
    override fun mouseClicked(e: MouseEvent?) {
        //get coordinates of the table cell
        val selectedRow = table.selectedRow
        val selectedColumn = table.selectedColumn

        if (selectedColumn == 0) {

            //get the name of the file shown in table cell
            val fileName = table.model.getValueAt(selectedRow, selectedColumn) as String

            if (!Desktop.isDesktopSupported()) {
                JOptionPane.showMessageDialog(null, "Not possible", fileName, JOptionPane.WARNING_MESSAGE)
            } else {
                val desktop = Desktop.getDesktop()
                if (desktop.isSupported(Desktop.Action.OPEN)) {
                    try {
                        desktop.open(File(fileName))
                    } catch (ex: Exception) {
                        ex.printStackTrace()
                    }

                }
            }

        } else if (selectedColumn == 1) {

            val x = e!!.xOnScreen
            val y = e.yOnScreen

            val text = table.model.getValueAt(
                    selectedRow,
                    selectedColumn) as String

            PopUpWindow(MainWindow.frame, text, x, y).isVisible = true
        }
    }
}
