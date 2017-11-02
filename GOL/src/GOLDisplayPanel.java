import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class GOLDisplayPanel extends JPanel {
    private final int WIDTH, MAX_X;
    private final int HEIGHT, MAX_Y;

    private Color GRIDLINES;
    private int STROKEWIDTH;
    private int MILLESECONDS_BETWEEN_FRAMES;

    private BufferedImage image;
    private Graphics2D g2d;

    private CellGrid grid;

    private int mouseX;
    private int mouseY;

    public boolean paused;
    private Timer play;

    public GOLDisplayPanel(BufferedImage image){
        this.image = image;
        g2d = image.createGraphics();

        WIDTH = image.getWidth();
        HEIGHT = image.getHeight();

        MAX_X = WIDTH - 1;
        MAX_Y = HEIGHT - 1;

        GRIDLINES = Color.DARK_GRAY;
        STROKEWIDTH = 1;
        MILLESECONDS_BETWEEN_FRAMES = 300;

        Dimension size = new Dimension(WIDTH, HEIGHT);
        setMinimumSize(size);
        setMaximumSize(size);
        setPreferredSize(size);

        grid = new CellGrid();
        drawCellGrid();
        //drawGridLines();

        initAnim();
        paused = true;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (paused && e.getX() >= 0 && e.getX() <= MAX_X && e.getY() >= 0 && e.getY() <= MAX_Y) {
                    mouseX = e.getX();
                    mouseY = e.getY();
                    increment();
                }
            }
        });
    }

    public void step(){
        grid.step();
        drawCellGrid();
        //drawGridLines();
        repaint();
    }

    private void drawCellGrid(){
        double side_length = (double)HEIGHT/(double)grid.numRows;

        for (int i = 0; i < grid.numRows; ++i){
            for (int j = 0; j < grid.numCols; ++j){
                g2d.setColor(grid.getColor(i, j));

                double x = (double)j * side_length;
                double y = (double)i * side_length;
                g2d.fill(new Rectangle2D.Double(x, y, side_length, side_length));
            }
        }
    }

    private void increment(){
        int row = mapY(mouseY);
        int col = mapX(mouseX);
        grid.increment(row, col);
        repaintSquare(row, col);
    }

    private int mapX(int mx){
        return ( (int) ((double)(mx * grid.numCols)/(double)WIDTH) );
    }

    private int mapY(int my){
        return ( (int)((double)(my * grid.numRows)/(double)HEIGHT) );
    }

    private void repaintSquare(int row, int col){
        g2d.setColor(grid.getColor(row, col));
        double side_length = (double)HEIGHT/(double)grid.numRows;
        double x = (double)col * side_length;
        double y = (double)row * side_length;
        g2d.fill(new Rectangle2D.Double(x, y, side_length, side_length));
        //drawGridLines();
        repaint();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }

    private void initAnim(){
        play = new Timer(MILLESECONDS_BETWEEN_FRAMES, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                play.stop();
                step();
                play.start();
            }
        });
    }

    public void animate(){
        paused = false;
        play.start();
    }

    public void pause(){
        paused = true;
        play.stop();
    }

    private void reset(){
        this.grid = new CellGrid();
        drawCellGrid();
        repaint();
    }

    private void drawGridLines(){
        g2d.setColor(GRIDLINES);
        g2d.setStroke(new BasicStroke(STROKEWIDTH));

        double side_length = (double)MAX_Y/(double)grid.numRows;
        double pos = 0;

        for (int i = 0; i <= grid.numCols; ++i){
            pos = (side_length * i > MAX_Y) ? MAX_Y : side_length * i;
            g2d.draw(new Line2D.Double(0.0, pos, MAX_X, pos));
            g2d.draw(new Line2D.Double(pos, 0, pos, MAX_Y));
        }
    }
}