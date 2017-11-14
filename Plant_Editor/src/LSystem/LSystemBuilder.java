package LSystem;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
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

    public Group getPlantParts() {
        StringBuilder instructions = stringGenerator();
        Group plantParts = new Group();

        GrowingState state = new GrowingState(lsd.scale);
        LinkedList<GrowingState> states = new LinkedList<GrowingState>();

        char c;

        for (int i = 0; i < instructions.length(); ++i){
            c = instructions.charAt(i);
            if (c == 'F'){
                plantParts.getChildren().add(getPart(state));
                grow(state);
            } else if (c == 'f'){
                grow(state);
            } else if (c == 'I'){
                plantParts.getChildren().add(getPart(state));
                grow(state);
            } else if (c == 'i'){
                grow(state);
            } else if (c == 'J'){
                plantParts.getChildren().add(getPart(state));
                grow(state);
            } else if (c == 'j'){
                grow(state);
            } else if (c == 'K'){
                plantParts.getChildren().add(getPart(state));
                grow(state);
            } else if (c == 'k'){
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
            } else if (c == ']'){
                state = states.pop();
                //draw leaves/flowers here?
            }
//            System.out.println(state.posX + ", " + state.posY + ", " + state.posZ);
        }

        return plantParts;
    }

    private Cylinder getPart(GrowingState gs){
        // *** THINGS THAT WILL BE VARIABLE IN THE FUTURE ***
        PhongMaterial greenMaterial = new PhongMaterial();
        greenMaterial.setDiffuseColor(Color.LIGHTGREEN);
        greenMaterial.setSpecularColor(Color.GREEN);
        // *** END ***

        // create mesh
        Cylinder cylinder = new Cylinder(gs.radius, gs.stepSize);

        // create transforms
        double[] vec = getVec(gs);
        System.out.println("Plotted at: " + (gs.posX + vec[0]/2) + ", " + (gs.posY + vec[1]/2) + ", " + (gs.posZ + vec[2]/2));
        System.out.println("vec: " + vec[0] + ", " + vec[1] + ", " + vec[2]);
        System.out.println("pitch: " + gs.pitch + " yaw: " + gs.yaw + " roll: " + gs.roll);
        Translate t = new Translate(gs.posX + vec[0]/2, gs.posY + vec[1]/2, gs.posZ + vec[2]/2);

        Rotate rx = new Rotate();
        rx.setAxis(Rotate.X_AXIS);
        //rx.setPivotX(gs.posX);
        rx.setAngle(gs.pitch);

        Rotate ry = new Rotate();
        ry.setAxis(Rotate.Y_AXIS);
        //ry.setPivotY(gs.posY);
        ry.setAngle(gs.yaw);

        Rotate rz = new Rotate();
        rz.setAxis(Rotate.Z_AXIS);
        //rz.setPivotZ(gs.posZ);
        rz.setAngle(gs.roll);

        // add tranforms and material
        cylinder.getTransforms().addAll(rx, ry, rz, t);
        cylinder.setMaterial(greenMaterial);
        return cylinder;
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
}
