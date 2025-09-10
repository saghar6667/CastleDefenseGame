package com.example.castledefense;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.awt.*;

public class MainMenu extends VBox {
    private Stage stage;
    private String selectedMap = "Default";
    private String selectedDifficulty = "Normal";

    public MainMenu(Stage stage) {
        this.stage = stage;
        setSpacing(20);
        setAlignment(Pos.CENTER);

        Text title = new Text("Castle Defense");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 36));
        
        ComboBox<String> mapSelector = new ComboBox<>();
        mapSelector.getItems().addAll("Default", "Forest", "Desert");
        mapSelector.setValue("Default");

        ComboBox<String> difficultySelector = new ComboBox<>();
        mapSelector.getItems().addAll("Easy", "Normal", "Hard");
        difficultySelector.setValue("Normal");

        Button newGameBtn = getGameBtn(stage, mapSelector, difficultySelector);

        getChildren().addAll(title, mapSelector, difficultySelector, newGameBtn);
    }

    private Button getGameBtn(Stage stage, ComboBox<String> mapSelector, ComboBox<String> difficultySelector) {
        Button newGameBtn = new Button("New Game");
        newGameBtn.setOnAction(e -> {
            selectedMap = mapSelector.getValue();
            selectedMap = difficultySelector.getValue();

            GameMap map = new GameMap(selectedMap);
            GameEngine engine = new GameEngine(map, selectedMap, selectedDifficulty);
            map.setEngine(engine);
            engine.startGame();

            Scene gameScene = new Scene(map, 800, 600);
            stage.setScene(gameScene);
        });
        return newGameBtn;
    }
}
