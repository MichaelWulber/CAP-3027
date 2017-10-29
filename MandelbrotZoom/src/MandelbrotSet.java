import java.awt.image.BufferedImage;

public class MandelbrotSet implements Fractal{
    public BoundedBox bounds;
    public int memberColor;
    public int nonMemberColor1;
    public int nonMemberColor2;
    public int nonMemberColor3;
    private MandelbrotPlotter mp;

    public MandelbrotSet(){
        this.bounds = new BoundedBox(-2, 2, -1.5, 1.5);
        this.memberColor = 0xFF000000;
        this.nonMemberColor1 = 0xFFFFFFFF;
        this.nonMemberColor2 = 0xFFFF3300;
        this.nonMemberColor3 = 0xFF100050;
        this.mp = new MandelbrotPlotter();
        this.mp.ms = this;
    }

    public void resetBounds(){
        this.bounds = new BoundedBox(-2, 2, -1.5, 1.5);
    }

    @Override
    public BufferedImage getImage(int cm) {
        return mp.plot(cm);
    }

    @Override
    public FractalPlotter getFractalPlotter() {
        return this.mp;
    }

    @Override
    public BoundedBox getBounds() {
        return bounds;
    }
}
