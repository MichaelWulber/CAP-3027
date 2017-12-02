package app.ForestGeneration;

import LSystem.LSystemDescription;
import LSystem.LSystemFileParser;
import app.Editor.EditorScene;
import app.Editor.ErrorScene;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Comparator;
import java.util.List;

public class GenerateForestScene extends Scene{
    final public static int DEFAULT_WIDTH = 800;
    final public static int DEFAULT_HEIGHT = 600;

    private Stage primaryStage;

    private int width;
    private int height;
    private int max;

    public GenerateForestScene(int width, int height, Stage primaryStage){
        super(new BorderPane(), width, height);
        this.primaryStage = primaryStage;
        this.width = width;
        this.height = height;
        this.max = 25;

        initMenuBar();
        initDisplay();

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
                primaryStage.setScene(new EditorScene(EditorScene.EDITOR_SCENE_WIDTH, EditorScene.EDITOR_SCENE_HEIGHT, LSystemFileParser.parseLSYS(fileChooser.showOpenDialog(primaryStage)), primaryStage));
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
        ((BorderPane) this.getRoot()).setTop(menuBar);
    }

    private void initDisplay(){
        BorderPane borderPane = (BorderPane) this.getRoot();

        ObservableList<TableEntry> entries = FXCollections.observableArrayList();
        TableView<TableEntry> tableView = new TableView<TableEntry>();

        TableColumn<TableEntry, String> fileNames = new TableColumn<>("Name");
        fileNames.setPrefWidth(width/2);
        fileNames.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<TableEntry, Float> plantWeights = new TableColumn<>("Weight");
        plantWeights.setPrefWidth(width/2);
        plantWeights.setCellValueFactory(new PropertyValueFactory<>("weight"));

        tableView.setItems(entries);
        tableView.getColumns().addAll(fileNames, plantWeights);
        borderPane.setCenter(tableView);



        Button selectionButton = new Button();
        selectionButton.setText("Select Plants");
        selectionButton.setPrefWidth(150);
        selectionButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("lsys files","*.lsys"));
            List<File> files = fileChooser.showOpenMultipleDialog(primaryStage);
            for (File file : files){
                if (file != null && isUnique(file, entries)){
                    entries.add(new TableEntry(file, 1));
                }
            }
        });

        Button addButton = new Button();
        addButton.setText("Add");
        addButton.setPrefWidth(150);
        addButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("lsys files","*.lsys"));
            File file = fileChooser.showOpenDialog(primaryStage);
            if (file != null && isUnique(file, entries)){
                entries.add(new TableEntry(file, 1));
            }
        });

        Button removeButton = new Button();
        removeButton.setText("Remove");
        removeButton.setPrefWidth(150);
        removeButton.setOnAction(e -> {
            TableEntry removed = (TableEntry) tableView.getSelectionModel().getSelectedItem();
            entries.remove(removed);
        });

        HBox addRemove = new HBox();
        addRemove.setAlignment(Pos.CENTER);
        addRemove.setSpacing(10);
        addRemove.getChildren().addAll(selectionButton, addButton, removeButton);


        // set weight widgets
        TextField weightField = new TextField("");
        weightField.setPrefWidth(150);

        Button weightButton = new Button();
        weightButton.setText("Set Weight");
        weightButton.setPrefWidth(150);
        weightButton.setOnAction(e -> {
            TableEntry alter = (TableEntry) tableView.getSelectionModel().getSelectedItem();
            try {
                alter.weight = Float.valueOf(weightField.getText());
            } catch (Exception exception){
                ErrorScene.display(weightField.getText() + " is not a valid float.");
            }
            tableView.refresh();
        });

        // set max number of plants widgets
        TextField maxField = new TextField("");
        weightField.setPrefWidth(150);

        Button maxButton = new Button();
        maxButton.setText("Set Max");
        maxButton.setPrefWidth(150);
        maxButton.setOnAction(e -> {
            TableEntry alter = (TableEntry) tableView.getSelectionModel().getSelectedItem();
            try {
                max = Integer.valueOf(maxField.getText());
            } catch (Exception exception){
                ErrorScene.display(maxField.getText() + " is not a valid integer.");
            }
        });

        HBox inputs = new HBox();
        inputs.setAlignment(Pos.CENTER);
        inputs.setSpacing(10);
        inputs.getChildren().addAll(weightField, weightButton, maxField, maxButton);

        // generate forest
        Button generateButton = new Button();
        generateButton.setText("Generate Forest");
        generateButton.setPrefWidth(150);
        generateButton.setOnAction(e -> {
            try {
                LSystemDescription[] plants = new LSystemDescription[entries.size()];
                entries.sort(new Comparator<TableEntry>() {
                    @Override
                    public int compare(TableEntry o1, TableEntry o2) {
                        return (o1.weight < o2.weight) ? 1:0;
                    }
                });
                int totalWieght = 0;
                float[] weights = new float[entries.size()];
                for (int i = 0; i < entries.size(); ++i) {
                    plants[i] = LSystemFileParser.parseLSYS(entries.get(i).file);
                    weights[i] = entries.get(i).weight;
                    totalWieght += entries.get(i).weight;
                }
                ForestDisplay forest = new ForestDisplay(ForestDisplay.DEFAULT_WIDTH, ForestDisplay.DEFAULT_HEIGHT, plants, weights, max, totalWieght);
            } catch (Exception exception){
                ErrorScene.display("Error Generating Forest");
            }
        });

        VBox buttonGroups = new VBox();
        buttonGroups.setMinHeight(125);
        buttonGroups.setAlignment(Pos.CENTER);
        buttonGroups.setSpacing(10);
        buttonGroups.getChildren().addAll(addRemove, inputs, generateButton);
        borderPane.setBottom(buttonGroups);
    }

    private boolean isUnique(File file1, ObservableList<TableEntry> selectedFiles) {
        for (TableEntry te : selectedFiles){
            if (file1.getName().equals(te.file.getName())) {
                return false;
            }
        }
        return true;
    }
}
