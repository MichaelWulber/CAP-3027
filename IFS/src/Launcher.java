// Michael Wulber
// CAP 3027
// HW07

import javax.swing.*;

public class Launcher {

    private static final int WIDTH = 1000;
    private static final int HEIGHT = 1000;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI(){
        JFrame frame = new ImageFrame(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

}