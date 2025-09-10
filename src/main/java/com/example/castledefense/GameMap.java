package com.example.castledefense;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.geometry.Point2D;
import java.util.*;

public class GameMap extends Pane {
    private final int TILE_SIZE = 64;
    private final int ROWS = 10;
    private final int COLS = 13;
    private GameEngine engine;

    private String[][] tileMap = {
            {"grass","grass","path","path","path","grass","grass","grass","grass","grass","grass","grass","grass"},
            {"grass","grass","grass","grass","path","grass","grass","grass","grass","grass","grass","grass","grass"},
            {"grass","grass","grass","grass","path","path","path","path","path","path","path","path","castle"},
            {"grass","grass","grass","grass","grass","grass","grass","grass","grass","grass","grass","grass","grass"},
            // Add more rows if needed
    };

    public GameMap() {
        loadTiles();
        placeCastle();
    }

    private void loadTiles() {
        for (int row = 0; row < tileMap.length; row++) {
            for (int col = 0; col < tileMap[row].length; col++) {
                String type = tileMap[row][col];
                Image img = new Image(getClass().getResourceAsStream("/images/tiles/" + type + ".png"));
                ImageView view = new ImageView(img);
                view.setFitWidth(TILE_SIZE);
                view.setFitHeight(TILE_SIZE);
                view.setX(col * TILE_SIZE);
                view.setY(row * TILE_SIZE);
                getChildren().add(view);
            }
        }
    }

    private void placeCastle() {
        ImageView castle = new ImageView(new Image("/images/tiles/castle.png"));
        castle.setFitWidth(64);
        castle.setFitHeight(64);
        castle.setX(12 * TILE_SIZE);
        castle.setY(2 * TILE_SIZE);
        getChildren().add(castle);
    }

    public List<Point2D> getPath() {
        return List.of(
                new Point2D(2 * TILE_SIZE, 2 * TILE_SIZE),
                new Point2D(4 * TILE_SIZE, 2 * TILE_SIZE),
                new Point2D(4 * TILE_SIZE, 1 * TILE_SIZE),
                new Point2D(4 * TILE_SIZE, 0 * TILE_SIZE),
                new Point2D(6 * TILE_SIZE, 0 * TILE_SIZE),
                new Point2D(10 * TILE_SIZE, 0 * TILE_SIZE),
                new Point2D(12 * TILE_SIZE, 2 * TILE_SIZE)
        );
    }

    public void setEngine(GameEngine engine) {
        this.engine = engine;
    }
}