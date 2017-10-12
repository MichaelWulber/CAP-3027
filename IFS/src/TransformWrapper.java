import java.awt.geom.AffineTransform;

public class TransformWrapper {
    private AffineTransform transform;
    private double probability;

    public TransformWrapper(AffineTransform transform) {
        this.transform = transform;
        this.probability = 0;
    }

    public TransformWrapper(AffineTransform transform, double probability) {
        this.transform = transform;
        this.probability = probability;
    }

    public AffineTransform getTransform() {
        return transform;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }

    public String toString(){
        String s = "";
        double[] m = new double[9];
        transform.getMatrix(m);
        for (double d : m) {
            s += Double.toString(d) + ", ";
        }
        s += "p = " + probability;
        return s;
    }
}
