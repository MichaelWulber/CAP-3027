package app;

import javafx.application.Application;
import javafx.stage.Stage;

public class Launcher extends Application {
    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("test");
        WelcomeScene welcomeScene = new WelcomeScene(800, 400, stage);
        stage.setScene(welcomeScene);
        stage.show();
    }
}
