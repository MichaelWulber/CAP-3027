import java.awt.*;
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
        StringBuilder instructions = stringGenerator(generations);
        for (int i = 0; i < instructions.length(); ++i){
            if (instructions.charAt(i) == 'F') {

            } else if (instructions.charAt(i) == 'F'){

            } else if (instructions.charAt(i) == 'f'){

            } else if (instructions.charAt(i) == 'L'){

            } else if (instructions.charAt(i) == 'l'){

            } else if (instructions.charAt(i) == 'R'){

            } else if (instructions.charAt(i) == 'r'){

            } else if (instructions.charAt(i) == '['){

            } else if (instructions.charAt(i) == ']'){

            } else if (instructions.charAt(i) == '+'){

            } else if (instructions.charAt(i) == '-'){

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
