package app.Editor;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ErrorScene {
    public static void display(String message){
        Stage errorWindow = new Stage();
        BorderPane borderPane = new BorderPane();
        Scene scene = new Scene(borderPane, 450, 175);
        errorWindow.initModality(Modality.APPLICATION_MODAL);
        errorWindow.setTitle("ERROR");

        VBox content = new VBox();
        content.setAlignment(Pos.CENTER);
        content.setSpacing(10);
        content.minHeight(150);
        borderPane.setCenter(content);

        Label errorMessage = new Label(message);
        content.getChildren().add(errorMessage);

        Button okayButton = new Button();
        okayButton.setAlignment(Pos.CENTER);
        okayButton.setText("Okay");
        okayButton.setPrefWidth(150);
        okayButton.setOnAction(e -> {
            errorWindow.close();
        });
        content.getChildren().add(okayButton);

        errorWindow.setScene(scene);
        errorWindow.showAndWait();
    }
}
