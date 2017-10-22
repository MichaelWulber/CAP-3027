import java.awt.*;
import java.awt.image.BufferedImage;

public class MandlebrotPlotter {

    public int tmax;
    public int width;
    public int height;
    public MandelbrotSet ms;

    public MandlebrotPlotter(){
        this.tmax = 100;
        this.width = 100;
        this.height = 100;
        this.ms = null;
    }

    public MandlebrotPlotter(int tmax, int width, int height, MandelbrotSet ms){
        this.tmax = tmax;
        this.width = width;
        this.height = height;
        this.ms = ms;
    }

    public BufferedImage plot(){
        BufferedImage fractalImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for (int i = 0; i < width; ++i){
            for (int j = 0; j < width; ++j) {
                fractalImage.setRGB(i, j, ms.nonMemberColor);
            }
        }

        ComplexNumber m = new ComplexNumber(ms.bounds.lowerX, ms.bounds.lowerY);
        ComplexNumber z = new ComplexNumber(0, 0);
        double dr = (double)((ms.bounds.upperX - ms.bounds.lowerX)/(width - 1));
        double di = (double)((ms.bounds.upperY - ms.bounds.lowerY)/(height - 1));

        int t = 0;

        System.out.println("generating image...");
        for (int i = 0; i < width; ++i){
            for (int j = 0; j < width; ++j){
                while (t < tmax){
                    z.mult(z);
                    z.add(m);
                    if (z.sumSquares() > 4){
                        break;
                    }
                    t++;
                }
                if (t < tmax){
                    fractalImage.setRGB(i, j, ms.nonMemberColor);
                } else {
                    fractalImage.setRGB(i, j, ms.memberColor);
                }
            }
        }

        System.out.println("done!");
        return fractalImage;
    }

}
