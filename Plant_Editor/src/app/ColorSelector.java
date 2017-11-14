package app;

import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class ColorSelector {

    public static Color color;

    private static float r = 0;
    private static float g = 0;
    private static float b = 0;

    public static void display(){
        Stage colorSelector = new Stage();
        BorderPane borderPane = new BorderPane();
        Scene scene = new Scene(borderPane, 500, 300);
        colorSelector.initModality(Modality.APPLICATION_MODAL);
        colorSelector.setTitle("color selection");


        // preview color
        Rectangle preview = new Rectangle(150, 150, new Color(r, g, b, 1));
        borderPane.setRight(preview);

        // red color slider
        Slider red = new Slider(0.0, 1.0, 0.0);
        red.setOrientation(Orientation.HORIZONTAL);
        red.setPrefWidth(300);
        red.valueProperty().addListener((obs, oldVal, newVal) -> {
            red.setValue(newVal.floatValue());
            setR(newVal.floatValue());
            preview.setFill(new Color(r, g, b, 1));
        });

        // green color sliders
        Slider green = new Slider(0.0, 1.0, 0.0);
        green.setOrientation(Orientation.HORIZONTAL);
        green.setPrefWidth(300);
        green.valueProperty().addListener((obs, oldVal, newVal) -> {
            green.setValue(newVal.floatValue());
            setG(newVal.floatValue());
            preview.setFill(new Color(r, g, b, 1));
        });

        // blue sliders
        Slider blue = new Slider(0.0, 1.0, 0.0);
        blue.setOrientation(Orientation.HORIZONTAL);
        blue.setPrefWidth(300);
        blue.valueProperty().addListener((obs, oldVal, newVal) -> {
            blue.setValue(newVal.floatValue());
            setB(newVal.floatValue());
            preview.setFill(new Color(r, g, b, 1));
        });

        VBox components = new VBox();
        components.getChildren().addAll(red, green, blue);
        borderPane.setCenter(components);

        // buttons
        Button okayButton = new Button();
        okayButton.setText("okay");
        okayButton.setAlignment(Pos.BOTTOM_CENTER);
        okayButton.setPrefWidth(150);
        okayButton.setOnAction(e -> {
            color = new Color(r, g, b, 1);
            colorSelector.close();
        });

        Button cancelButton = new Button();
        cancelButton.setText("cancel");
        cancelButton.setAlignment(Pos.BOTTOM_CENTER);
        cancelButton.setPrefWidth(150);
        cancelButton.setOnAction(e -> {
            colorSelector.close();
        });

        HBox buttons = new HBox();
        buttons.getChildren().addAll(cancelButton, okayButton);
        borderPane.setBottom(buttons);

        colorSelector.setScene(scene);
        colorSelector.show();
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
