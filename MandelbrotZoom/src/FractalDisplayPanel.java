import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class FractalDisplayPanel extends JPanel{
    // panel size
    private final int WIDTH, MAX_X;
    private final int HEIGHT, MAX_Y;
    private final int MILLESECONDS_BETWEEN_FRAMES = 17;

    // image displayed on panel
    private BufferedImage image;
    private Graphics2D g2d;

    // Normal or Tiger
    public int colorMode;

    private double mouseX;
    private double mouseY;

    private Timer animIn;
    private Timer animOut;

    public Fractal fractal = null;

    public FractalDisplayPanel(BufferedImage image){
        this.image = image;
        g2d = image.createGraphics();

        WIDTH = image.getWidth();
        HEIGHT = image.getHeight();
        Dimension size = new Dimension(WIDTH, HEIGHT);
        setMinimumSize(size);
        setMaximumSize(size);
        setPreferredSize(size);

        MAX_X = WIDTH - 1;
        MAX_Y = HEIGHT - 1;

        colorMode = 0;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (fractal != null) {
                    mouseX = e.getX();
                    mouseY = e.getY();

                    if (e.getButton() == MouseEvent.BUTTON1) {
                        animIn.stop();
                        animOut.start();
                    } else if (e.getButton() == MouseEvent.BUTTON3) {
                        animOut.stop();
                        animIn.start();
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1){
                    animOut.stop();
                } else if (e.getButton() == MouseEvent.BUTTON3) {
                    animIn.stop();
                }
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
            }
        });

        initZooms();
    }

    private void initZooms(){
        animIn = new Timer(MILLESECONDS_BETWEEN_FRAMES, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                animIn.stop();
                zoomIn();
                displayFractal();
                animIn.restart();
            }
        });

        animOut = new Timer(MILLESECONDS_BETWEEN_FRAMES, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                animOut.stop();
                zoomOut();
                displayFractal();
                animOut.restart();
            }
        });
    }

    public void displayFractal(){
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    final BufferedImage display = fractal.getImage(colorMode);
//                    image = display;
//                    SwingUtilities.invokeAndWait(new Runnable() {
//                        @Override
//                        public void run() { 
//                            setImage(display);
//                        }
//                    });
//                } catch (Exception e) {
//                    JOptionPane.showMessageDialog(null, e, "error displaying fractal image", JOptionPane.ERROR_MESSAGE );
//                }
//            }
//        }).start();
        try {
            image = fractal.getImage(colorMode);
            setImage(image);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e, "error displaying fractal image", JOptionPane.ERROR_MESSAGE );
        }
    }

    public void setFractal(Fractal fractal){
        this.fractal = fractal;
    }

    // change background image
    public void setImage(BufferedImage src){
        g2d.setPaintMode();
        g2d.drawImage(src,
                0, 0, MAX_X, MAX_Y,
                0, 0, (src.getWidth() - 1), (src.getHeight() - 1),
                this);
        repaint();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }

    private void zoomIn(){
        BoundedBox bounds = fractal.getBounds();

        double lx = bounds.lowerX + (mouseX/(double)WIDTH) * bounds.getRangeX() * 0.01;
        double ux = lx + bounds.getRangeX()* 0.99;
        double ly = bounds.lowerY + (mouseY/(double)HEIGHT) * bounds.getRangeY() * 0.01;
        double uy = ly + bounds.getRangeY()* 0.99;

        fractal.getBounds().lowerX = lx;
        fractal.getBounds().upperX = ux;
        fractal.getBounds().lowerY = ly;
        fractal.getBounds().upperY = uy;
    }

    private void zoomOut(){
        BoundedBox bounds = fractal.getBounds();

        double lx = bounds.lowerX + (mouseX/(double)WIDTH) * bounds.getRangeX() * (-0.0101010101);
        double ux = lx + bounds.getRangeX()* 1.0101010101;
        double ly = bounds.lowerY + (mouseY/(double)HEIGHT) * bounds.getRangeY() * (-0.0101010101);
        double uy = ly + bounds.getRangeY()* 1.0101010101;

        fractal.getBounds().lowerX = lx;
        fractal.getBounds().upperX = ux;
        fractal.getBounds().lowerY = ly;
        fractal.getBounds().upperY = uy;
    }
}
