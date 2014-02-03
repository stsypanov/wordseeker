package wordseeker.gui;

import javax.swing.*;
import java.awt.*;

/**
 * User: rad1kal
 * Date: 15.03.13
 */
class InputField extends JTextField {

    InputField() {
        super();
        setBackground(Color.DARK_GRAY);
        setFont(new Font("Verdana", Font.BOLD, 14));
        setForeground(Color.LIGHT_GRAY);
        Dimension d = new Dimension(200, 30);
        setPreferredSize(d);
        setMinimumSize(d);
        setBorder(null);
        setCaretColor(Color.white);
    }
}
