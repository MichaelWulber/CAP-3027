import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

public class ImageFrame extends JFrame implements Runnable {

    private ImageIcon image_icon;
    private JLabel label;
    private final JFileChooser chooser;
    private BufferedImage image;

    private LinkedList<LSystem> LSQueue;
    private LSystem LS;

    public ImageFrame(int width, int height) {

        // frame attributes
        this.setTitle("CAP 3027 2017 - HW07 - Michael Wulber");
        this.setSize(width, height);
        this.image_icon = new ImageIcon();
        this.label = new JLabel(image_icon);
        this.setContentPane(new JScrollPane(label));

        // setup file chooser
        chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("."));

        // add a menu to the frame
        addMenu();

        // create LSystem
        this.LSQueue = new LinkedList<LSystem>();
        this.LS = new LSystem();
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

        // Load IFS Description
        JMenuItem loadLS = new JMenuItem("Load L-System");
        loadLS.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadLS();
            }
        });

        fileMenu.add(loadLS);

        // configure Image
        JMenuItem configureImage = new JMenuItem("Configure Image");
        configureImage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                configureImage();
            }
        });

        fileMenu.add(configureImage);

        // Display L-System
        JMenuItem displayLS = new JMenuItem("Display L-System");
        displayLS.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayLS();
            }
        });

        fileMenu.add(displayLS);

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
            }
            else {
                foreground = (int) Long.parseLong( d, 16 );
            }
            if ( (foreground > 0xFFFFFF) || (foreground < 0) ){
                throw new Exception("Invalid Color");
            }

            // get background color
            d = JOptionPane.showInputDialog("What color would you like the background to be?");
            int background;
            if (d.substring(0, 2).equals("0x")){
                background =(int) Long.parseLong( d.substring( 2, d.length() ), 16 );
            }
            else {
                background = (int) Long.parseLong( d, 16 );
            }
            if ( (background > 0xFFFFFF) || (background < 0) ){
                throw new Exception("Invalid Color");
            }

            for (LSystem ls : LSQueue){
                ls.setImage(new BufferedImage(dim, dim, BufferedImage.TYPE_INT_ARGB));
                ls.setForeground(new Color(foreground));
                ls.setBackground(new Color(background));
            }

            LS.setImage(new BufferedImage(dim, dim, BufferedImage.TYPE_INT_ARGB));
            LS.setForeground(new Color(foreground));
            LS.setBackground(new Color(background));

        } catch(Exception e){
            JOptionPane.showMessageDialog(null, e, "error", JOptionPane.ERROR_MESSAGE );
        }
    }

    private void loadLS(){
        try {
            // load file
            File file = getFile();
            if (file == null) {
                throw new Exception();
            }

            // parse file
            LSystem ls = new LSystem();
            double[] transform_data = new double[7];
            HashMap<String, String> generators = new HashMap<String, String>();
            Scanner scanner = new Scanner(file);
            double d = 0;
            String line;

            // parse delta
            if (scanner.hasNextDouble()) {
                ls.setDelta(scanner.nextDouble());
            } else {
                throw new Exception("invalid delta");
            }

            // parse segment length scaling factor
            if (scanner.hasNextDouble()) {
                d = scanner.nextDouble();
                if (d <= 0){
                    throw new Exception("invalid segment length scaling factor");
                }
                ls.setSlsf(d);
            } else {
                throw new Exception("invalid segment length scaling factor");
            }

            // parse segment length scaling factor
            line = scanner.nextLine();
            if ( (line = scanner.nextLine()) != null ) {
                ls.setInitiator(removeWhiteSpace(line));
            } else {
                throw new Exception("invalid segment length scaling factor");
            }

            // parse generators
            if (!scanner.hasNextLine()){
                throw new Exception("L-System description lacks generator(s)");
            }
            while (scanner.hasNextLine()) {
                line = removeWhiteSpace(scanner.nextLine());
                String[] generator = line.split("=");
                generators.put(generator[0], generator[1]);
            }

            ls.setGenerators(generators);
            ls.setForeground(LS.getForeground());
            ls.setBackground(LS.getBackground());
            ls.setImage(LS.getImage());
            LSQueue.push(ls);

        } catch(Exception e){
            JOptionPane.showMessageDialog(null, e, "error", JOptionPane.ERROR_MESSAGE );
        }
    }

    private void displayLS(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    // get user input
                    String d = JOptionPane.showInputDialog("What would you like the generation number to be?");
                    int gen = Integer.parseInt(d);
                    if (gen < 0){
                        throw new Exception("invalid input");
                    }

                    d = JOptionPane.showInputDialog("What would you like the starting x position to be?");
                    double x = Double.parseDouble(d);
                    if (x < -1 || x > 1){
                        throw new Exception("invalid input");
                    }

                    d = JOptionPane.showInputDialog("What would you like the starting y position to be?");
                    double y = Double.parseDouble(d);
                    if (y < -1 || y > 1){
                        throw new Exception("invalid input");
                    }

                    d = JOptionPane.showInputDialog("What would you like initial bearing to be?");
                    double b = Double.parseDouble(d);

                    d = JOptionPane.showInputDialog("What would you like the base segment length to be?");
                    double bsl = Double.parseDouble(d);
                    if (bsl <= 0){
                        throw new Exception("invalid input");
                    }

                    LSystem ls;
                    if (!LSQueue.isEmpty()){
                        ls = LSQueue.peekFirst();
                        LS = ls;
                        LSQueue.remove();
                    } else {
                        ls = LS;
                    }

                    ls.turtleState.coord.x = x;
                    ls.turtleState.coord.y = y;
                    ls.turtleState.setBearing(b);
                    ls.turtleState.bsl = bsl;


                    final BufferedImage LS_image = LS.imageGenerator(gen);
                    image = LS_image;
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            displayBufferedImage(LS_image);
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
