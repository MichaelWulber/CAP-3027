package LSystem;

import Plant.PlantBranch;
import Plant.PlantComponent;
import Plant.Root;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

import java.util.LinkedList;

public class LSystemBuilder {
    private LSystemDescription lsd;

    public LSystemBuilder(LSystemDescription lsd){
        this.lsd = lsd;
    }

    // string generation functions
    private StringBuilder stringGenerator(){
        StringBuilder output = new StringBuilder(lsd.seed);

        for (int g = 0; g < lsd.branchingDegree; ++g){
            for (int i = 0; i < output.length(); ++i){
                i = replace(output, i);
            }
        }

        return output;
    }

    private int replace(StringBuilder output, int index){
        if ( lsd.rules.containsKey(output.charAt(index)) ){
            StringBuilder replacement = lsd.rules.get(output.charAt(index));
            output.insert(index, replacement);
            output.deleteCharAt(index + replacement.length());
            return index + replacement.length() - 1;
        }
        return index;
    }

    public PlantComponent getPlantParts() {
        StringBuilder instructions = stringGenerator();

        //debug
        System.out.println(instructions);

        LinkedList<PlantComponent> plantStack = new LinkedList<PlantComponent>();
        Root plant = new Root();
        PlantComponent current = plant;

        GrowingState state = new GrowingState(lsd.scale);
        LinkedList<GrowingState> states = new LinkedList<GrowingState>();

        // reuse variables
        char c;
        PlantBranch branch;

        for (int i = 0; i < instructions.length(); ++i){
            c = instructions.charAt(i);
            if (c == 'F') {
                branch = getBranch(state);
                current.addChild(branch);
                System.out.println(current.getChildren().size());
                current = branch;
                grow(state);
            } else if (c == 'I'){
                branch = getBranch(state);
                current.addChild(branch);
                current = branch;
                grow(state);
            } else if (c == 'J'){
                branch = getBranch(state);
                current.addChild(branch);
                current = branch;
                grow(state);
            } else if (c == 'K'){
                branch = getBranch(state);
                current.addChild(branch);
                current = branch;
                grow(state);
            } else if (c == '+'){
                state.pitch += lsd.dPitch;
            } else if (c == '-'){
                state.pitch -= lsd.dPitch;
            } else if (c == '/'){
                state.yaw += lsd.dYaw;
            } else if (c == '\\'){
                state.yaw -= lsd.dYaw;
            } else if (c == '&'){
                state.roll += lsd.dRoll;
            } else if (c == '^'){
                state.roll -= lsd.dRoll;
            } else if (c == '['){
                states.push(new GrowingState(state));
                plantStack.push(current);
            } else if (c == ']'){
                System.out.println("branch popped");
                state = states.pop();
                current = plantStack.pop();
            }
        }

        return plant;
    }

    private PlantBranch getBranch(GrowingState gs){
        // *** THINGS THAT WILL BE VARIABLE IN THE FUTURE ***
        PhongMaterial greenMaterial = new PhongMaterial();
        greenMaterial.setDiffuseColor(Color.LIGHTGREEN);
        greenMaterial.setSpecularColor(Color.GREEN);

        int resolution = lsd.resolution;
        // *** END ***

        // create plant branch
        PlantBranch branch = new PlantBranch();

        // create transforms
        double[] vec = getVec(gs);
//        System.out.println("vec: " + vec[0] + ", " + vec[1] + ", " + vec[2]);
//        System.out.println("pitch: " + gs.pitch + " yaw: " + gs.yaw + " roll: " + gs.roll);
//        System.out.println("Line at: (" + (gs.posX) + ", " + (gs.posY) + ", " + (gs.posZ) + ") , (" + (gs.posX + vec[0]) + ", " + (gs.posY + vec[1]) + ", " + (gs.posZ + vec[2]) + ")");
        Sphere[] spheres = new Sphere[resolution];

        double dx = vec[0]/(resolution - 1);
        double dy = vec[1]/(resolution - 1);
        double dz = vec[2]/(resolution - 1);

        double xx = gs.posX;
        double yy = gs.posY;
        double zz = gs.posZ;

        Sphere sphere;

        for (int i = 0; i < resolution; i++){
            sphere = new Sphere(lsd.radius, 3);
            sphere.setMaterial(greenMaterial);
            sphere.setDrawMode(DrawMode.FILL);

            sphere.setTranslateX(xx);
            sphere.setTranslateY(-yy);
            sphere.setTranslateZ(zz);

            branch.addShape(sphere);

            xx += dx;
            yy += dy;
            zz += dz;
        }

        return branch;
    }

    private void grow(GrowingState gs){
        double[] vec = getVec(gs);
        gs.posX += vec[0];
        gs.posY += vec[1];
        gs.posZ += vec[2];
    }

    private double[] getVec(GrowingState gs){
        double p = Math.toRadians(gs.pitch);
        double y = Math.toRadians(gs.yaw);
        double r = Math.toRadians(gs.roll);
        double[] vec = new double[]{0, gs.stepSize, 0};

        double xx = vec[0];
        double yy = vec[1];
        double zz = vec[2];

//        System.out.println("\n...");
        // rotate around x-axis
        vec[1] = yy*Math.cos(p) - zz*Math.sin(p);
        vec[2] = yy*Math.sin(p) + zz*Math.cos(p);
        xx = vec[0];
        yy = vec[1];
        zz = vec[2];
//        System.out.println("vec: " + vec[0] + ", " + vec[1] + ", " + vec[2]);

        // rotate around y-axis
        vec[0] = xx*Math.cos(y) + zz*Math.sin(y);
        vec[2] = xx*Math.sin(y) + zz*Math.cos(y);
        xx = vec[0];
        yy = vec[1];
        zz = vec[2];
//        System.out.println("vec: " + vec[0] + ", " + vec[1] + ", " + vec[2]);

        // rotate around z-axis
        vec[0] = xx*Math.cos(r) - yy*Math.sin(r);
        vec[1] = xx*Math.sin(r) + yy*Math.cos(r);
//        System.out.println("vec: " + vec[0] + ", " + vec[1] + ", " + vec[2]);
//        System.out.println("...\n");

//        if (gs.radius > 1) {
//            gs.radius = gs.radius * 0.98;
//        }
//        gs.stepSize = gs.stepSize * 0.98;

        return vec;
    }

    public void setLsd(LSystemDescription lsd){
        this.lsd = lsd;
    }
}
