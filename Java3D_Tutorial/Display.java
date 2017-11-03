import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Display extends JPanel {
    public int WIDTH, HEIGHT, MAX_X, MAX_Y;

    public BufferedImage image;
    public Graphics2D g2d;

    Display(int width, int height){
        this.WIDTH = width;
        this.HEIGHT = height;
        this.MAX_X = width - 1;
        this.MAX_Y = height - 1;

        this.image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        this.g2d = image.createGraphics();

        reset();
    }

    // repaint the display black
    private void reset(){
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, WIDTH, HEIGHT);
        repaint();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }

}
