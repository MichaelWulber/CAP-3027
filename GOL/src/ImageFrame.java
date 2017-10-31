import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImageFrame extends JFrame {
    private final GOLDisplayPanel panel;
    private final JFileChooser chooser;
    private BufferedImage image;

    public ImageFrame(int width, int height) {

        // frame attributes
        this.setTitle("CAP 3027 2017 - HW10 - Michael Wulber");
        this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        this.panel = new GOLDisplayPanel(new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB));
        this.getContentPane().add(panel, BorderLayout.CENTER);

        // setup file chooser
        chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("."));

        // add a menu to the frame
        addMenu();
    }

    private void addMenu(){
        // setup the frame's menu bar

        // File menu
        JMenu fileMenu = new JMenu("File");

        // begin
        JMenuItem begin = new JMenuItem("begin");
        begin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // ...
            }
        });

        fileMenu.add(begin);

        // stop
        JMenuItem stop = new JMenuItem("stop");
        stop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // ...
            }
        });

        fileMenu.add(stop);

        // Save Image
        JMenuItem saveImage = new JMenuItem("Save Image");
        saveImage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveImage();
            }
        });

        fileMenu.add(saveImage);

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

    private void saveImage(){
        try
        {
            // load file
            File outputFile = getFile();
            if (outputFile == null) {
                throw new Exception();
            }
            javax.imageio.ImageIO.write( image, "png", outputFile );
        }
        catch ( Exception e )
        {
            JOptionPane.showMessageDialog( ImageFrame.this,
                    "Error saving file",
                    "oops!",
                    JOptionPane.ERROR_MESSAGE );
        }
    }

    // open a file selected by the user
    private File getFile(){
        File file = null;

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
            file = chooser.getSelectedFile();
        }

        return file;
    }

    private String removeWhiteSpace(String input) {
        return input.replaceAll("\\s", "");
    }
}
