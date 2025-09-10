package com.example.castledefense;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.awt.*;
import java.util.Objects;

public class MainMenu extends VBox {
    private Stage stage;
    private String selectedMap = "Default";
    private String selectedDifficulty = "Normal";

    public MainMenu(Stage stage) {
        this.stage = stage;
        setSpacing(20);
        setAlignment(Pos.CENTER);

        BackgroundImage bgImage = new BackgroundImage(
                new Image(Objects.requireNonNull(getClass().
                        getResource("/images/castle light.jpg")).toExternalForm()),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(800, 600, false, false, false, false)
        );
        setBackground(new Background(bgImage));

        Text title = new Text("Castle Defense");
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 36));
        title.setFill(Color.GOLD);
        title.setStroke(Color.BLACK);
        title.setStyle("-fx-effect: dropshadow(qaussian, black, 5, 0.5, 0, 2);");
        
        ComboBox<String> mapSelector = new ComboBox<>();
        mapSelector.getItems().addAll("Default", "Forest", "Desert");
        mapSelector.setValue("Default");

        ComboBox<String> difficultySelector = new ComboBox<>();
        difficultySelector.getItems().addAll("Easy", "Normal", "Hard");
        difficultySelector.setValue("Normal");

        Button newGameBtn = getGameBtn(stage, mapSelector, difficultySelector);
        getChildren().addAll(title, mapSelector, difficultySelector, newGameBtn);
    }

    private Button getGameBtn(Stage stage, ComboBox<String> mapSelector, ComboBox<String> difficultySelector) {
        Button newGameBtn = new Button("New Game");
        newGameBtn.setOnAction(e -> {
            selectedMap = mapSelector.getValue();
            selectedDifficulty = difficultySelector.getValue();

            GameEngine engine = new GameEngine(selectedMap, selectedDifficulty);
            GameMap map = new GameMap(selectedMap, engine);
            engine.setMap(map);
            engine.startGame();

            Scene gameScene = new Scene(map, 800, 600);
            stage.setScene(gameScene);
        });
        return newGameBtn;
    }
}
