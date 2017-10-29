import java.awt.image.BufferedImage;

public interface Fractal {
    FractalPlotter getFractalPlotter();

    BufferedImage getImage(int cm);

    BoundedBox getBounds();
}
