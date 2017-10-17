import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Scanner;

public class ImageFrame extends JFrame implements Runnable{

    private ImageIcon image_icon;
    private JLabel label;
    private final JFileChooser chooser;
    private BufferedImage image;

    // I made these so that the user can queue a bunch of fractals without affecting the current one
    private LinkedList<IFS> IFSqueue;
    private IFS lastIFS;

    // constructor
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

        // instantiate IFSqueue and lastIFS
        this.IFSqueue = new LinkedList<IFS>();
        this.lastIFS = new IFS();
    }

    private void addMenu() {
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
        JMenuItem loadIFSDescription = new JMenuItem("Load IFS Description");
        loadIFSDescription.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadIFSDescription();
            }
        });

        fileMenu.add(loadIFSDescription);

        // configure Image
        JMenuItem configureImage = new JMenuItem("Configure Image");
        configureImage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                configureImage();
            }
        });

        fileMenu.add(configureImage);

        // Display IFS
        JMenuItem displayIFS = new JMenuItem("Display IFS");
        displayIFS.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayIFS();
            }
        });

        fileMenu.add(displayIFS);

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

    private void loadIFSDescription() {
        try {
            // load file
            File file = getFile();
            if (file == null) {
                throw new Exception();
            }

            // parse file
            IFS ifs = new IFS();
            double[] transform_data = new double[7];
            LinkedList<TransformWrapper> affineTransforms = new LinkedList<TransformWrapper>();
            Scanner scanner;
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            double probability = 0;
            double d = 0;
            int count = 0;
            String line = "";
            while ( (line = br.readLine()) != null ) {
                scanner = new Scanner(line);
                while ( scanner.hasNextDouble() ) {
                    d = scanner.nextDouble();
                    transform_data[count] = d;
                    if (count > 6) {
                        throw new Exception("Invalid IFS Description");
                    }
                    count++;
                }
                affineTransforms.add(new TransformWrapper(new AffineTransform(transform_data[0], transform_data[2],
                        transform_data[1], transform_data[3],
                        transform_data[4], transform_data[5]),
                        transform_data[6]));
                transform_data[6] = 0;
                count = 0;
            }

            determineWeights(affineTransforms);
            ifs.setTransforms(affineTransforms);
            ifs.setForeground(lastIFS.getForeground());
            ifs.setBackground(lastIFS.getBackground());
            ifs.setImage(lastIFS.getImage());
            IFSqueue.addLast(ifs);

        } catch(Exception e){
            JOptionPane.showMessageDialog(null, e, "error", JOptionPane.ERROR_MESSAGE );
        }
    }

    private void configureImage() {
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

            for (IFS ifs : IFSqueue) {
                ifs.setImage(new BufferedImage(dim, dim, BufferedImage.TYPE_INT_ARGB));
                ifs.setForeground(new Color(foreground));
                ifs.setBackground(new Color(background));
            }

            lastIFS.setImage(new BufferedImage(dim, dim, BufferedImage.TYPE_INT_ARGB));
            lastIFS.setForeground(new Color(foreground));
            lastIFS.setBackground(new Color(background));

        } catch(Exception e){
            JOptionPane.showMessageDialog(null, e, "error", JOptionPane.ERROR_MESSAGE );
        }
    }

    // generates fractal image and display it in the JFrame
    private void displayIFS(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    // get user input
                    String d = JOptionPane.showInputDialog("What would you like the generation number to be?");
                    int gen = Integer.parseInt(d);

                    for (IFS ifs : IFSqueue){
                        ifs.setGenerations(gen);
                    }
                    lastIFS.setGenerations(gen);

                    IFS ifs;
                    if (!IFSqueue.isEmpty()) {
                        ifs = IFSqueue.peekFirst();
                        lastIFS = ifs;
                        IFSqueue.remove();
                    } else {
                        ifs = lastIFS;
                    }
                    final BufferedImage fractal_image = ifs.generateImage();
                    image = fractal_image;

                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            displayBufferedImage(fractal_image);
                        }
                    });
                } catch(Exception e){
                    JOptionPane.showMessageDialog(null, e, "error", JOptionPane.ERROR_MESSAGE );
                }
            }
        }).start();
    }

    // calculates probability weights
    private void determineWeights(LinkedList<TransformWrapper> affineTransforms){
        double sum = 0;
        double P = 1;
        double det = 0;

        for (TransformWrapper tw : affineTransforms){
            det = tw.getTransform().getDeterminant();
            if (det == 0){
                tw.setProbability(0.01);
            }
            sum += Math.abs(det);
            P -= tw.getProbability();
        }
        if (P != 1) {
            for (TransformWrapper tw : affineTransforms) {
                if (tw.getProbability() == 0) {
                    tw.setProbability((Math.abs(tw.getTransform().getDeterminant()) / sum) * P);
                }
            }
        } else {
            for (TransformWrapper tw : affineTransforms) {
                if (tw.getProbability() == 0) {
                    tw.setProbability(1.0 / affineTransforms.size());
                }
            }
        }

        // sort for distributed random selection to work later
        affineTransforms.sort(new Comparator<TransformWrapper>() {
            @Override
            public int compare(TransformWrapper o1, TransformWrapper o2) {
                return (o1.getProbability() < o2.getProbability()) ? 1 : 0;
            }
        });
    }

    // save the image
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

    // use to test if code is running on the EDT
    public void checkThread(String name){
        if (SwingUtilities.isEventDispatchThread()) {
            System.err.println(name + " is running on EDT");
        } else {
            System.err.println(name + " is not running on EDT");
        }
    }

    @Override
    public void run() {

    }
}