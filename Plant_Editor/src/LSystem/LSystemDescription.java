package LSystem;

import javafx.scene.paint.Color;

import java.util.HashMap;

public class LSystemDescription {
    public int branchingDegree;
    public double scale;
    public double dPitch;
    public double dYaw;
    public double dRoll;
    public StringBuilder seed;
    public HashMap<Character, StringBuilder> rules;
    public Color color;

    public LSystemDescription(){
        this.branchingDegree = 0;
        this.scale = 0;
        this.dPitch = 0;
        this.dYaw = 0;
        this.dRoll = 0;
        this.seed = new StringBuilder("");
        this.rules = new HashMap<>();
    }

    public LSystemDescription(int branchingDegree, double scale, double dPitch, double dYaw, double dRoll, StringBuilder seed, HashMap<Character, StringBuilder> rules){
        this.branchingDegree = branchingDegree;
        this.scale = scale;
        this.dPitch = dPitch;
        this.dYaw = dYaw;
        this.dRoll = dRoll;
        this.seed = seed;
        this.rules = rules;
    }

}
