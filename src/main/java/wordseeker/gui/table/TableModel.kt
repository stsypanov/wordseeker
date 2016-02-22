package wordseeker.gui.table

import javax.swing.table.AbstractTableModel
import java.util.ArrayList

/**
 * The class instantiates AbstractTableModel and used as table model in Table.

 * @author rad1kal
 */
class TableModel
/**
 * Construct class instance and initialise entryList.
 */
private constructor() : AbstractTableModel() {
    val listOfEntries: ArrayList<TableEntry>?

    init {
        listOfEntries = ArrayList<TableEntry>()
    }

    override fun getRowCount(): Int {
        if (listOfEntries != null)
            return listOfEntries.size
        else
            return 1
    }

    override fun getColumnCount(): Int {
        return 2
    }

    override fun getColumnName(c: Int): String {
        when (c) {
            0 -> return "Путь к файлу"
            1 -> return "Найденная строка"
            else -> return ""
        }
    }

    override fun isCellEditable(row: Int, col: Int): Boolean {
        when (col) {
            0 -> return false
            1 -> return true
            else -> return false
        }
    }

    override fun getValueAt(rowIndex: Int, columnIndex: Int): Any? {
        if (listOfEntries != null) {
            when (columnIndex) {
                0 -> return listOfEntries[rowIndex].pathToFile
                1 -> return listOfEntries[rowIndex].foundInFile
            }
        }
        return null
    }

    companion object {
        private var table: TableModel = TableModel()

        val instance: TableModel
            @Synchronized get() {
                return table
            }
    }
}
