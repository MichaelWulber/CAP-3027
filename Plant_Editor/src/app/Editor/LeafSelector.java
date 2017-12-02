package app.Editor;

import Mesh.LeafDescription;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
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

public class LeafSelector {

    public static float r = 0;
    public static float g = 0;
    public static float b = 0;

    public static double r1 = 1;
    public static double r2 = 1;
    public static double tilt = 0;

    public static LeafDescription display(LeafDescription ld){
        Stage colorSelector = new Stage();
        BorderPane borderPane = new BorderPane();
        Scene scene = new Scene(borderPane, 450, 450);
        colorSelector.initModality(Modality.APPLICATION_MODAL);
        colorSelector.setTitle("color selection");

        // set values
        r = (float) ld.color.getRed();
        g = (float) ld.color.getGreen();
        b = (float) ld.color.getBlue();

        r1 = ld.r1;
        r2 = ld.r2;
        tilt = ld.tilt;

        // preview color
        StackPane pane = new StackPane();
        pane.setMinWidth(150);
        pane.setMinHeight(150);
        pane.setPadding(new Insets(40.0));
        pane.setAlignment(Pos.TOP_CENTER);
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

        Label r1Label = new Label("Radius 1: " +  String.format("%.2f", r1));
        // r1 slider
        Slider radius1 = new Slider(0.0, 100.0, r1);
        radius1.setOrientation(Orientation.HORIZONTAL);
        radius1.setPrefWidth(200);
        radius1.setMaxWidth(200);
        radius1.valueProperty().addListener((obs, oldVal, newVal) -> {
            radius1.setValue(newVal.doubleValue());
            setR1(newVal.doubleValue());
            r1Label.setText("Radius 1: " +  String.format("%.2f", r1));
        });

        // r2 slider
        Label r2Label = new Label("Radius 2: " +  String.format("%.2f", r2));
        Slider radius2 = new Slider(1.0, 100.0, r2);
        radius2.setOrientation(Orientation.HORIZONTAL);
        radius2.setPrefWidth(200);
        radius2.setMaxWidth(200);
        radius2.valueProperty().addListener((obs, oldVal, newVal) -> {
            radius2.setValue(newVal.doubleValue());
            setR2(newVal.doubleValue());
            r2Label.setText("Radius 2: " +  String.format("%.2f", r2));
        });

        // tilt slider
        Label tiltLabel = new Label("Tilt: " +  String.format("%.2f", tilt));
        Slider tiltSlider = new Slider(0.0, 180.0, b);
        tiltSlider.setOrientation(Orientation.HORIZONTAL);
        tiltSlider.setPrefWidth(200);
        tiltSlider.setMaxWidth(200);
        tiltSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            tiltSlider.setValue(newVal.doubleValue());
            setTilt(newVal.doubleValue());
            tiltLabel.setText("Tilt: " +  String.format("%.2f", tilt));
        });

        VBox components = new VBox();
        components.setAlignment(Pos.CENTER);
        components.setSpacing(20);
        components.getChildren().addAll(red, green, blue, r1Label, radius1, r2Label, radius2, tiltLabel, tiltSlider);
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
        buttons.setPrefHeight(150);
        buttons.setPrefHeight(50);
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(10);
        buttons.getChildren().addAll(okayButton);
        borderPane.setBottom(buttons);

        colorSelector.setScene(scene);
        colorSelector.showAndWait();

        return new LeafDescription(new Color(r, g, b, 1), r1, r2, tilt);
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

    private static void setR1(double val){
        r1 = val;
    }

    private static void setR2(double val){
        r2 = val;
    }

    private static void setTilt(double val){
        tilt = val;
    }
}
