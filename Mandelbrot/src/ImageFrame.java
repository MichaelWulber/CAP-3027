import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImageFrame extends JFrame {

    private final AreaSelectPanel panel;
    private final JButton button;
    private final JFileChooser chooser;
    private BufferedImage image;

    private MandelbrotSet mSet;
    private MandlebrotPlotter mp;

    private JuliaSet jSet;
    private JuliaPlotter jp;

    private int selection;

    public ImageFrame(int width, int height) {

        // frame attributes
        this.setTitle("CAP 3027 2017 - HW09 - Michael Wulber");

        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        this.panel = new AreaSelectPanel(img);

        this.button = new JButton("Zoom");
        this.button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selection == 0){
                    mSet.bounds.map(width, height, panel.getXCoord(), panel.getYCoord(), panel.getW(), panel.getH());
                    mandelbrotZoom();
                } else if (selection == 1){
                    jSet.bounds.map(width, height, panel.getXCoord(), panel.getYCoord(), panel.getW(), panel.getH());
                    juliaZoom();
                } else {
                    System.out.println("error: cannot zoom");
                }
            }
        });

        this.getContentPane().add(panel, BorderLayout.CENTER);
        this.getContentPane().add(button, BorderLayout.SOUTH);

        // setup file chooser
        chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("."));

        // add a menu to the frame
        addMenu();

        this.mSet = new MandelbrotSet();
        this.mp = new MandlebrotPlotter();
        this.mp.ms = this.mSet;

        this.jSet = new JuliaSet();
        this.jp = new JuliaPlotter();
        this.jp.js = this.jSet;

        this.selection = -1;
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
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    jSet.resetBounds();
                    final BufferedImage display = jp.plot();

                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            panel.setImage(display);
                        }
                    });
                    selection = 1;
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e, "error", JOptionPane.ERROR_MESSAGE );
                }
            }
        }).start();
    }

    public void juliaZoom(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final BufferedImage display = jp.plot();

                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            panel.setImage(display);
                        }
                    });
                    selection = 1;
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e, "error", JOptionPane.ERROR_MESSAGE );
                }
            }
        }).start();
    }

    public void mandelbrot(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mSet.resetBounds();
                    final BufferedImage display = mp.plot();

                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            panel.setImage(display);
                        }
                    });
                    selection = 0;
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e, "error", JOptionPane.ERROR_MESSAGE );
                }
            }
        }).start();
    }

    public void mandelbrotZoom(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final BufferedImage display = mp.plot();

                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            panel.setImage(display);
                        }
                    });
                    selection = 0;
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

    private String removeWhiteSpace(String input) {
        return input.replaceAll("\\s", "");
    }
}
