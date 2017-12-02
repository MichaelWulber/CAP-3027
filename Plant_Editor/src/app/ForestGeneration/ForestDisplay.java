package app.ForestGeneration;

import LSystem.LSystemBuilder;
import LSystem.LSystemDescription;
import Plant.PlantComponent;
import Plant.Plant_Iterators.Iter;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.Shape3D;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.LinkedList;
import java.util.Random;

public class ForestDisplay {
    final public static int DEFAULT_WIDTH = 800;
    final public static int DEFAULT_HEIGHT = 600;

    private Stage stage;
    private Scene display;
    private Group root;
    private BorderPane borderPane;

    private int width;
    private int height;
    private int max;
    private int totalWeight;
    private LSystemDescription[] plants;
    private float[] weights;

    public ForestDisplay(int width, int height, LSystemDescription[] plants, float[] weights, int max, int totalWeight){
        this.width = width;
        this.height = height;
        this.plants = plants;
        this.max = max;
        this.totalWeight = totalWeight;
        this.weights = weights;

        initStage();
        initDisplay();
        showDisplay();
    }

    private void initStage(){
        stage = new Stage();
        root = new Group();
        display = new Scene(root, DEFAULT_WIDTH, DEFAULT_HEIGHT, true);
        display.setFill(Color.LIGHTBLUE);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Forest Image Display");
        stage.setResizable(false);
    }

//    private void initMenuBar(){
//        MenuBar menuBar = new MenuBar();
//        // --- exit ---
//        MenuItem exit = new MenuItem("Close");
//        exit.setOnAction(e -> System.exit(0));
//
//        Menu fileMenu = new Menu("File");
//
//        fileMenu.getItems().addAll(exit);
//        menuBar.getMenus().addAll(fileMenu);
//        borderPane.setTop(menuBar);
//    }

    private void initDisplay(){
        Random rng = new Random();
        LSystemBuilder builder = new LSystemBuilder();
        LinkedList<CircleBound> bounds = new LinkedList<>();

        int index = 0;
        int failureCount = 0;
        boolean cont = true;
        int count = 0;
        float rand = 0;
        float randCount = 0;

        while (cont && count < max) {
            failureCount = 0;

            rand = rng.nextFloat() * totalWeight;
            randCount = weights[0];
            index = 0;
            while(randCount < rand){
                System.out.println(index + ": " + randCount);
                index++;
                randCount += weights[index];
            }

            builder.setLsd(plants[index]);
            PlantComponent plant = builder.getPlantParts();
            Group subGroup = new Group();
            Iter iter = plant.makePreOrderIter();
            for (iter.reset(); iter.isValid(); iter.next()) {
                for (Shape3D shape : iter.currentItem().getShapes()) {
                    subGroup.getChildren().add(shape);
                }
            }
            CircleBound bound = new CircleBound(rng.nextDouble() * 10000 - 5000, rng.nextDouble() * 10000 - 5000, plants[index].radius * 10);
            while (!fits(bound, bounds) && cont){
                bound = new CircleBound(rng.nextDouble() * 10000 - 5000, rng.nextDouble() * 10000 - 5000, plants[index].radius * 10);
                failureCount++;
                if (failureCount > 100){
                    cont = false;
                }
            }
            subGroup.setTranslateX(rng.nextDouble() * 8000 - 4000);
            subGroup.setTranslateZ(rng.nextDouble() * 8000 - 4000);
            root.getChildren().add(subGroup);
            count++;
        }

        Box ground = new Box(10000, 1, 10000);
        ground.setDrawMode(DrawMode.FILL);
        ground.setMaterial(new PhongMaterial(Color.TAN));
        root.getChildren().add(ground);

        // add camera
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setFieldOfView(45);
        camera.setTranslateY(-500);
        camera.setNearClip(0.001);
        camera.setFarClip(50000);

        Rotate rotateY = new Rotate(0, Rotate.Y_AXIS);
        Rotate rotateX = new Rotate(0, Rotate.X_AXIS);
        Translate translateY = new Translate(0, 0, 0);

        camera.getTransforms().addAll(rotateX, rotateY, translateY);
        display.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.D)){
                rotateY.setAngle(rotateY.getAngle() + 2.0);
            } else if (e.getCode().equals(KeyCode.A)){
                rotateY.setAngle(rotateY.getAngle() - 2.0);
            } else if (e.getCode().equals(KeyCode.W)){
                rotateX.setAngle(rotateX.getAngle() + 2.0);
            } else if (e.getCode().equals(KeyCode.S)){
                rotateX.setAngle(rotateX.getAngle() - 2.0);
            } else if (e.getCode().equals(KeyCode.UP)){
                translateY.setY(translateY.getTy() - 10);
            } else if (e.getCode().equals(KeyCode.DOWN)){
                translateY.setY(translateY.getTy() + 10);
            } else if (e.getCode().equals(KeyCode.ESCAPE)){
                stage.close();
            }
        });
        display.setCamera(camera);

        // add light source
        PointLight light = new PointLight(Color.WHITE);
        light.setTranslateY(-500);
        root.getChildren().add(light);
    }

    public void showDisplay(){
        stage.setScene(display);
        stage.showAndWait();
    }

    private boolean fits(CircleBound bound, LinkedList<CircleBound> l){
        for (CircleBound cb : l){
            if (bound.intersects(cb)){
                return false;
            }
        }
        return true;
    }
}
