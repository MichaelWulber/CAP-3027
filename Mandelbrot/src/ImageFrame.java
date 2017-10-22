import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImageFrame extends JFrame implements Runnable {

    private ImageIcon image_icon;
    private JLabel label;
    private final JFileChooser chooser;
    private BufferedImage image;

    public ImageFrame(int width, int height) {

        // frame attributes
        this.setTitle("CAP 3027 2017 - HW09 - Michael Wulber");
        this.setSize(width, height);
        this.image_icon = new ImageIcon();
        this.label = new JLabel(image_icon);
        this.setContentPane(new JScrollPane(label));

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

        // Exit
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        fileMenu.add(exitItem);

        // Load Description
        JMenuItem load = new JMenuItem("Load ...");
        load.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                load();
            }
        });

        fileMenu.add(load);

        // configure Image
        JMenuItem configureImage = new JMenuItem("Configure Image");
        configureImage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                configureImage();
            }
        });

        fileMenu.add(configureImage);

        // Display
        JMenuItem display = new JMenuItem("Display ...");
        display.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                display();
            }
        });

        fileMenu.add(display);

        // Save Image
        JMenuItem saveImage = new JMenuItem("Save Image");
        saveImage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveImage();
            }
        });

        fileMenu.add(saveImage);

        // attach menu to a menu bar
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(fileMenu);
        this.setJMenuBar(menuBar);
    }

    private void configureImage(){
        try {
            // get user input
            String d = JOptionPane.showInputDialog("What would you like the size of the image to be?");
            int dim = Integer.parseInt(d);

            // get foreground color
            d = JOptionPane.showInputDialog("What color would you like the foreground to be?");
            int foreground;
            if (d.substring(0, 2).equals("0x")){
                foreground =(int) Long.parseLong( d.substring( 2, d.length() ), 16 );
                if ( (foreground > 0xFFFFFF) || (foreground < 0) ){
                    throw new Exception("Invalid Color");
                }
            }
            else {
                throw new Exception("Invalid Color");
            }

            // get background color
            d = JOptionPane.showInputDialog("What color would you like the background to be?");
            int background;
            if (d.substring(0, 2).equals("0x")){
                background =(int) Long.parseLong( d.substring( 2, d.length() ), 16 );
                if ((background > 0xFFFFFF) || (background < 0)) {
                    throw new Exception("Invalid Color");
                }
            }
            else {
                throw new Exception("Invalid Color");
            }

//            for (LSystem ls : LSQueue){
//                ls.setImage(new BufferedImage(dim, dim, BufferedImage.TYPE_INT_ARGB));
//                ls.setForeground(new Color(foreground));
//                ls.setBackground(new Color(background));
//            }
//
//            LS.setImage(new BufferedImage(dim, dim, BufferedImage.TYPE_INT_ARGB));
//            LS.setForeground(new Color(foreground));
//            LS.setBackground(new Color(background));

        } catch(Exception e){
            JOptionPane.showMessageDialog(null, e, "error", JOptionPane.ERROR_MESSAGE );
        }
    }

    private void load(){
        try {
            // load file
            File file = getFile();
            if (file == null) {
                throw new Exception();
            }

            // ...

        } catch(Exception e){
            JOptionPane.showMessageDialog(null, e, "error", JOptionPane.ERROR_MESSAGE );
        }
    }

    private void display(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    // ...

                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            // display image
                        }
                    });

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e, "error", JOptionPane.ERROR_MESSAGE );
                }
            }
        }).start();
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

    // Display BufferedImage
    public void displayBufferedImage(BufferedImage image) {
        this.image_icon.setImage(image);
        this.label.repaint();
        this.validate();
    }

    private String removeWhiteSpace(String input) {
        return input.replaceAll("\\s", "");
    }

    @Override
    public void run() {

    }
}
