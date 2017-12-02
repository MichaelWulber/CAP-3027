package LSystem;

import Mesh.LeafDescription;
import javafx.scene.paint.Color;

import java.io.File;
import java.util.Scanner;

public class LSystemFileParser {
    public static LSystemDescription parseLSYS(File file) throws Exception{
        LSystemDescription lsd = new LSystemDescription();

        Scanner scanner = new Scanner(file);
        double d;
        int i;
        String line;
        float r;
        float g;
        float b;
        int numRules;

        double radius1;
        double radius2;
        double tilt;

        // branching degree
        if (scanner.hasNextInt()) {
            lsd.branchingDegree = scanner.nextInt();
        } else {
            throw new Exception("corrupted file: invalid branching degree");
        }

        // scale
        if (scanner.hasNextDouble()) {
            lsd.scale = scanner.nextDouble();
        } else {
            throw new Exception("corrupted file: invalid scale");
        }

        // dPitch
        if (scanner.hasNextDouble()) {
            lsd.dPitch = scanner.nextDouble();
        } else {
            throw new Exception("corrupted file: invalid pitch");
        }

        // dYaw
        if (scanner.hasNextDouble()) {
            lsd.dYaw = scanner.nextDouble();
        } else {
            throw new Exception("corrupted file: invalid yaw");
        }

        // dRoll
        if (scanner.hasNextDouble()) {
            lsd.dRoll = scanner.nextDouble();
        } else {
            throw new Exception("corrupted file: invalid roll");
        }

        // radius
        if (scanner.hasNextDouble()) {
            lsd.radius = scanner.nextDouble();
        } else {
            throw new Exception("corrupted file: invalid initial radius");
        }

        // shrinkRate
        if (scanner.hasNextDouble()) {
            lsd.shrinkRate = scanner.nextDouble();
        } else {
            throw new Exception("corrupted file: invalid shrink-rate");
        }

        // resolution
        if (scanner.hasNextInt()) {
            lsd.resolution = scanner.nextInt();
        } else {
            throw new Exception("corrupted file: invalid resolution)");
        }

        // color
        if (scanner.hasNextFloat()) {
            r = scanner.nextFloat();
        } else {
            throw new Exception("corrupted file: invalid color component (r)");
        }

        if (scanner.hasNextFloat()) {
            g = scanner.nextFloat();
        } else {
            throw new Exception("corrupted file: invalid color component (g)");
        }

        if (scanner.hasNextFloat()) {
            b = scanner.nextFloat();
        } else {
            throw new Exception("corrupted file: invalid color component (b)");
        }

        lsd.color = new Color(r, g, b, 1);

        // leaf description
        if (scanner.hasNextFloat()) {
            r = scanner.nextFloat();
        } else {
            throw new Exception("corrupted file: invalid leaf color component (r)");
        }

        if (scanner.hasNextFloat()) {
            g = scanner.nextFloat();
        } else {
            throw new Exception("corrupted file: invalid leaf color component (g)");
        }

        if (scanner.hasNextFloat()) {
            b = scanner.nextFloat();
        } else {
            throw new Exception("corrupted file: invalid leaf color component (b)");
        }

        if (scanner.hasNextDouble()) {
            radius1 = scanner.nextDouble();
        } else {
            throw new Exception("corrupted file: invalid leaf radius1");
        }

        if (scanner.hasNextDouble()) {
            radius2 = scanner.nextDouble();
        } else {
            throw new Exception("corrupted file: invalid leaf radius2");
        }

        if (scanner.hasNextDouble()) {
            tilt = scanner.nextDouble();
        } else {
            throw new Exception("corrupted file: invalid leaf tilt");
        }

        lsd.ld = new LeafDescription(new Color(r, g, b, 1), radius1, radius2, tilt);

        if (scanner.hasNextInt()){
            numRules = scanner.nextInt();
        } else {
            throw new Exception("corrupted file: invalid number of rules");
        }

        if (scanner.hasNext()){
            lsd.seed = new StringBuilder(scanner.next());
        } else {
            throw new Exception("corrupted file: invalid seed");
        }


        scanner.nextLine();
        for (int j = 0; j < numRules; j++){
            line = removeWhiteSpace(scanner.nextLine());
//            System.out.println("line: " + "\"" + line + "\"");
            String[] rule = line.split("=");
            lsd.addRule(rule[0].charAt(0), new StringBuilder(rule[1]));
        }

        for (int j = 0; j < numRules; j++){
            line = removeWhiteSpace(scanner.nextLine());
//            System.out.println("line: " + "\"" + line + "\"");
            String[] prob = line.split("=");
            lsd.probs.put(prob[0].charAt(0), Double.valueOf(prob[1]));
        }

        return lsd;
    }

    private static String removeWhiteSpace(String input) {
        return input.replaceAll("\\s", "");
    }
}
