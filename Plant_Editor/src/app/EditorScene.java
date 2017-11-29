package app;

import LSystem.LSystemBuilder;
import LSystem.LSystemDescription;
import LSystem.LSystemFileParser;
import Plant.PlantComponent;
import Plant.Plant_Iterators.Iter;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.Shape3D;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;
import javax.xml.crypto.dsig.Transform;
import java.io.File;
import java.io.FileWriter;

public class EditorScene extends Scene {
    final public static int EDITOR_SCENE_WIDTH = 800;
    final public static int EDITOR_SCENE_HEIGHT = 600;
    final private int SUBSCENE_WIDTH = 600;
    final private int VBOX_WIDTH = 200;

    final private Color DEFAULT_COLOR = Color.BLACK;
    final private int DEFAULT_BRANCHING_DEGREE = 0;

    private Stage primaryStage;
    Group root;
    SubScene display;

    private int width;
    private int height;

    private LSystemDescription plant;
    private LSystemBuilder builder;

    public EditorScene(int width, int height, LSystemDescription lsd, Stage primaryStage){
        super(new BorderPane());
        this.primaryStage = primaryStage;
        this.width = width;
        this.height = height;

        this.plant = lsd;
        this.builder = new LSystemBuilder(plant);

        initMenuBar();
        initDisplay();
        initTools();
    }

    public EditorScene(int width, int height, Stage primaryStage){
        super(new BorderPane());
        this.primaryStage = primaryStage;
        this.width = width;
        this.height = height;

        this.plant = new LSystemDescription();
        this.builder = new LSystemBuilder(plant);

        initMenuBar();
        initDisplay();
        initTools();
    }

    private void initMenuBar(){
        MenuBar menuBar = new MenuBar();

        // file chooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("lsys files","*.lsys"));

        // --- file menu ---
        Menu fileMenu = new Menu("File");

        // --- new plant ---
        MenuItem new_plant = new MenuItem("New Plant");
        new_plant.setOnAction(e -> {
            primaryStage.setScene(new EditorScene(EDITOR_SCENE_WIDTH, EDITOR_SCENE_HEIGHT, primaryStage));
        });

        // --- load plant ---
        MenuItem loadPlant = new MenuItem("Load Plant");
        loadPlant.setOnAction(e -> {
            try {
                primaryStage.setScene(new EditorScene(EditorScene.EDITOR_SCENE_WIDTH, EditorScene.EDITOR_SCENE_HEIGHT, LSystemFileParser.parseLSYS(fileChooser.showOpenDialog(primaryStage)), primaryStage));
                System.out.println("...");
            } catch (Exception exception){
                System.out.println(exception);
            }
        });

        // --- save plant ---
        MenuItem savePlant = new MenuItem("Save Plant");
        savePlant.setOnAction(e -> {
            try {
                File saveFile = fileChooser.showSaveDialog(primaryStage);
                if (saveFile != null){
                    String contents = plant.branchingDegree + "\n" +
                            plant.scale + "\n" +
                            plant.dPitch + "\n" +
                            plant.dYaw + "\n" +
                            plant.dRoll + "\n" +
                            plant.radius + "\n" +
                            plant.shrinkRate + "\n" +
                            plant.resolution + "\n" +
                            ColorSelector.r + "\n" +
                            ColorSelector.g + "\n" +
                            ColorSelector.b + "\n" +
                            plant.seed;
                    for (Character key : plant.rules.keySet()){
                        contents += "\n" + key.toString() + "=" + plant.rules.get(key).toString();
                    }
                    // file writer
                    FileWriter fileWriter = new FileWriter(saveFile);
                    fileWriter.write(contents);
                    fileWriter.close();
                }
            } catch (Exception exception){
                System.out.println(exception);
            }
        });

        // --- generate forest ---
        MenuItem generateForest = new MenuItem("Generate Forest");
        generateForest.setOnAction(e -> {
            try {
                primaryStage.setScene(new GenerateForestScene(GenerateForestScene.DEFAULT_WIDTH, GenerateForestScene.DEFAULT_HEIGHT, primaryStage));
            } catch (Exception exception){
                System.out.println(exception);
            }
        });

        // --- exit ---
        MenuItem exit = new MenuItem("exit");
        exit.setOnAction(e -> System.exit(0));

        // other menu items
        // [...]

        // combine menu components
        fileMenu.getItems().addAll(new_plant, loadPlant, savePlant, generateForest, exit);
        menuBar.getMenus().addAll(fileMenu);
        BorderPane borderPane = (BorderPane) this.getRoot();
        borderPane.setTop(menuBar);
    }

