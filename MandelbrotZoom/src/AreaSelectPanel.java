import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class AreaSelectPanel extends JPanel{
    static private final Color OUTLINE_COLOR = Color.BLACK;

    // panel size
    private final int WIDTH, MAX_X;
    private final int HEIGHT, MAX_Y;

    // image displayed on panel
    private BufferedImage image;
    private Graphics2D g2d;

    // current selection
    private int x = -1;
    private int y = -1;
    private int w = 0;
    private int h = 0;

    public AreaSelectPanel(BufferedImage image){
        this.image = image;
        g2d = image.createGraphics();
        g2d.setXORMode(OUTLINE_COLOR);

        WIDTH = image.getWidth();
        HEIGHT = image.getHeight();
        Dimension size = new Dimension(WIDTH, HEIGHT);
        setMinimumSize(size);
        setMaximumSize(size);
        setPreferredSize(size);

        MAX_X = WIDTH - 1;
        MAX_Y = HEIGHT - 1;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                clearSelection(e.getPoint());
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                updateSelection(e.getPoint());
            }
        });
    }

    // accessors

    Point2D.Double getUpperLeft(){
        return getUpperLeft( new Point2D.Double() );
    }

    Point2D.Double getUpperLeft(Point2D.Double p){
        if (w < 0){
            if (h < 0){
                p.setLocation( (x + w)/((double)MAX_X), (y + h)/((double)MAX_Y) );
            } else {
                p.setLocation( (x + w)/((double)MAX_X), y/((double)MAX_Y) );
            }
        } else if (h < 0){
            p.setLocation( x/((double)MAX_X), (y + h)/((double)MAX_Y) );
        } else {
            p.setLocation( x/((double)MAX_X), y/((double)MAX_Y) );
        }

        return p;
    }

    Point2D.Double getLowerRight(){
        return getLowerRight( new Point2D.Double() );
    }

    Point2D.Double getLowerRight(Point2D.Double p){
        if (w < 0){
            if (h < 0){
                p.setLocation( x/((double)MAX_X), y/((double)MAX_Y) );
            } else {
                p.setLocation( x/((double)MAX_X), (y + h)/((double)MAX_Y) );
            }
        } else if (h < 0){
            p.setLocation( (x + w)/((double)MAX_X), y/((double)MAX_Y) );
        } else {
            p.setLocation( (x + w)/((double)MAX_X), (y + h)/((double)MAX_Y) );
        }

        return p;
    }

    // change background image
    public void setImage(BufferedImage src){
        g2d.setPaintMode();
        g2d.drawImage(src,
                0, 0, MAX_X, MAX_Y,
                0, 0, (src.getWidth() - 1), (src.getHeight() - 1),
                this);
        g2d.setXORMode(OUTLINE_COLOR);

        x = -1;
        y = -1;
        w = 0;
        h = 0;

        repaint();
    }

    // behaviors

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }

    private void clearSelection(Point p){
        // erase old selection
        drawSelection();

        // begin new selection
        x = (p.x < 0) ? 0 : (p.x < WIDTH) ? p.x : MAX_X;
        y = (p.y < 0) ? 0 : (p.y < HEIGHT) ? p.y : MAX_Y;
        w = 0;
        h = 0;

        drawSelection();
    }

    private void updateSelection(Point p){
        // erase old selection
        drawSelection();

        // begin new selection
        int px = (p.x < 0) ? 0 : (p.x < WIDTH) ? p.x : MAX_X;
        int py = (p.y < 0) ? 0 : (p.y < HEIGHT) ? p.y : MAX_Y;
        w = px - x;
        h = py - y;

        if (Math.abs((double)w/(double)h) > (double)WIDTH/(double)HEIGHT){
            if (w < 0) {
                w = (int)(-WIDTH * (double) Math.abs(h) / (double) HEIGHT);
            } else {
                w = (int)(WIDTH * (double) Math.abs(h) / (double) HEIGHT);
            }
        } else {
            if (h < 0) {
                h = (int) (-HEIGHT * (double) Math.abs(w) / (double) WIDTH);
            } else {
                h = (int) (HEIGHT * (double) Math.abs(w) / (double) WIDTH);
            }
        }

        drawSelection();
    }

    private void drawSelection(){
        if (w < 0){
            if (h < 0){
                g2d.drawRect((x + w), (y + h), -w, -h);
            } else {
                g2d.drawRect((x + w), y, -w, h);
            }
        } else if (h < 0){
            g2d.drawRect(x, (y + h), w, -h);
        } else {
            g2d.drawRect(x, y, w, h);
        }

        repaint();
    }

    public int getXCoord() {
        return x;
    }

    public int getYCoord() {
        return y;
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }
}
