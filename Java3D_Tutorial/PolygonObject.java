import java.awt.*;

public class PolygonObject {
    Polygon poly;
    Color color;

    public PolygonObject(double[] x, double[] y, Color color) throws Exception{
        this.color = color;
        this.poly = new Polygon();

        if (x.length != y.length){
            throw new Exception("coordinate arrays are of different lengths");
        } else {
            for (int i = 0; i < x.length; ++i){
                poly.addPoint((int)x[i], (int)y[i]);
            }
        }

    }
}
