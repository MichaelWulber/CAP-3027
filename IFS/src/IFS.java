import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Random;

public class IFS {
    private final int DEPTH_THRESHHOLD = 100;
    private final int ITERATIONS = 1000000;

    private LinkedList<TransformWrapper> transforms;
    private Color foreground;
    private Color background;
    private BufferedImage image;
    private Graphics2D g2d;
    private int[] distribution;

    // default values
    public IFS(){
        this.image = new BufferedImage(1000, 1000, BufferedImage.TYPE_INT_ARGB);
        this.foreground = Color.BLACK;
        this.background = Color.WHITE;
    }

    public BufferedImage generateImage(){
        g2d = (Graphics2D) image.createGraphics();
        g2d.setColor(background);
        g2d.fillRect(0, 0, image.getWidth(), image.getHeight());

        // debug
        for (TransformWrapper tw : transforms) {
            System.out.println(tw.getTransform().toString());
        }

        Point2D.Double point = randomPoint();
        Point2D.Double temp = point;
        System.out.println("(x, y): (" + point.x + ", " + point.y + ")" );
        AffineTransform at;

        g2d.setColor(foreground);
        for (int i = 0; i < DEPTH_THRESHHOLD; i++) {
            at = randomAffineTransform();
            at.transform(point, temp);
            point = temp;
            g2d.draw(new Line2D.Double(mapX(point.x), mapY(point.y),
                    mapX(point.x), mapY(point.y)));
        }

        for (int i = 0; i < ITERATIONS; i++){
            at = randomAffineTransform();
            at.transform(point, temp);
            point = temp;
            g2d.draw(new Line2D.Double(mapX(point.x), mapY(point.y),
                    mapX(point.x), mapY(point.y)));
        }

        // debug
        printDistribution();

        g2d.dispose();
        return image;
    }

    private AffineTransform randomAffineTransform(){
        Random rng = new Random();
        double num = rng.nextDouble();
        double sum = 0;
        int count = 0;

        for (TransformWrapper tw : transforms){
            sum += tw.getProbability();
            if (num < sum){
                // System.out.println(count);
                this.distribution[count]++;
                return tw.getTransform();
            }
            count++;
        }
        this.distribution[count] += 1;
        return transforms.getLast().getTransform();
    }

    // return actual distributions
    private void printDistribution() {
        for (int i = 0; i < distribution.length; i++) {
            System.out.println("dist: " + (double)distribution[i]/(double)ITERATIONS);
        }
    }

    // returns random point on the unit square
    private Point2D.Double randomPoint(){
        Random rng = new Random();
        return new Point2D.Double(rng.nextDouble(), rng.nextDouble());
    }

    // mapping function
    private double mapX(double d){
        return d * (double)image.getWidth();
    }

    // mapping function
    private double mapY(double d){
        return (1.0 - d) * (double)image.getWidth();
    }

    // GETTERS AND SETTERS
    public void setTransforms(LinkedList<TransformWrapper> transforms) {
        this.distribution = new int[transforms.size()];
        for (int i = 0; i < distribution.length; i++){
            distribution[i] = 0;
        }
        System.out.println(transforms.size());
        this.transforms = transforms;
    }

    public void setForeground(Color foreground) {
        this.foreground = foreground;
    }

    public void setBackground(Color background) {
        this.background = background;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
}
