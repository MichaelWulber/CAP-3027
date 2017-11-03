import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ApplicationWindow extends JFrame{
    private final Display panel;

    public ApplicationWindow(Dimension screenSize) {
        // frame attributes
        this.setTitle("3D Tutorial - Michael Wulber");
        this.setPreferredSize(screenSize);
        this.panel = new Display(screenSize.width, screenSize.height);
        this.getContentPane().add(panel, BorderLayout.CENTER);

        // add a menu to the frame
        addMenu();
    }

    private void addMenu(){
        // setup the frame's menu bar

        // File menu
        JMenu fileMenu = new JMenu("File");

        // Exit
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        fileMenu.add(exitItem);

        // attach menu to a menu bar
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(fileMenu);
        this.setJMenuBar(menuBar);
    }
}
