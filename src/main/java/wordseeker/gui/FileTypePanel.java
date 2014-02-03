
package wordseeker.gui;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

/**
 * User: rad1kal
 * Date: 19.03.13
 */
class FileTypePanel extends JPanel {
    private final JCheckBox docx;
    private final JCheckBox doc;
    private final JCheckBox txt;
    private final JCheckBox rtf;

    FileTypePanel() {
        super(new FlowLayout(FlowLayout.LEFT));
        setBackground(Color.LIGHT_GRAY);
        Border d = BorderFactory.createLineBorder(Color.DARK_GRAY);
        setBorder(BorderFactory.createTitledBorder(d,
                "Расширения просматриваемых файлов", TitledBorder.LEFT, TitledBorder.CENTER,
                new Font("Verdana", Font.PLAIN, 12)));

        docx = new CheckBox("docx");
        doc = new CheckBox("doc");
        txt = new CheckBox("txt");
        rtf = new CheckBox("rtf");

        add(docx);
        add(doc);
        add(txt);
        add(rtf);

    }

    public boolean isDocxSelected() {
        return docx.isSelected();
    }

    public boolean isDocSelected() {
        return doc.isSelected();
    }

    public boolean isRtfSelected() {
        return rtf.isSelected();
    }

    public boolean isTxtSelected() {
        return txt.isSelected();
    }

    private static class CheckBox extends JCheckBox {
        CheckBox(String text) {
            super(text);
            setFocusable(false);
            setBackground(Color.LIGHT_GRAY);
        }
    }
}
