
package wordseeker.gui.table;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * The class instantiates AbstractTableModel and used as table model in Table.
 *
 * @author rad1kal
 */
public class TableModel extends AbstractTableModel {
    private final List<TableEntry> entryList;
    private static TableModel table;

    /**
     * Construct class instance and initialise entryList.
     */
    private TableModel() {
        entryList = new ArrayList<TableEntry>();
    }

    public static synchronized TableModel getInstance() {
        if (table == null)
            table = new TableModel();
        return table;
    }

    public List<TableEntry> getListOfEntries() {
        return entryList;
    }

    @Override
    public int getRowCount() {
        if (entryList != null)
            return entryList.size();
        else
            return 1;
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public String getColumnName(int c) {
        switch (c) {
            case 0:
                return "Путь к файлу";
            case 1:
                return "Найденная строка";
            default:
                return "";
        }
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        switch (col) {
            case 0:
                return false;
            case 1:
                return true;
            default:
                return false;
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (entryList != null) {
            switch (columnIndex) {
                case 0:
                    return entryList.get(rowIndex).getPathToFile();
                case 1:
                    return entryList.get(rowIndex).getFoundInFile();
            }
        }
        return null;
    }
}
