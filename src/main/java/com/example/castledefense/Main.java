package com.example.castledefense;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        MainMenu menu = new MainMenu(stage);
        Scene menuScene = new Scene(menu, 800, 600);

        stage.setTitle("Castle Defense");
        stage.setScene(menuScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}