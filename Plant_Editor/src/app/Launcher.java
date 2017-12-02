package app;

import javafx.application.Application;
import javafx.stage.Stage;

public class Launcher extends Application {
    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Forest");
        WelcomeScene welcomeScene = new WelcomeScene(WelcomeScene.WELCOME_SCENE_WIDTH,WelcomeScene.WELCOME_SCENE_HEIGHT, stage);
        stage.setResizable(false);
        stage.setScene(welcomeScene);
        stage.show();
    }
}
