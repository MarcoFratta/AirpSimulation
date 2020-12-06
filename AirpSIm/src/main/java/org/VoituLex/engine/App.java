package org.VoituLex.engine;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

     static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        Engine engine = new Engine(null, 3, 3,10);
        engine.start();
    }


    public static void main(String[] args) {
        launch();
    }

}