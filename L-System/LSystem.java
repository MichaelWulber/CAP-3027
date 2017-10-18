import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class LSystem {
    private BufferedImage image;
    private Color background;
    private Color foreground;
    private double delta;
    private double slsf;
    private String initiator;
    private HashMap<String, String> generators;
    private LinkedList<TurtleState> states;
    public TurtleState turtleState;



    // default values
    public LSystem(){
        this.image = new BufferedImage(1000, 1000, BufferedImage.TYPE_INT_ARGB);
        this.background = Color.WHITE;
        this.foreground = Color.BLACK;
        this.states = new LinkedList<TurtleState>();
        this.turtleState = new TurtleState();
    }

    // generation functions
    public StringBuilder stringGenerator(int generations){
        StringBuilder output = new StringBuilder(initiator);
        String replacement;

        for (int g = 0; g < generations; ++g){
            for (int i = 0; i < output.length(); ++i){
                i = replace(output, i);
            }
        }

        return output;
    }

    public BufferedImage imageGenerator(int generations){
        // Graphics2D object
        Graphics2D g2d = (Graphics2D)image.createGraphics();
        g2d.setColor(background);
        g2d.fillRect(0, 0, image.getWidth(), image.getHeight());
        g2d.setColor(foreground);

//        // Affine transform used to calculate the endpoint values
//        Point2D.Double basePoint = new Point2D.Double(turtleState.bsl, 0);
//        AffineTransform rotate = AffineTransform.getRotateInstance(this.delta);
//
//        // Affine transforms used map (x, y) onto the display
//        AffineTransform translate1 = AffineTransform.getTranslateInstance(1.0, 1.0);
//        AffineTransform flipY = AffineTransform.getScaleInstance(0.0, -1.0);
//        AffineTransform scale = AffineTransform.getScaleInstance(image.getWidth()/2, -image.getHeight()/2);
//        AffineTransform composite1 = scale;
//        composite1.concatenate(flipY);
//        composite1.concatenate(translate1);

        Point2D.Double endPoint;
        double seg_len = turtleState.bsl/Math.pow(slsf, generations);
        StringBuilder instructions = stringGenerator(generations);
        for (int i = 0; i < instructions.length(); ++i){
            if (instructions.charAt(i) == 'F'){
                endPoint = new Point2D.Double(turtleState.coord.x + turtleState.bsl * Math.cos(turtleState.bearing),
                        turtleState.coord.y + turtleState.bsl * Math.sin(turtleState.bearing));
                g2d.draw(new Line2D.Double(turtleState.coord.x, turtleState.coord.y, endPoint.x, endPoint.y));
            } else if (instructions.charAt(i) == 'f'){
                endPoint = new Point2D.Double(turtleState.coord.x + turtleState.bsl * Math.cos(turtleState.bearing),
                        turtleState.coord.y + turtleState.bsl * Math.sin(turtleState.bearing));
            } else if (instructions.charAt(i) == 'L'){
                endPoint = new Point2D.Double(turtleState.coord.x + turtleState.bsl * Math.cos(turtleState.bearing),
                        turtleState.coord.y + turtleState.bsl * Math.sin(turtleState.bearing));
                g2d.draw(new Line2D.Double(turtleState.coord.x, turtleState.coord.y, endPoint.x, endPoint.y));
            } else if (instructions.charAt(i) == 'l'){
                endPoint = new Point2D.Double(turtleState.coord.x + turtleState.bsl * Math.cos(turtleState.bearing),
                        turtleState.coord.y + turtleState.bsl * Math.sin(turtleState.bearing));
            } else if (instructions.charAt(i) == 'R'){
                endPoint = new Point2D.Double(turtleState.coord.x + turtleState.bsl * Math.cos(turtleState.bearing),
                        turtleState.coord.y + turtleState.bsl * Math.sin(turtleState.bearing));
                g2d.draw(new Line2D.Double(turtleState.coord.x, turtleState.coord.y, endPoint.x, endPoint.y));
            } else if (instructions.charAt(i) == 'r'){
                endPoint = new Point2D.Double(turtleState.coord.x + turtleState.bsl * Math.cos(turtleState.bearing),
                        turtleState.coord.y + turtleState.bsl * Math.sin(turtleState.bearing));
            } else if (instructions.charAt(i) == '['){
                states.push(new TurtleState(turtleState.x, turtleState.y, turtleState.bearing, turtleState.bsl));
            } else if (instructions.charAt(i) == ']'){
                turtleState = states.pop();
            } else if (instructions.charAt(i) == '+'){
                turtleState.bearing += delta;
            } else if (instructions.charAt(i) == '-'){
                turtleState.bearing -= delta;
            }
        }
        return this.image;
    }


    // utility functions
    private int replace(StringBuilder output, int index){
        if ( generators.containsKey(String.valueOf(output.charAt(index))) ){
            String replacement = generators.get(String.valueOf(output.charAt(index)));
            output.insert(index, replacement);
            output.deleteCharAt(index + replacement.length());
            return index + replacement.length();
        }
        return index;
    }

    private double mapX(double x){
        return ( (x + 1.0) * (image.getWidth()/2.0) );
    }

    private double mapY(double y){
        return ( image.getHeight() - (y + 1.0) * (image.getHeight()/2.0) );
    }
    // ...

    // GETTERS and SETTERS
    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public void setBackground(Color background) {
        this.background = background;
    }

    public void setForeground(Color foreground) {
        this.foreground = foreground;
    }

    public void setDelta(double delta) {
        this.delta = delta;
    }

    public void setSlsf(double slsf) {
        this.slsf = slsf;
    }

    public void setInitiator(String initiator) {
        this.initiator = initiator;
    }

    public void setGenerators(HashMap<String, String> generators) {
        this.generators = generators;
    }

    @Override
    public String toString() {
        String s = delta + "\n" + slsf + "\n" + initiator + "\n";
        for(Map.Entry<String, String> entry : generators.entrySet()) {
            s += "( " + entry.getKey() + ", " + entry.getValue() + " )\n";
        }
        return s;
    }
}
