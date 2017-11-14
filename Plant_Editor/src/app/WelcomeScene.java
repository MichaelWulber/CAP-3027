package app;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.Random;

public class WelcomeScene extends Scene
{
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
