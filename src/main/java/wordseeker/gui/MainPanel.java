
package wordseeker.gui;

import wordseeker.Parser;
import wordseeker.gui.table.Table;
import wordseeker.gui.table.TableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * User: rad1kal
 * Date: 14.03.13
 */
public class MainPanel extends JPanel {
    private final InputField inputField;
    private final Button searchButton = new Button("Недостаточно данных для поиска");
    private File rootFolder;
    private final FileTypePanel fileTypePanel = new FileTypePanel();
    private final ControlPanel controlPanel;
    private static JScrollPane jsp;
    private final JTable table;

    public MainPanel() {
        super(new GridBagLayout());
        setBorder(null);
        setBackground(Color.LIGHT_GRAY);
        Label label = new Label("Введите искомую строку:");
        GridBagConstraints c = new GridBagConstraints(0, 0, 2, 1, 1, 0,
                GridBagConstraints.WEST, GridBagConstraints.BOTH,
                new Insets(10, 10, 5, 10),
                0, 0);
        add(label, c);
        /**
         *
         *
         *
         *
         *
         */

        c.insets = new Insets(5, 10, 5, 10);
        c.gridy = 1;
        inputField = new InputField();
        inputField.addKeyListener(new InputFieldListener());
        add(inputField, c);
        /**
         *
         *
         */

        c.gridy = 2;
        c.gridwidth = 2;
        c.weightx = 1;
        JPanel browsePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        browsePanel.setBackground(Color.LIGHT_GRAY);
        browsePanel.add(new Label("Выберите каталог: "));
        Button browseButton = new Button("Обзор");
        browsePanel.add(browseButton);
        browseButton.addActionListener(new BrowseButtonListener());
        add(browsePanel, c);

        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2;
        controlPanel = new ControlPanel();
        add(controlPanel, c);

        c.gridy = 4;
        add(fileTypePanel, c);

        c.gridy = 5;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.CENTER;

        searchButton.setEnabled(false);
        searchButton.addActionListener(new SearchButtonListener());
        searchButton.setEnabled(false);
        add(searchButton, c);

        table = new Table();
        jsp = new JScrollPane(table);
        jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 6;
        c.weighty = 1;
        c.gridwidth = 2;
        c.gridheight = 1;
        c.weightx = 1;
        add(jsp, c);
    }

    /**
     * @return the jsp
     */
    public static JScrollPane getJsp() {
        return jsp;
    }

    /**
     * Private class for panel labels.
     */
    private static class Label extends JLabel {
        Label(String text) {
            super(text);
            setFont(new Font("Verdana", Font.PLAIN, 12));
            setForeground(Color.DARK_GRAY);
            setFocusable(false);
        }
    }

    private static class Button extends JButton {
        Button(String text) {
            super(text);
            setFont(new Font("Verdana", Font.BOLD, 12));
            setFocusable(false);
        }
    }

    private class InputFieldListener extends KeyAdapter {
        @Override
        public void keyReleased(KeyEvent e) {
            if (!inputField.getText().isEmpty() & rootFolder != null) {
                searchButton.setText("Поиск");
                searchButton.setEnabled(true);
            } else if (inputField.getText().isEmpty() & rootFolder != null) {
                searchButton.setText("Введите строку для поиска");
                searchButton.setEnabled(false);
            } else if (!inputField.getText().isEmpty() & rootFolder == null) {
                searchButton.setText("Укажите корневой каталог");
                searchButton.setEnabled(false);
            } else {
                searchButton.setText("Недостаточно данных для поиска");
                searchButton.setEnabled(false);
            }
        }
    }

    private class BrowseButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser folderBrowser = new JFileChooser("D:\\");
            folderBrowser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int result = folderBrowser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                rootFolder = folderBrowser.getSelectedFile();
                if (!inputField.getText().isEmpty()) {
                    searchButton.setText("Поиск");
                    searchButton.setEnabled(true);
                }
            }
        }
    }

    private class SearchButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            boolean docxSelected = fileTypePanel.isDocxSelected();
            boolean docSelected = fileTypePanel.isDocSelected();
            boolean rtfSelected = fileTypePanel.isRtfSelected();
            boolean txtSelected = fileTypePanel.isTxtSelected();
            boolean regExpUsed = controlPanel.isRegExpUsed();

            //clear the data in table
            ((TableModel) table.getModel()).getListOfEntries().clear();
            ((TableModel) table.getModel()).fireTableDataChanged();

            try {
                Parser parser;
                if (regExpUsed) {
                    String regExp = inputField.getText();
                    parser = new Parser(rootFolder, regExp,
                            docxSelected, docSelected,
                            rtfSelected, txtSelected);
                } else {
                    String toBeFound = inputField.getText();
                    boolean isCaseSensitive = controlPanel.isCaseSensitive();
                    parser = new Parser(rootFolder, toBeFound, isCaseSensitive,
                            docxSelected, docSelected,
                            rtfSelected, txtSelected);
                }
                new Thread(parser).start();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }

    }
}
