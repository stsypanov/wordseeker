package wordseeker.gui;

import java.awt.Dimension;

import static java.awt.Frame.MAXIMIZED_BOTH;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;

import static javax.swing.JFrame.EXIT_ON_CLOSE;

import javax.swing.KeyStroke;
import javax.swing.UIManager;

/**
 * @author stsypanov
 */
public class MainWindow extends JFrame {

    private static JFrame window;

    public MainWindow() throws HeadlessException {
        super("WordSeeker: поиск в текстовых документах");
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        window = this;
        add(new MainPanel());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 600));
        pack();
        setExtendedState(MAXIMIZED_BOTH);

        InputMap im = getRootPane().getInputMap();
        ActionMap am = getRootPane().getActionMap();
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "exit");
        am.put("exit", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.dispose();
                System.exit(0);
            }
        });

        getRootPane().setInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT, im);
    }

    public static JFrame getFrame() {
        return window;
    }
}
