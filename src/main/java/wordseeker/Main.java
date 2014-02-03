package wordseeker;

import wordseeker.gui.MainWindow;

import javax.swing.*;

/**
 * @author rad1kal
 */
class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainWindow().setVisible(true);
            }
        });
    }
}
