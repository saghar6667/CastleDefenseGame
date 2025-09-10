package com.example.castledefense;

import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
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
    private List<Circle> placementSpots = new ArrayList<>();

    public GameMap(String mapName, GameEngine engine) {
        setPrefSize(800, 600);
        this.engine = engine;
        createMap();
        loadBackground(mapName);

    }

    public void loadBackground(String mapName) {
        String imagePath = switch (mapName) {
            case "Forest" -> "/images/forest.jpg";
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
        Label castleHealthLabel = new Label("Castle HP: 100" + engine.getCastle().getHealth());
        castleHealthLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        castleHealthLabel.setTextFill(Color.BLANCHEDALMOND);
        castleHealthLabel.setLayoutX(20);
        castleHealthLabel.setLayoutY(20);
        castleHealthLabel.setId("hpLabel");

        Label waveLabel = new Label("Wave: 1");
        waveLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        waveLabel.setTextFill(Color.WHITE);
        waveLabel.setLayoutX(20);
        waveLabel.setLayoutY(50);
        waveLabel.setId("waveLabel");

        Button supplyBtn = new Button("Supply Menu");
        supplyBtn.setLayoutX(650);
        supplyBtn.setLayoutY(20);
        supplyBtn.setOnAction(e -> {
            SupplyMenu menu = new SupplyMenu(engine);
            getChildren().add(menu);
        });

        Label breachLabel = new Label("Breached: 0");
        breachLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        breachLabel.setTextFill(Color.ORANGE);
        breachLabel.setLayoutX(20);
        breachLabel.setLayoutY(80);
        breachLabel.setId("breachLabel");

        getChildren().addAll(waveLabel, supplyBtn, breachLabel, castleHealthLabel);
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

        ((Label) lookup("#hpLabel")).setText("Castle HP: " + engine.getCastle().getHealth());
        ((Label) lookup("#waveLabel")).setText("Wave: " + engine.getWave());
        ((Label) lookup("#breachedLabel")).setText("Breached: " + engine.getEnemiesReached());
    }

    private void createMap() {
        addCastleIcon();
        addTowerSpots(engine.getBuildZones());
        setUpHud();
    }

    public void showGameOver() {
        Text gameOver = new Text("GAME OVER");
        gameOver.setFont(Font.font("Verdana", FontWeight.BOLD, 48));
        gameOver.setFill(Color.RED);
        gameOver.setX(200);
        gameOver.setY(300);
        getChildren().add(gameOver);
    }

    public void showVictory() {
        Text victory = new Text("VICTORY");
        victory.setFont(Font.font("Verdana", FontWeight.BOLD, 48));
        victory.setFill(Color.DARKBLUE);
        victory.setX(200);
        victory.setY(300);
        getChildren().add(victory);
    }

    public void showPlacementSpots(String type) {
        placementSpots.clear();
        for (int i = 100; i < 700; i += 100) {
            Circle circle = new Circle(i, 500, 15, Color.LIGHTBLUE);
            circle.setOpacity(0.6);
            placementSpots.add(circle);
            getChildren().add(circle);
        }
    }

    public void clearPlacementSpots() {
        for (Circle circle : placementSpots) {
            getChildren().remove(circle);
        }
        placementSpots.clear();
    }

    public boolean isValidPlacement(double x, double y, String type) {
        for (Circle circle : placementSpots) {
            if (Math.hypot(circle.getCenterX() - x, circle.getCenterY() - y) < 20) {
                return true;
            }
        }
        return false;
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

}
