package app;

import LSystem.LSystemBuilder;
import LSystem.LSystemDescription;
import javafx.geometry.Orientation;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

import javax.xml.crypto.dsig.Transform;

public class EditorScene extends Scene {

    final private Color DEFAULT_COLOR = Color.BLACK;
    final private int DEFAULT_BRANCHING_DEGREE = 1;

    private Stage primaryStage;
    private Group plantParts;

    private int width;
    private int height;
    private Color color;

    private LSystemDescription plant;
    private LSystemBuilder builder;

    public EditorScene(int width, int height, Stage primaryStage){
        super(new BorderPane(), width, height);

        this.primaryStage = primaryStage;
        this.width = width;
        this.height = height;
        this.color = Color.LIGHTGREEN;

        this.plant = new LSystemDescription();
        plant.branchingDegree = 4;
        plant.dRoll = 30;
        plant.scale = 50;
        plant.seed = new StringBuilder("X");
        plant.rules.put('X', new StringBuilder("F[&X]F[^X]&X"));
        plant.rules.put('F', new StringBuilder("FF"));

        this.builder = new LSystemBuilder(plant);

        initMenuBar();
        initDisplay();
        initTools();
    }

    private void initMenuBar(){
        MenuBar menuBar = new MenuBar();

        // --- file menu ---
        Menu fileMenu = new Menu("file");

        // --- new plant ---
        MenuItem new_plant = new MenuItem("new plant");
        new_plant.setOnAction(e -> {
            primaryStage.setScene(new EditorScene(800, 400, primaryStage));
        });

        // --- exit ---
        MenuItem exit = new MenuItem("exit");
        exit.setOnAction(e -> System.exit(0));

        // other menu items
        // [...]

        // combine menu components
        fileMenu.getItems().addAll(new_plant, exit);
        menuBar.getMenus().addAll(fileMenu);
        BorderPane borderPane = (BorderPane) this.getRoot();
        borderPane.setTop(menuBar);
    }

    private void initDisplay(){
        Group root = new Group();
        SubScene display = new SubScene(root, width * (3.0/4.0), height, true, SceneAntialiasing.BALANCED);
        display.setFill(Color.LIGHTGRAY);
        ((BorderPane)this.getRoot()).setLeft(display);

        // *** REMOVE ***
        PhongMaterial testMaterial = new PhongMaterial();
        testMaterial.setSpecularColor(Color.LIGHTSALMON);
        testMaterial.setDiffuseColor(Color.SKYBLUE);

        Sphere testSphere = new Sphere(100, 25);
        testSphere.setMaterial(testMaterial);
        testSphere.setDrawMode(DrawMode.FILL);
        root.getChildren().add(testSphere);
        // *** END ***
//        Group plantParts = builder.getPlantParts();

        // camera transform


        // add camera
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setFieldOfView(35);
        camera.setTranslateZ(-2000);
        camera.setNearClip(0.01);
        camera.setFarClip(4000);
        camera.setOnKeyPressed(e -> {
            switch (e.getCode()){
                case UP:
                    System.out.println("up");
                    // positive rotation about x
                    break;
                case DOWN:
                    System.out.println("down");
                    // positive rotation about x
                    break;
                case RIGHT:
                    System.out.println("right");
                    // positive rotation about y
                    break;
                case LEFT:
                    System.out.println("left");
                    // positive rotation about y
                    break;
            }
        });
        display.setCamera(camera);

        // add light source
        PointLight light = new PointLight(Color.WHITE);
        light.setTranslateX(-500);
        light.setTranslateY(1000);
        light.setTranslateZ(-500);
        root.getChildren().add(light);
    }

    private void initTools() {
        VBox tools = new VBox();
        ((BorderPane) this.getRoot()).setRight(tools);

        // tools
        Label branchingLabel = new Label("degree of branching: 0");
        Slider branchingSlider = new Slider(0, 10, DEFAULT_BRANCHING_DEGREE);
        branchingSlider.setOrientation(Orientation.HORIZONTAL);
        branchingSlider.setMajorTickUnit(1.0);
        branchingSlider.setPrefWidth(150);
        branchingSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            branchingSlider.setValue(newVal.intValue());
            branchingLabel.setText("degree of branching: " + branchingSlider.getValue());
            plant.branchingDegree = newVal.intValue();
        });

        Label radiusLabel = new Label("initial branch radius: 1");
        Slider radiusSlider = new Slider(1, 100, 1.0);
        radiusSlider.setOrientation(Orientation.HORIZONTAL);
        radiusSlider.setPrefWidth(150);
        radiusSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            radiusSlider.setValue(newVal.intValue());
            radiusLabel.setText("initial branch radius: " + radiusSlider.getValue());
            // plant.setRadius(newVal.intValue());
            // plant.display();
        });

        Button colorSelection = new Button("color");
        colorSelection.setPrefWidth(150);
        colorSelection.setOnAction(e -> {
            ColorSelector.display();
            this.plant.color = ColorSelector.color;
            redraw();
        });

        // [...]

        tools.getChildren().addAll(branchingLabel, branchingSlider,
                radiusLabel, radiusSlider,
                colorSelection);
    }

    private void getSelectedColor(){
        ColorSelector.display();
        this.plant.color = ColorSelector.color;
    }

    private void redraw(){
        plantParts = builder.getPlantParts();
    }
}
