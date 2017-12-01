package LSystem;

import Mesh.Meshy;
import Mesh.LeafDescription;
import Mesh.LeafMesh;
import Plant.PlantBranch;
import Plant.PlantComponent;
import Plant.Root;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

import java.util.LinkedList;
import java.util.Random;

public class LSystemBuilder {
    private LSystemDescription lsd;

    public LSystemBuilder(LSystemDescription lsd){
        this.lsd = lsd;
    }
    public LSystemBuilder(){
        lsd = new LSystemDescription();
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
        Random rng = new Random();
        if ( lsd.rules.containsKey(output.charAt(index)) && lsd.probs.get(output.charAt(index)) > rng.nextDouble() ){
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

        GrowingState state = new GrowingState(lsd.radius, -lsd.scale);
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
            } else if (c == 'L'){
                current.addShape(genLeaf(state));
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
                if (state.radius * lsd.shrinkRate > 1) {
                    state.radius *= lsd.shrinkRate;
                }
                plantStack.push(current);
            } else if (c == ']'){
                state = states.pop();
                current = plantStack.pop();
            }
        }

        return plant;
    }

    private PlantBranch getBranch(GrowingState gs){
        // create plant branch
        PlantBranch branch = new PlantBranch();

        // create transforms
        double[] vec = getVec(gs);
        double dx = vec[0]/(lsd.resolution);
        double dy = vec[1]/(lsd.resolution);
        double dz = vec[2]/(lsd.resolution);

        double xx = gs.posX;
        double yy = gs.posY;
        double zz = gs.posZ;

        double rr = gs.radius;
        gs.radius *= 1.0;
        double dr = (rr - rr * 1.0)/(lsd.resolution);

        double[][] skeleton = new double[lsd.resolution + 1][3];
        double[][] angles = new double[lsd.resolution + 1][2];
        double[] radii = new double[lsd.resolution + 1];

        for (int i = 0; i <= lsd.resolution; i++){
            skeleton[i][0] = xx;
            skeleton[i][1] = yy;
            skeleton[i][2] = zz;

            angles[i][0] = gs.pitch;
            angles[i][1] = gs.roll;

            radii[i] = rr;

            rr -= dr;
            xx += dx;
            yy += dy;
            zz += dz;
        }

        Meshy meshy = new Meshy(skeleton, angles, radii, 10, lsd.color);
        MeshView meshyView = meshy.getMeshView();
        branch.addShape(meshyView);
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

        // rotate around x-axis
        vec[1] = yy*Math.cos(p) + zz*Math.sin(p);
        vec[2] = -yy*Math.sin(p) + zz*Math.cos(p);
        xx = vec[0];
        yy = vec[1];
        zz = vec[2];

        // rotate around y-axis
        vec[0] = xx*Math.cos(y) - zz*Math.sin(y);
        vec[2] = xx*Math.sin(y) + zz*Math.cos(y);
        xx = vec[0];
        yy = vec[1];
        zz = vec[2];

        // rotate around z-axis
        vec[0] = xx*Math.cos(r) + yy*Math.sin(r);
        vec[1] = -xx*Math.sin(r) + yy*Math.cos(r);

        return vec;
    }

    private Shape3D genLeaf(GrowingState state){
        LeafMesh lm = new LeafMesh(lsd.ld);
        MeshView leaf = lm.getMeshView();

        leaf.setTranslateX(state.posX);
        leaf.setTranslateY(state.posY);
        leaf.setTranslateZ(state.posZ);
        Rotate ry = new Rotate(state.yaw, Rotate.Y_AXIS);
        leaf.getTransforms().add(ry);

        return leaf;
    }

    public void setLsd(LSystemDescription lsd){
        this.lsd = lsd;
    }
}
