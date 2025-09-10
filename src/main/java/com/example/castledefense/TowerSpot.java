package com.example.castledefense;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import javafx.geometry.Point2D;

public class TowerSpot extends StackPane {
    private boolean occupied = false;
    private final Rectangle background;
    private final GameMap map;
    private final GameEngine engine;

    public TowerSpot(double x, double y, GameEngine gameEngine, GameMap map) {
        this.engine = gameEngine;
        this.map = map;

        setLayoutX(x);
        setLayoutY(y);
        setPrefSize(GameMap.TILE_SIZE, GameMap.TILE_SIZE);

        background = new Rectangle(40, 40);
        background.setFill(Color.LIGHTBLUE);
        background.setStroke(Color.DARKBLUE);
        getChildren().add(background);

        setOnMouseClicked(e -> {
            if (!occupied) {
                placeTower(x + (double) GameMap.TILE_SIZE / 2, y + (double) GameMap.TILE_SIZE / 2);
                occupied = true;
                background.setFill(Color.DARKGRAY);
            }
        });
    }

    private void placeTower(double centerX, double centerY) {
        Tower tower;
        if (Math.random() < 0.5) {
            tower = new FastShooterTower(centerX, centerY);
        } else {
            tower = new HeavyShooterTower(centerX, centerY);
        }
        engine.addTower(tower);
        map.getChildren().add(new TowerView(tower));
    }

    public boolean isOccupied() {
        return occupied;
    }
}
