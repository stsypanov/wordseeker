
package wordseeker.gui.table;

import java.awt.Font;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

/**
 * @author rad1kal
 */
public class Table extends JTable {

    public Table() {
        super(TableModel.getInstance());
        setFont(new Font("Verdana", Font.PLAIN, 12));
        setDragEnabled(false);
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        addMouseListener(new TableListener(this));
        setAutoCreateRowSorter(true);
    }

}
