package com.example.castledefense;

import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.*;

public class GameMap extends Pane {
    public static final int TILE_SIZE = 40;
    private GameEngine engine;
    private Text castleHealthLabel;

    public GameMap(String mapName) {
        setPrefSize(800, 600);
        createMap();
        loadBackground(mapName);

    }

    public void loadBackground(String mapName) {
        String imagePath = switch (mapName) {
            case "Forest" -> "/images/forest.png";
            case "Desert" -> "/images/desert.png";
            default -> "/images/default.png";
        };
        Image background = new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
        ImageView bgView = new ImageView(background);
        bgView.setFitWidth(800);
        bgView.setFitHeight(600);
        getChildren().add(bgView);
    }

    private void setUpHud() {
        castleHealthLabel = new Text("Castle Health: " + engine.getCastle().getHealth());
        castleHealthLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        castleHealthLabel.setFill(Color.BLANCHEDALMOND);
        castleHealthLabel.setX(20);
        castleHealthLabel.setY(30);
        getChildren().add(castleHealthLabel);
    }

    public void updateVisuals() {
        getChildren().removeIf(node -> node instanceof EnemyView);
        for (Enemy enemy : engine.getEnemies()) {
            getChildren().add(new EnemyView(enemy));
        }

        getChildren().removeIf(node -> node instanceof TowerView);
        for (Tower tower : engine.getTowers()) {
            getChildren().add(new TowerView(tower));
        }

        castleHealthLabel.setText("Castle Health: " + engine.getCastle().getHealth());
    }

    private void createMap() {
        addCastleIcon();
        addTowerSpots(engine.getBuildZones());
        setUpHud();
    }

    public void showGameOver() {
        Text gameOver = new Text("Game Over!");
        gameOver.setFont(Font.font("Arial", FontWeight.BOLD, 48));
        gameOver.setFill(Color.RED);
        gameOver.setX(200);
        gameOver.setY(300);
        getChildren().add(gameOver);
    }

    public void showVictory() {
        Text victory = new Text("You Won!");
        victory.setFont(Font.font("Arial", FontWeight.BOLD, 48));
        victory.setFill(Color.DARKBLUE);
        victory.setX(200);
        victory.setY(300);
        getChildren().add(victory);
    }

    private void generateTiles() {
        for (int row = 0; row < 15; row++) {
            for (int col = 0; col < 20; col++) {
                Rectangle tile = new Rectangle(TILE_SIZE, TILE_SIZE);
                tile.setX(col * TILE_SIZE);
                tile.setY(row * TILE_SIZE);
                tile.setFill(Color.LIGHTGRAY);
                tile.setStroke(Color.BLACK);
                getChildren().add(tile);
            }

        }
    }

    private void drawPath() {
        for (int i = 0; i < engine.getEnemyPath().size() - 1; i ++) {
            Point2D p1 = engine.getEnemyPath().get(i);
            Point2D p2 = engine.getEnemyPath().get(i + 1);
            Line line = new Line(
                    p1.getX() * TILE_SIZE +  (double) TILE_SIZE / 2,
                    p1.getY() * TILE_SIZE + (double) TILE_SIZE / 2,
                    p2.getX() * TILE_SIZE + (double) TILE_SIZE / 2,
                    p2.getY() * TILE_SIZE + (double) TILE_SIZE / 2
            );
        }

    }

    private void addCastleIcon() {
        Point2D endPoint = engine.getCastle().getPosition();
        Image castleImage = new Image(Objects.requireNonNull(
                getClass().getResourceAsStream("/images/619097.png")));
        ImageView castleView = new ImageView(castleImage);
        castleView.setFitWidth(60);
        castleView.setFitHeight(60);
        castleView.setX(endPoint.getX() * TILE_SIZE);
        castleView.setY(endPoint.getY() * TILE_SIZE);
        castleView.setPreserveRatio(true);
        getChildren().add(castleView);

    }

    private void addTowerSpots(List<Point2D> buildZones) {
        for (Point2D zone : buildZones) {
            TowerSpot spot = new TowerSpot(
                    zone.getX() * TILE_SIZE, zone.getY() * TILE_SIZE, engine, this);
            getChildren().add(spot);
        }
    }



    public void setEngine(GameEngine engine) {
        this.engine = engine;
    }
}
