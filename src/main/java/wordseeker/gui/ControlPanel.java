package wordseeker.gui;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

/**
 * User: rad1kal
 * Date: 17.03.13
 */
class ControlPanel extends JPanel {
    public static final String FONT_NAME = "Verdana";
    private final RadioButton findText = new RadioButton("Искать текст");
    private final RadioButton findRegExp = new RadioButton("Искать регулярное выражение");
    private final RadioButton isCaseSensitive = new RadioButton("Учитывать регистр");

    /**
     *
     *
     *
     **/

    ControlPanel() {
        super(new FlowLayout(FlowLayout.LEFT));
        setBackground(Color.LIGHT_GRAY);
        Border d = BorderFactory.createLineBorder(Color.DARK_GRAY);
        setBorder(BorderFactory.createTitledBorder(d,
                "Условия поиска", TitledBorder.LEFT, TitledBorder.CENTER,
                new Font(FONT_NAME, Font.PLAIN, 12)));
        add(findText, FlowLayout.LEFT);
        add(findRegExp, FlowLayout.LEFT);
        add(isCaseSensitive);
        isCaseSensitive.setVisible(false);
        ButtonGroup bg = new ButtonGroup();
        bg.add(findRegExp);
        bg.add(findText);
        findRegExp.setSelected(true);
        findText.addActionListener(new Listener());
        findRegExp.addActionListener(new Listener());
    }

    private class Listener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (findText.isSelected())
                isCaseSensitive.setVisible(true);
            else
                isCaseSensitive.setVisible(false);
        }
    }

    private static class RadioButton extends JRadioButton {
        RadioButton(String text) {
            super(text);
            setFocusable(false);
            setFont(new Font(FONT_NAME, Font.PLAIN, 14));
            setBackground(Color.LIGHT_GRAY);
            setForeground(Color.DARK_GRAY);
        }
    }

    public boolean isCaseSensitive() {
        return isCaseSensitive.isSelected();
    }

    public boolean isRegExpUsed() {
        return findRegExp.isSelected();
    }
}
