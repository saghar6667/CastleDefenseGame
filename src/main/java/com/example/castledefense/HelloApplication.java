package com.example.castledefense;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        GameMap map = new GameMap();
        map.startGameLoop();
        Pane root = new Pane();
        root.getChildren().add(map);
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("Castle Defense");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}