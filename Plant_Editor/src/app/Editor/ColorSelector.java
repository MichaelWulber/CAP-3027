package app.Editor;

import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jdk.nashorn.internal.runtime.regexp.joni.constants.StackPopLevel;


public class ColorSelector {

    public static Color color;

    public static float r = 0;
    public static float g = 0;
    public static float b = 0;

    public static Color display(){
        Stage colorSelector = new Stage();
        BorderPane borderPane = new BorderPane();
        Scene scene = new Scene(borderPane, 450, 175);
        colorSelector.initModality(Modality.APPLICATION_MODAL);
        colorSelector.setTitle("color selection");

        // preview color
        StackPane pane = new StackPane();
        pane.setPrefWidth(150);
        pane.setAlignment(Pos.CENTER);
        Rectangle preview = new Rectangle(100, 100, new Color(r, g, b, 1));
        pane.getChildren().add(preview);
        borderPane.setRight(pane);

        // red color slider
        Slider red = new Slider(0.0, 1.0, r);
        red.setOrientation(Orientation.HORIZONTAL);
        red.setPrefWidth(200);
        red.setMaxWidth(200);
        red.valueProperty().addListener((obs, oldVal, newVal) -> {
            red.setValue(newVal.floatValue());
            setR(newVal.floatValue());
            preview.setFill(new Color(r, g, b, 1));
        });

        // green color sliders
        Slider green = new Slider(0.0, 1.0, g);
        green.setOrientation(Orientation.HORIZONTAL);
        green.setPrefWidth(200);
        green.setMaxWidth(200);
        green.valueProperty().addListener((obs, oldVal, newVal) -> {
            green.setValue(newVal.floatValue());
            setG(newVal.floatValue());
            preview.setFill(new Color(r, g, b, 1));
        });

        // blue sliders
        Slider blue = new Slider(0.0, 1.0, b);
        blue.setOrientation(Orientation.HORIZONTAL);
        blue.setPrefWidth(200);
        blue.setMaxWidth(200);
        blue.valueProperty().addListener((obs, oldVal, newVal) -> {
            blue.setValue(newVal.floatValue());
            setB(newVal.floatValue());
            preview.setFill(new Color(r, g, b, 1));
        });

        VBox components = new VBox();
        components.setAlignment(Pos.CENTER);
        components.setSpacing(30);
        components.getChildren().addAll(red, green, blue);
        borderPane.setCenter(components);

        // buttons
        Button okayButton = new Button();
        okayButton.setAlignment(Pos.CENTER);
        okayButton.setText("okay");
        okayButton.setPrefWidth(150);
        okayButton.setOnAction(e -> {
            colorSelector.close();
        });

        HBox buttons = new HBox();
        buttons.setPrefHeight(50);
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(10);
        buttons.getChildren().addAll(okayButton);
        borderPane.setBottom(buttons);

        colorSelector.setScene(scene);
        colorSelector.showAndWait();

        return new Color(r, g, b, 1);
    }

    private static void setR(float val){
        r = val;
    }

    private static void setG(float val){
        g = val;
    }

    private static void setB(float val){
        b = val;
    }

}
