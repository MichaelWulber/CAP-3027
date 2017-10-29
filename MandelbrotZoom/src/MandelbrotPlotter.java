import java.awt.*;
import java.awt.image.BufferedImage;

public class MandelbrotPlotter implements FractalPlotter{

    public int tmax;
    public int width;
    public int height;
    public MandelbrotSet ms;

    public MandelbrotPlotter(){
        this.tmax = 100;
        this.width = 600;
        this.height = 450;
        this.ms = null;
    }

    @Override
    public BufferedImage plot(int cm) {
        return (cm == 0) ? normalPlot() : tigerPlot();
    }

    public BufferedImage normalPlot(){
        BufferedImage fractalImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        int[] colors = ColorUtil.doubleGradient(ms.nonMemberColor1, ms.nonMemberColor2, ms.nonMemberColor3, tmax);

        ComplexNumber m = new ComplexNumber(ms.bounds.lowerX, ms.bounds.lowerY);
        ComplexNumber z = new ComplexNumber(0, 0);
        double dr = (ms.bounds.upperX - ms.bounds.lowerX)/((double)width - 1.0);
        double di = (ms.bounds.upperY - ms.bounds.lowerY)/((double)height - 1.0);

        int t = 0;

        for (int i = 0; i < width; ++i){
            for (int j = 0; j < height; ++j){
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
                    fractalImage.setRGB(i, j, ms.memberColor);
                }
                m.imag += di;
                z.real = 0;
                z.imag = 0;
                t = 0;
            }
            m.imag = ms.bounds.lowerY;
            m.real += di;
        }

        return fractalImage;
    }

    public BufferedImage tigerPlot(){
        BufferedImage fractalImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        int[] colors = ColorUtil.doubleGradient(ms.nonMemberColor1, ms.nonMemberColor2, ms.nonMemberColor3, tmax);

        ComplexNumber m = new ComplexNumber(ms.bounds.lowerX, ms.bounds.lowerY);
        ComplexNumber z = new ComplexNumber(0, 0);
        double dr = (ms.bounds.upperX - ms.bounds.lowerX)/((double)width - 1.0);
        double di = (ms.bounds.upperY - ms.bounds.lowerY)/((double)height - 1.0);

        int t = 0;

        for (int i = 0; i < width; ++i){
            for (int j = 0; j < height; ++j){
                while (t < tmax){
                    z.square();
                    z.add(m);
                    if (z.sumSquares() > 4){
                        break;
                    }
                    ++t;
                }
                if (t >= tmax){
                    fractalImage.setRGB(i, j, ms.memberColor);
                } else if (t % 2 == 0){
                    fractalImage.setRGB(i, j, ms.memberColor);
                } else {
                    fractalImage.setRGB(i, j, colors[t]);
                }
                m.imag += di;
                z.real = 0;
                z.imag = 0;
                t = 0;
            }
            m.imag = ms.bounds.lowerY;
            m.real += di;
        }

        return fractalImage;
    }


}
