package com.example.castledefense;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class SupplyMenu extends VBox {
    public SupplyMenu(GameEngine engine) {
        setSpacing(10);
        setStyle("-fx-background-color: rgba(0,0,0,0.7); -fx-padding: 10;");

        Button bmbBtn = new Button("Drop Bomb");
        Button fastTowerBtn = new Button("Fast Shooter Tower");
        Button heavyTowerBtn = new Button("Heavy Shooter Tower");

        bmbBtn.setOnAction(e -> engine.enterPlacementMode("Bomb"));
        fastTowerBtn.setOnAction(e -> engine.enterPlacementMode("Fast Tower"));
        heavyTowerBtn.setOnAction(e -> engine.enterPlacementMode("Heavy Tower"));

        getChildren().addAll(bmbBtn, fastTowerBtn, heavyTowerBtn);
    }
}
