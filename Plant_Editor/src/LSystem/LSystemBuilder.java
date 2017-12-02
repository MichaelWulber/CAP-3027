package LSystem;

import Mesh.LeafMesh;
import Mesh.Meshy2;
import Plant.PlantBranch;
import Plant.PlantComponent;
import Plant.Root;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.Shape3D;
import javafx.scene.transform.Rotate;

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

        Meshy2 meshy = new Meshy2((float) gs.stepSize, (float) gs.radius, 10, lsd.color);
        MeshView meshyView = meshy.getMeshView();

        meshyView.setTranslateX(gs.posX);
        meshyView.setTranslateY(gs.posY);
        meshyView.setTranslateZ(gs.posZ);

        Rotate rx = new Rotate(gs.pitch, Rotate.X_AXIS);
        Rotate ry = new Rotate(gs.yaw, Rotate.Y_AXIS);
        Rotate rz = new Rotate(gs.roll, Rotate.Z_AXIS);

        meshyView.getTransforms().addAll(rz, ry, rx);

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
        vec[1] = yy*Math.cos(p) - zz*Math.sin(p);
        vec[2] = yy*Math.sin(p) + zz*Math.cos(p);
        xx = vec[0];
        yy = vec[1];
        zz = vec[2];

        // rotate around y-axis
        vec[0] = xx*Math.cos(y) + zz*Math.sin(y);
        vec[2] = -xx*Math.sin(y) + zz*Math.cos(y);
        xx = vec[0];
        yy = vec[1];
        zz = vec[2];

        // rotate around z-axis
        vec[0] = xx*Math.cos(r) - yy*Math.sin(r);
        vec[1] = xx*Math.sin(r) + yy*Math.cos(r);

        return vec;
    }

    private Shape3D genLeaf(GrowingState state){
        LeafMesh lm = new LeafMesh(lsd.ld);
        MeshView leaf = lm.getMeshView();

        leaf.setTranslateX(state.posX);
        leaf.setTranslateY(state.posY);
        leaf.setTranslateZ(state.posZ);
        Rotate rx = new Rotate(state.pitch, Rotate.X_AXIS);
        Rotate ry = new Rotate(state.yaw, Rotate.Y_AXIS);
        Rotate rz = new Rotate(state.roll, Rotate.Z_AXIS);
        leaf.getTransforms().addAll(rz, rx, ry);

        return leaf;
    }

    public void setLsd(LSystemDescription lsd){
        this.lsd = lsd;
    }
}
