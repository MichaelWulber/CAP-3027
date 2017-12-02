package app;

import LSystem.LSystemFileParser;
import app.Editor.EditorScene;
import app.Editor.ErrorScene;
import app.ForestGeneration.GenerateForestScene;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Random;

public class WelcomeScene extends Scene
{
    final public static int WELCOME_SCENE_WIDTH = 800;
    final public static int WELCOME_SCENE_HEIGHT = 600;

    private Stage primaryStage;
    private int width;
    private int height;

    final private Random rng = new Random();
    private String[] hints;
    private int curIndex;

    private Label hint = new Label();
    private Button previuos = new Button();
    private Button next = new Button();


    public WelcomeScene(int width, int height, Stage primaryStage){
        super(new BorderPane(), width, height);
        this.primaryStage = primaryStage;
        this.width = width;
        this.height = height;
        initMenuBar();
        initHints();
        initButtons();
    }

    private void initMenuBar(){
        MenuBar menuBar = new MenuBar();

        // --- file menu ---
        Menu fileMenu = new Menu("File");

        // file chooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("lsys files","*.lsys"));

        // --- new plant ---
        MenuItem newPlant = new MenuItem("New Plant");
        newPlant.setOnAction(e -> {

            primaryStage.setScene(new EditorScene(EditorScene.EDITOR_SCENE_WIDTH, EditorScene.EDITOR_SCENE_HEIGHT, primaryStage));
        });

        // --- load plant ---
        MenuItem loadPlant = new MenuItem("Load Plant");
        loadPlant.setOnAction(e -> {
            try {
                File file = fileChooser.showOpenDialog(primaryStage);
                if (file != null) {
                    primaryStage.setScene(new EditorScene(EditorScene.EDITOR_SCENE_WIDTH, EditorScene.EDITOR_SCENE_HEIGHT, LSystemFileParser.parseLSYS(file), primaryStage));
                }
            } catch (Exception exception){
                ErrorScene.display(exception.toString());
            }
        });

        // --- generate forest ---
        MenuItem generateForest = new MenuItem("Generate Forest");
        generateForest.setOnAction(e -> {
            try {
                primaryStage.setScene(new GenerateForestScene(GenerateForestScene.DEFAULT_WIDTH, GenerateForestScene.DEFAULT_HEIGHT, primaryStage));
            } catch (Exception exception){
                ErrorScene.display(exception.toString());
            }
        });

        // --- exit ---
        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(e -> System.exit(0));

        // other menu items
        // [...]

        // combine menu components
        fileMenu.getItems().addAll(newPlant, loadPlant, generateForest, exit);
        menuBar.getMenus().addAll(fileMenu);
        BorderPane borderPane = (BorderPane) this.getRoot();
        borderPane.setTop(menuBar);
    }

    private void initHints(){
        this.hints = new String[]{
                "There are many types of instructions: (F, I, J, K, +, -, \\, /, &, ^, [, ], L)",
                "(F, I, J, K) all cause a branch to be created at the current angle",
                "(+, -) all rotate the current state by angle around the x-axis",
                "(\\, /) all rotate the current state by angle around the y-axis",
                "(&, ^) all rotate the current state by angle around the z-axis",
                "([, ]) store and restore the state respectively",
                "(L) causes a leaf to be drawn at the current position and state",
                "All other characters such as X, Y, Z, N... serve as rewrite characters which you can use to store complex branching patterns"
        };

        // choose random first hint
        curIndex = rng.nextInt(hints.length);
        hint.setText( hints[curIndex] );

        // add hint to the scene
        BorderPane borderPane = (BorderPane) this.getRoot();
        borderPane.setCenter(hint);
    }

    private void initButtons(){
        HBox buttons = new HBox();
        buttons.setPrefHeight(200);
        buttons.setPrefWidth(100);
        buttons.setSpacing(20);
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(previuos, next);

        BorderPane borderPane = (BorderPane) this.getRoot();
        borderPane.setBottom(buttons);

        // previous
        previuos.setText("previous");
        previuos.setAlignment(Pos.CENTER);
        previuos.setPrefWidth(buttons.getPrefWidth());
        previuos.setOnAction(e -> {
            curIndex--;
            if (curIndex < 0){
                curIndex = hints.length - 1;
            }
            hint.setText( hints[curIndex] );
        });

        // next
        next.setText("next");
        next.setAlignment(Pos.CENTER);
        next.setPrefWidth(buttons.getPrefWidth());
        next.setOnAction(e -> {
            curIndex++;
            if (curIndex > hints.length - 1){
                curIndex = 0;
            }
            hint.setText( hints[curIndex] );
        });
    }

}
