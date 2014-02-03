package wordseeker.gui.table;

import wordseeker.gui.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

/**
 * @author rad1kal
 */
class TableListener extends MouseAdapter {
    private final JTable table;

    /**
     * Class constructor.
     *
     * @param table target table
     */
    public TableListener(JTable table) {
        this.table = table;
    }

    /**
     * Here we describe what happens when user press mouse left button on a
     * cell.
     *
     * @param e
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        //get coordinates of the table cell
        int selectedRow = table.getSelectedRow();
        int selectedColumn = table.getSelectedColumn();

        if (selectedColumn == 0) {

            //get the name of the file shown in table cell
            String fileName = (String) table.getModel().getValueAt(selectedRow, selectedColumn);

            if (!Desktop.isDesktopSupported()) {
                JOptionPane.showMessageDialog(null, "Not possible", fileName, JOptionPane.WARNING_MESSAGE);
            } else {
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.OPEN)) {
                    try {
                        desktop.open(new File(fileName));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }

        } else if (selectedColumn == 1) {

            int x = e.getXOnScreen();
            int y = e.getYOnScreen();

            String text = (String) table.getModel().getValueAt(
                    selectedRow,
                    selectedColumn);

            new PopUpWindow(MainWindow.getFrame(), text, x, y).setVisible(true);
        }
    }
}
