package com.example.castledefense;

import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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

        Button newGameBtn = new Button("New Game");
        ComboBox<String> mapSelector = new ComboBox<>();
        mapSelector.getItems().addAll("Default", "Forest", "Desert");
        mapSelector.setValue("De")
    }
}
