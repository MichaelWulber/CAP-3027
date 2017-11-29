package LSystem;

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

        // branching degree
        if (scanner.hasNextInt()) {
            lsd.branchingDegree = scanner.nextInt();
        } else {
            throw new Exception("corrupted file 0");
        }

        // scale
        if (scanner.hasNextDouble()) {
            lsd.scale = scanner.nextDouble();
        } else {
            throw new Exception("corrupted file 1");
        }

        // dPitch
        if (scanner.hasNextDouble()) {
            lsd.dPitch = scanner.nextDouble();
        } else {
            throw new Exception("corrupted file 2");
        }

        // dYaw
        if (scanner.hasNextDouble()) {
            lsd.dYaw = scanner.nextDouble();
        } else {
            throw new Exception("corrupted file 3");
        }

        // dRoll
        if (scanner.hasNextDouble()) {
            lsd.dRoll = scanner.nextDouble();
        } else {
            throw new Exception("corrupted file 4");
        }

        // radius
        if (scanner.hasNextDouble()) {
            lsd.radius = scanner.nextDouble();
        } else {
            throw new Exception("corrupted file 6");
        }

        // shrinkRate
        if (scanner.hasNextDouble()) {
            lsd.shrinkRate = scanner.nextDouble();
        } else {
            throw new Exception("corrupted file 7");
        }

        // resolution
        if (scanner.hasNextInt()) {
            lsd.resolution = scanner.nextInt();
        } else {
            throw new Exception("corrupted file 8");
        }

        // color
        if (scanner.hasNextFloat()) {
            r = scanner.nextFloat();
        } else {
            throw new Exception("corrupted file 9");
        }

        if (scanner.hasNextFloat()) {
            g = scanner.nextFloat();
        } else {
            throw new Exception("corrupted file 10");
        }

        if (scanner.hasNextFloat()) {
            b = scanner.nextFloat();
        } else {
            throw new Exception("corrupted file 11");
        }

        if (scanner.hasNextInt()){
            numRules = scanner.nextInt();
        } else {
            throw new Exception("corrupted file 12");
        }

        lsd.color = new Color(r, g, b, 1);

        if (scanner.hasNext()){
            lsd.seed = new StringBuilder(scanner.next());
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
