import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class GOLDisplayPanel extends JPanel {
    private final int WIDTH;
    private final int HEIGHT;

    private BufferedImage image;
    private Graphics2D g2d;

    private CellGrid grid;

    private int mouseX;
    private int mouseY;

    public GOLDisplayPanel(BufferedImage image){
        this.image = image;
        g2d = image.createGraphics();

        WIDTH = image.getWidth();
        HEIGHT = image.getHeight();
        Dimension size = new Dimension(WIDTH, HEIGHT);
        setMinimumSize(size);
        setMaximumSize(size);
        setPreferredSize(size);

        grid = new CellGrid();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
                invert();
            }
        });
    }

    private void invert(){
        int row = mapY(mouseY);
        int col = mapX(mouseX);

        grid.invert(col, row);

    }

    private int mapX(int mx){
        return ( (int)((double)mx/(double)WIDTH) * grid.numCols );
    }

    private int mapY(int my){
        return ( (int)((double)my/(double)HEIGHT) * grid.numRows );
    }

    private void repaintSquare(int row, int col){
        
        double side_length = (double)HEIGHT/(double)grid.numRows;
        double x = (double)col * side_length;
        double y = (double)row * side_length;
        g2d.draw(new Rectangle2D.Double(x, y, side_length, side_length));
        repaint();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }


}
