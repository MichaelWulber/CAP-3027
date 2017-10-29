import java.awt.image.BufferedImage;

public class JuliaSet implements Fractal{
    public BoundedBox bounds;
    public int memberColor;
    public int nonMemberColor1;
    public int nonMemberColor2;
    public int nonMemberColor3;
    private JuliaPlotter jp;

    public JuliaSet(){
        this.bounds = new BoundedBox(-2, 2, -1.5, 1.5);
        this.memberColor = 0xFF000000;
        this.nonMemberColor1 = 0xFFFFFFFF;
        this.nonMemberColor2 = 0xFFFF3300;
        this.nonMemberColor3 = 0xFF100050;

        this.jp = new JuliaPlotter();
        this.jp.js = this;
    }

    @Override
    public BufferedImage getImage(int cm) {
        return jp.plot(cm);
    }

    @Override
    public FractalPlotter getFractalPlotter() {
        return jp;
    }

    @Override
    public BoundedBox getBounds() {
        return bounds;
    }
}