    private void initDisplay(){
        root = new Group();
        display = new SubScene(root, SUBSCENE_WIDTH, EDITOR_SCENE_HEIGHT, true, SceneAntialiasing.BALANCED);
        display.setFill(Color.LIGHTGRAY);
        ((BorderPane)this.getRoot()).setLeft(display);

        int count = 0;
        PlantComponent plant = builder.getPlantParts();
        Iter iter =  plant.makePreOrderIter();
        for (iter.reset(); iter.isValid(); iter.next()){
            for (Shape3D shape : iter.currentItem().getShapes()){
                root.getChildren().add(shape);
            }
            count++;
        }
        System.out.println(count);

        // add camera
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setFieldOfView(45);
        camera.setTranslateZ(-2000);
        camera.setNearClip(0.001);
        camera.setFarClip(25000);

        Rotate rotateX = new Rotate(0, -camera.getTranslateX(), -camera.getTranslateY(), -camera.getTranslateZ(), Rotate.X_AXIS);
        Rotate rotateY = new Rotate(0, -camera.getTranslateX(), -camera.getTranslateY(), -camera.getTranslateZ(), Rotate.Y_AXIS);

        camera.getTransforms().addAll(rotateX, rotateY);
        this.getRoot().setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.W)) {
                rotateX.setAngle(rotateX.getAngle() + 2.0);
            } else if (e.getCode().equals(KeyCode.S)) {
                rotateX.setAngle(rotateX.getAngle() - 2.0);
            } else if (e.getCode().equals(KeyCode.D)){
                rotateY.setAngle(rotateY.getAngle() - 2.0);
            } else if (e.getCode().equals(KeyCode.A)) {
                rotateY.setAngle(rotateY.getAngle() + 2.0);
            } else if (e.getCode().equals(KeyCode.R)) {
                rotateX.setAngle(0.0);
                rotateY.setAngle(0.0);
            } else if (e.getCode().equals(KeyCode.Z)){
                camera.setTranslateX(camera.getTranslateX() * 1.01);
                camera.setTranslateY(camera.getTranslateY() * 1.01);
                camera.setTranslateZ(camera.getTranslateZ() * 1.01);

                rotateX.setPivotX(-camera.getTranslateX());
                rotateX.setPivotY(-camera.getTranslateY());
                rotateX.setPivotZ(-camera.getTranslateZ());

                rotateY.setPivotX(-camera.getTranslateX());
                rotateY.setPivotY(-camera.getTranslateY());
                rotateY.setPivotZ(-camera.getTranslateZ());
            } else if (e.getCode().equals(KeyCode.X)){
                camera.setTranslateX(camera.getTranslateX() * 0.99);
                camera.setTranslateY(camera.getTranslateY() * 0.99);
                camera.setTranslateZ(camera.getTranslateZ() * 0.99);

                rotateX.setPivotX(-camera.getTranslateX());
                rotateX.setPivotY(-camera.getTranslateY());
                rotateX.setPivotZ(-camera.getTranslateZ());

                rotateY.setPivotX(-camera.getTranslateX());
                rotateY.setPivotY(-camera.getTranslateY());
                rotateY.setPivotZ(-camera.getTranslateZ());
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
        tools.setPrefWidth(VBOX_WIDTH);
        tools.setSpacing(10);
        tools.setAlignment(Pos.TOP_CENTER);
        tools.getContentBias();

        ((BorderPane) this.getRoot()).setRight(tools);

        Label branchingLabel = new Label("Degree of Branching: " + plant.branchingDegree);
        Slider branchingSlider = new Slider(0, 10, plant.branchingDegree);
        branchingSlider.setOrientation(Orientation.HORIZONTAL);
        branchingSlider.setPrefWidth(150);
        branchingSlider.setMaxWidth(150);
        branchingSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            branchingSlider.setValue(newVal.intValue());
            branchingLabel.setText("Degree of Branching: " + branchingSlider.getValue());
            plant.branchingDegree = newVal.intValue();
        });

        Label radiusLabel = new Label("Initial Branch Radius: " + plant.radius);
        Slider radiusSlider = new Slider(1, 100, plant.radius);
        radiusSlider.setOrientation(Orientation.HORIZONTAL);
        radiusSlider.setPrefWidth(150);
        radiusSlider.setMaxWidth(150);
        radiusSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            radiusSlider.setValue(newVal.intValue());
            radiusLabel.setText("Initial Branch Radius: " + radiusSlider.getValue());
            plant.radius = newVal.intValue();
        });

        Label thinningLabel = new Label("Branch Thinning Rate: " + String.format("%.2f", plant.shrinkRate));
        Slider thinningSlider = new Slider(0.0, 1.0, plant.shrinkRate);
        thinningSlider.setOrientation(Orientation.HORIZONTAL);
        thinningSlider.setPrefWidth(150);
        thinningSlider.setMaxWidth(150);
        thinningSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            thinningSlider.setValue(newVal.floatValue());
            thinningLabel.setText( "Branch Thinning Rate: " + String.format("%.2f", thinningSlider.getValue()) );
            plant.shrinkRate = newVal.floatValue();
        });

        Label segmentsLabel = new Label("Segments Per Branch: " + plant.resolution);
        Slider segmentsSlider = new Slider(1.0, 10.0, plant.resolution);
        segmentsSlider.setOrientation(Orientation.HORIZONTAL);
        segmentsSlider.setPrefWidth(150);
        segmentsSlider.setMaxWidth(150);
        segmentsSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            segmentsSlider.setValue(newVal.intValue());
            segmentsLabel.setText( "Segments Per Branch: " + segmentsSlider.getValue() );
            plant.resolution = newVal.intValue();
        });

        Label stepLabel = new Label("Scale: " + plant.scale);
        Slider stepSlider = new Slider(1.0, 1000, plant.scale);
        stepSlider.setOrientation(Orientation.HORIZONTAL);
        stepSlider.setPrefWidth(150);
        stepSlider.setMaxWidth(150);
        stepSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            stepSlider.setValue(newVal.intValue());
            stepLabel.setText("Scale: " + stepSlider.getValue());
            plant.scale = newVal.intValue();
        });

        Label xAngleDeltaLabel = new Label("X Angle Delta: " + plant.dPitch);
        Slider xAngleDeltaSlider = new Slider(0.0, 180.0, plant.dPitch);
        xAngleDeltaSlider.setOrientation(Orientation.HORIZONTAL);
        xAngleDeltaSlider.setPrefWidth(150);
        xAngleDeltaSlider.setMaxWidth(150);
        xAngleDeltaSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            xAngleDeltaSlider.setValue(newVal.intValue());
            xAngleDeltaLabel.setText("X Angle Delta: " + xAngleDeltaSlider.getValue());
            plant.dPitch = newVal.doubleValue();
        });

        Label yAngleDeltaLabel = new Label("Y Angle Delta: " + plant.dYaw);
        Slider yAngleDeltaSlider = new Slider(0.0, 180.0, plant.dYaw);
        yAngleDeltaSlider.setOrientation(Orientation.HORIZONTAL);
        yAngleDeltaSlider.setPrefWidth(150);
        yAngleDeltaSlider.setMaxWidth(150);
        yAngleDeltaSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            yAngleDeltaSlider.setValue(newVal.intValue());
            yAngleDeltaLabel.setText("Y Angle Delta: " + yAngleDeltaSlider.getValue());
            plant.dYaw = newVal.doubleValue();
        });

        Label zAngleDeltaLabel = new Label("Z Angle Delta: " + plant.dRoll);
        Slider zAngleDeltaSlider = new Slider(0.0, 180.0, plant.dRoll);
        zAngleDeltaSlider.setOrientation(Orientation.HORIZONTAL);
        zAngleDeltaSlider.setPrefWidth(150);
        zAngleDeltaSlider.setMaxWidth(150);
        zAngleDeltaSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            zAngleDeltaSlider.setValue(newVal.intValue());
            zAngleDeltaLabel.setText("Z Angle Delta: " + zAngleDeltaSlider.getValue());
            plant.dRoll = newVal.doubleValue();
        });

        HBox seedStuff = new HBox();
        seedStuff.setAlignment(Pos.CENTER);
        seedStuff.setSpacing(10);
        TextField seedField = new TextField("");
        seedField.setPrefWidth(70);
        seedField.setMaxWidth(70);
        Button seedButton = new Button("Set Seed");
        seedButton.setPrefWidth(70);
        seedButton.setMaxWidth(70);
        seedButton.setOnAction(e -> {
            plant.seed = new StringBuilder(seedField.getText());
        });
        seedStuff.getChildren().addAll(seedField, seedButton);


        Button ruleSelection = new Button("Rules");
        ruleSelection.setPrefWidth(150);
        ruleSelection.setOnAction(e -> {
            RuleSelector.display(plant);
        });

        Button flowerLeafSelection = new Button("Flower/Leaf Attributes");
        flowerLeafSelection.setPrefWidth(150);
        flowerLeafSelection.setOnAction(e -> {
            // ...
        });

        Button colorSelection = new Button("Color");
        colorSelection.setPrefWidth(150);
        colorSelection.setOnAction(e -> {
            this.plant.color = ColorSelector.display();
        });

        Button render = new Button("Render");
        render.setPrefWidth(150);
        render.setOnAction(e -> {
            redraw();
        });
        // [...]

        tools.getChildren().addAll(branchingLabel, branchingSlider,
                radiusLabel, radiusSlider,
                segmentsLabel, segmentsSlider,
                stepLabel, stepSlider,
                thinningLabel, thinningSlider,
                xAngleDeltaLabel, xAngleDeltaSlider,
                yAngleDeltaLabel, yAngleDeltaSlider,
                zAngleDeltaLabel, zAngleDeltaSlider,
                seedStuff,
                flowerLeafSelection,
                ruleSelection,
                colorSelection, render);
    }

    private void getSelectedColor(){
        ColorSelector.display();
        this.plant.color = ColorSelector.color;
    }

    private void redraw(){
        root.getChildren().clear();
        builder.setLsd(plant);
        PlantComponent plant = builder.getPlantParts();
        Iter iter =  plant.makePreOrderIter();
        for (iter.reset(); iter.isValid(); iter.next()){
            for (Shape3D shape : iter.currentItem().getShapes()){
                root.getChildren().add(shape);
            }
        }
    }
}
