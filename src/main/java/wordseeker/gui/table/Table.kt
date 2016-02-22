package wordseeker.gui.table

import java.awt.Font
import javax.swing.JTable
import javax.swing.ListSelectionModel

/**
 * @author rad1kal
 */
class Table : JTable(TableModel.instance) {

    init {
        font = Font("Verdana", Font.PLAIN, 12)
        dragEnabled = false
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION)
        addMouseListener(TableListener(this))
        autoCreateRowSorter = true
    }

}
