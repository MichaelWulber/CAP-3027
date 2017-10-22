import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
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
        g2d.setRenderingHint( RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON );
        g2d.setColor(background);
        g2d.fillRect(0, 0, image.getWidth(), image.getHeight());
        g2d.setColor(foreground);

        StringBuilder instructions = stringGenerator(generations);
        double scale = (turtleState.bsl/2)/(Math.pow(slsf, generations));
        Point2D.Double endPoint = new Point2D.Double();

//        // Affine transform used to calculate the endpoint values
//        AffineTransform translate = AffineTransform.getTranslateInstance( scale * Math.cos(Math.toRadians(turtleState.getBearing())),
//                scale * Math.sin(Math.toRadians(turtleState.getBearing())) );
//
//        // Affine transforms used map (x, y) onto the display
//        AffineTransform translate1 = AffineTransform.getTranslateInstance(1.0, -1.0);
//        AffineTransform scale1 = AffineTransform.getScaleInstance(image.getWidth()/2, -image.getHeight()/2);
//        AffineTransform mapToDisplay = scale1;
//        mapToDisplay.concatenate(translate1);

        for (int i = 0; i < instructions.length(); ++i){
            if (instructions.charAt(i) == 'F'){
                endPoint = new Point2D.Double(turtleState.coord.x + scale * Math.cos(Math.toRadians(turtleState.getBearing())),
                        turtleState.coord.y + scale * Math.sin(Math.toRadians(turtleState.getBearing())));
                g2d.draw(new Line2D.Double(mapX(turtleState.coord.x), mapY(turtleState.coord.y), mapX(endPoint.x), mapY(endPoint.y)));
                turtleState.coord.x = endPoint.x;
                turtleState.coord.y = endPoint.y;
            }
            else if (instructions.charAt(i) == 'f'){
                endPoint = new Point2D.Double(turtleState.coord.x + scale * Math.cos(Math.toRadians(turtleState.getBearing())),
                        turtleState.coord.y + scale * Math.sin(Math.toRadians(turtleState.getBearing())));
                turtleState.coord.x = endPoint.x;
                turtleState.coord.y = endPoint.y;
            }
            else if (instructions.charAt(i) == 'L'){
                endPoint = new Point2D.Double(turtleState.coord.x + scale * Math.cos(Math.toRadians(turtleState.getBearing())),
                        turtleState.coord.y + scale * Math.sin(Math.toRadians(turtleState.getBearing())));
                g2d.draw(new Line2D.Double(mapX(turtleState.coord.x), mapY(turtleState.coord.y), mapX(endPoint.x), mapY(endPoint.y)));
                turtleState.coord.x = endPoint.x;
                turtleState.coord.y = endPoint.y;
            }
            else if (instructions.charAt(i) == 'l'){
                endPoint = new Point2D.Double(turtleState.coord.x + scale * Math.cos(Math.toRadians(turtleState.getBearing())),
                        turtleState.coord.y + scale * Math.sin(Math.toRadians(turtleState.getBearing())));
                turtleState.coord.x = endPoint.x;
                turtleState.coord.y = endPoint.y;
            }
            else if (instructions.charAt(i) == 'R'){
                endPoint = new Point2D.Double(turtleState.coord.x + scale * Math.cos(Math.toRadians(turtleState.getBearing())),
                        turtleState.coord.y + scale * Math.sin(Math.toRadians(turtleState.getBearing())));
                g2d.draw(new Line2D.Double(mapX(turtleState.coord.x), mapY(turtleState.coord.y), mapX(endPoint.x), mapY(endPoint.y)));
                turtleState.coord.x = endPoint.x;
                turtleState.coord.y = endPoint.y;
            }
            else if (instructions.charAt(i) == 'r'){
                endPoint = new Point2D.Double(turtleState.coord.x + scale * Math.cos(Math.toRadians(turtleState.getBearing())),
                        turtleState.coord.y + scale * Math.sin(Math.toRadians(turtleState.getBearing())));
                turtleState.coord.x = endPoint.x;
                turtleState.coord.y = endPoint.y;
            }
            else if (instructions.charAt(i) == '['){
                states.push(new TurtleState(turtleState.coord.x, turtleState.coord.y, turtleState.getBearing(), turtleState.bsl));
            }
            else if (instructions.charAt(i) == ']'){
                turtleState = states.pop();
            }
            else if (instructions.charAt(i) == '+'){
                turtleState.setBearing( (turtleState.getBearing() + delta) );
            }
            else if (instructions.charAt(i) == '-'){
                turtleState.setBearing( (turtleState.getBearing() - delta) );
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
            return index + replacement.length() - 1;
        }
        return index;
    }

    private double mapX(double x){
        return ( (x + 1.0) * (image.getWidth()/2.0) );
    }

    private double mapY(double y){
        return ( (1.0 - y) * (image.getHeight()/2.0) );
    }

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

    public BufferedImage getImage() {
        return image;
    }

    public Color getBackground() {
        return background;
    }

    public Color getForeground() {
        return foreground;
    }
}
