package com.example.project1maxledlighting;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    public static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {

        primaryStage = stage;
        stage.setTitle("project1maxledlighting");
        loadScene("view1.fxml");

    }

    public static void loadScene(String fxmlFile) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxmlFile));
        Scene scene = new Scene(fxmlLoader.load());
        String cssFile = App.class.getResource("style.css").toExternalForm();
        scene.getStylesheets().add(cssFile);
        primaryStage.setTitle("Power");
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
primaryStage.setMaximized(true);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}