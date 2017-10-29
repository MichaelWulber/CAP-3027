import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImageFrame extends JFrame {
    private final int NORMAL = 0;
    private final int TIGER = 1;

    private final FractalDisplayPanel panel;
    private final JLabel label;
    private final JFileChooser chooser;
    private BufferedImage image;

    public ImageFrame(int width, int height) {

        // frame attributes
        this.setTitle("CAP 3027 2017 - HW09 - Michael Wulber");

        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        this.panel = new FractalDisplayPanel(img);
        this.label = new JLabel("Click and hold to zoom (LMB to zoom in/RMB to zoom out)");

        this.getContentPane().add(panel, BorderLayout.CENTER);
        this.getContentPane().add(label, BorderLayout.SOUTH);

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

        // Mandelbrot
        JMenuItem mandelbrot = new JMenuItem("Mandelbrot");
        mandelbrot.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mandelbrot();
            }
        });

        fileMenu.add(mandelbrot);

        // Julia
        JMenuItem julia = new JMenuItem("Julia");
        julia.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                julia();
            }
        });

        fileMenu.add(julia);

        // color scheme
        JMenu colorScheme = new JMenu("Color Scheme");

        JMenuItem normal = new JMenuItem("Normal");
        normal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                panel.colorMode = NORMAL;
            }
        });

        colorScheme.add(normal);

        JMenuItem tiger = new JMenuItem("Tiger");
        tiger.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                panel.colorMode = TIGER;
            }
        });

        colorScheme.add(tiger);
        fileMenu.add(colorScheme);

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

    public void julia(){
        panel.setFractal(new JuliaSet());
        panel.displayFractal();
    }


    public void mandelbrot(){
        panel.setFractal(new MandelbrotSet());
        panel.displayFractal();
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
