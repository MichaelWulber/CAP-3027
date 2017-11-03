// Michael Wulber
// CAP 3027
// Java 3D Tutorial

import javax.swing.*;
import java.awt.*;

public class Launcher {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI(){
        ApplicationWindow window = new ApplicationWindow(Toolkit.getDefaultToolkit().getScreenSize());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.pack();
        window.setVisible(true);
    }

}