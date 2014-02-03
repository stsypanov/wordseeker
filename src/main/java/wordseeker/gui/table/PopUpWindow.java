package wordseeker.gui.table;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author rad1kal
 */
class PopUpWindow extends JWindow {
    private static final int WINDOW_WIDTH = 250;
    private static final int WINDOW_HEIGHT = 150;

    public PopUpWindow(JFrame owner, String text, int x, int y) {
        super(owner);
        JTextArea jta = new JTextArea(text);
        jta.setLineWrap(true);
        jta.setFont(new Font("Verdana", Font.PLAIN, 12));
        JScrollPane jsp = new JScrollPane(jta);
        jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        JButton btn = new JButton("close");
        btn.setFocusable(false);
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        if (!((x + WINDOW_WIDTH) > d.width | (y + WINDOW_HEIGHT) > d.height)) {
            setBounds(x, y, WINDOW_WIDTH, WINDOW_HEIGHT);
        } else
            setBounds(x - WINDOW_WIDTH, y - WINDOW_HEIGHT, WINDOW_WIDTH, WINDOW_HEIGHT);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(jsp, BorderLayout.CENTER);
        getContentPane().add(btn, BorderLayout.SOUTH);
    }
}
