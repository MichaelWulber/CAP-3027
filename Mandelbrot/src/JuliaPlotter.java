import java.awt.image.BufferedImage;

public class JuliaPlotter {
    public int tmax;
    public int width;
    public int height;
    public JuliaSet js;

    public JuliaPlotter(){
        this.tmax = 100;
        this.width = 600;
        this.height = 450;
        this.js = null;
    }

    public JuliaPlotter(int tmax, int width, int height, JuliaSet js){
        this.tmax = tmax;
        this.width = width;
        this.height = height;
        this.js = js;
    }

    public BufferedImage plot(){
        BufferedImage fractalImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        int[] colors = ColorUtil.getLinearGradient(js.nonMemberColor1, js.nonMemberColor2, tmax);

        ComplexNumber m = new ComplexNumber(0.0, 1.0);
        ComplexNumber z0 = new ComplexNumber(js.bounds.lowerX, js.bounds.lowerY);
        ComplexNumber z = new ComplexNumber(0, 0);
        double dr = (js.bounds.upperX - js.bounds.lowerX)/((double)width - 1.0);
        double di = (js.bounds.upperY - js.bounds.lowerY)/((double)height - 1.0);

        int t = 0;

        for (int i = 0; i < width; ++i){
            for (int j = 0; j < height; ++j){
                z.real = z0.real;
                z.imag = z0.imag;
                while (t < tmax){
                    z.square();
                    z.add(m);
                    if (z.sumSquares() > 4){
                        break;
                    }
                    ++t;
                }
                if (t < tmax){
                    fractalImage.setRGB(i, j, colors[t]);
                } else {
                    fractalImage.setRGB(i, j, js.memberColor);
                }
                z0.imag += di;
                t = 0;
            }
            z0.imag = js.bounds.lowerY;
            z0.real += dr;
        }
        return fractalImage;
    }
}
