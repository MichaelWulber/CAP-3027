package app;

import LSystem.LSystemFileParser;
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
        Menu fileMenu = new Menu("file");

        // file chooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("lsys files","*.lsys"));

        // --- new plant ---
        MenuItem newPlant = new MenuItem("new plant");
        newPlant.setOnAction(e -> {

            primaryStage.setScene(new EditorScene(EditorScene.EDITOR_SCENE_WIDTH, EditorScene.EDITOR_SCENE_WIDTH, primaryStage));
        });

        // --- load plant ---
        MenuItem loadPlant = new MenuItem("load plant");
        loadPlant.setOnAction(e -> {
            try {
                primaryStage.setScene(new EditorScene(EditorScene.EDITOR_SCENE_WIDTH, EditorScene.EDITOR_SCENE_WIDTH, LSystemFileParser.parseLSYS(fileChooser.showOpenDialog(primaryStage)), primaryStage));
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
        fileMenu.getItems().addAll(newPlant, loadPlant, exit);
        menuBar.getMenus().addAll(fileMenu);
        BorderPane borderPane = (BorderPane) this.getRoot();
        borderPane.setTop(menuBar);
    }

    private void initHints(){
        this.hints = new String[]{
                "hint 1",
                "hint 2",
                "hint 3"
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
        buttons.setPrefWidth(100);
        buttons.setSpacing(20);
        buttons.setAlignment(Pos.BOTTOM_CENTER);
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
