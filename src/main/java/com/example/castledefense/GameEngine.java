package com.example.castledefense;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GameEngine extends Pane {
    private List<Point2D> enemyPath;
    private List<Point2D> buildZones;
    private final static List<Point2D> placeTowers = new ArrayList<>();
    private final static List<Enemy> enemies = new ArrayList<>();
    private final static List<Tower> towers = new ArrayList<>();
    private final static Castle castle = new Castle(new Point2D(600, 400), 100);
    private int totalSpawned = 0, enemyHealthMod;
    private int enemiesReachedEnd = 0;
    private boolean waveIsFinished = false;
    private Timeline gameLoop;
    private GameMap map;
    private double towerCoolDownMod, spawnInterval;
    private ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    private boolean isPlacing = false;
    private String placementType = " ";

    public GameEngine(String mapName, String difficulty) {
        this.enemyPath = loadPath(mapName);
        initializeBuildZones();

        switch (difficulty) {
            case "Easy" -> {
                spawnInterval = 3.0;
                enemyHealthMod = 1;
                towerCoolDownMod = 0.8;
            }
            case "Normal" -> {
                spawnInterval = 2.0;
                enemyHealthMod = 2;
                towerCoolDownMod = 1.0;
            }
            case "Hard" -> {
                spawnInterval = 1.0;
                enemyHealthMod = 3;
                towerCoolDownMod = 1.2;}
        }
    }

    public void setMap(GameMap map) {
        this.map = map;
    }

    private List<Point2D> loadPath(String mapName) {
        return switch (mapName) {
            case "Forest" -> List.of(
                    new Point2D(0, 0), new Point2D(3, 0),
                    new Point2D(3, 4), new Point2D(7, 4));
            case "Desert" -> List.of(
                    new Point2D(0, 5), new Point2D(5, 5),
                    new Point2D(5, 2), new Point2D(10, 2));
            default -> List.of(
                    new Point2D(0, 0), new Point2D(5, 0),
                    new Point2D(4, 5), new Point2D(10, 5));
        };
    }

    public void enterPlacementMode(String type) {
        isPlacing = true;
        placementType = type;
        map.showPlacementSpots(type);
    }

    public void placementClick(double x, double y) {
        if (!isPlacing) {
            return;
        }

        if (map.isValidPlacement(x, y, placementType)) {
            switch (placementType) {
                case "Fast Tower" -> {
                    Tower tower = new FastShooterTower(x, y);
                    towers.add(tower);
                }
                case "Heavy Tower" -> {
                    Tower tower = new HeavyShooterTower(x, y);
                }
                case "Bomb" -> dropBombAt(x, y);
            }
            isPlacing = false;
            placementType = "";
            map.clearPlacementSpots();
        }
    }

    private void initializePath() {
        enemyPath = List.of(
                new Point2D(0, 5),
                new Point2D(5,5),
                new Point2D(5,2),
                new Point2D(10,2),
                new Point2D(10,6),
                new Point2D(15,6)
        );
    }

    private void initializeBuildZones() {
        buildZones = List.of(
                new Point2D(3,3),
                new Point2D(7,4),
                new Point2D(12,3),
                new Point2D(14,5)
        );
    }

    public void startGame() {
        spawnEnemies();
        startGameLoop();
    }

    public List<Point2D> getBuildZones() {
        return buildZones;
    }

    public List<Point2D> getEnemyPath() {
        return enemyPath;
    }

    public Castle getCastle() {
        return castle;
    }

    public void addTower(Tower tower) {
        towers.add(tower);
    }

    public List<Enemy> getEnemies() {
        return new ArrayList<>(enemies);
    }

    public List<Tower> getTowers() {
        return new ArrayList<>(towers);
    }

    public void dropBombAt(double x, double y) {
        for (Enemy enemy : enemies) {
            if (Math.hypot(enemy.getX() - x, enemy.getY() - y) < 50) {
                enemy.takeDamage(70);
            }
        }
    }

    private void spawnEnemies() {
        executor.scheduleAtFixedRate(() -> {
            Platform.runLater(() -> {
                Enemy enemy = new Enemy(0, 300, 100, 1.5);
                totalSpawned++;
                enemies.add(enemy);
            });
        }, 0, (long) (spawnInterval * 1000), TimeUnit.MILLISECONDS);
    }

    public void startGameLoop() {
        executor.scheduleAtFixedRate(() -> {
            long currentTime = System.currentTimeMillis();

            for (Enemy enemy : enemies) {
                enemy.update();
            }

            for (Tower tower : towers) {
                tower.update(enemies, map, currentTime);
            }

            Platform.runLater(() -> {
                enemies.removeIf(Enemy::isDead);
            });
        }, 0, 50, TimeUnit.MILLISECONDS);
    }

    private void drawMap() {
        Canvas canvas = new Canvas(800, 600);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.DARKGREEN);
        gc.fillRect(0, 0, getWidth(), getHeight());

        gc.setStroke(Color.DARKRED);
        gc.setLineWidth(4);
        for (int i = 0; i < enemyPath.size() - 1; i++) {
            Point2D point1 = enemyPath.get(i);
            Point2D point2 = enemyPath.get(i + 1);
            gc.strokeLine(point1.getX(), point1.getY(), point2.getX(), point2.getY());
        }

        gc.setFill(Color.DARKBLUE);
        for (Point2D zone : buildZones) {
            gc.fillOval(zone.getX() - 10, zone.getY() - 10, 20, 20);
        }

        gc.setFill(Color.GOLD);
        for (Point2D tower : placeTowers) {
            gc.fillRect(tower.getX() - 10, tower.getY() - 10, 20, 20);
        }

        for (Enemy enemy : enemies) {
            enemy.draw(gc);
        }
    }

    public EnemyType chooseRandomType() {
        EnemyType[] types = EnemyType.values();
        return types[new Random().nextInt(types.length)];
    }

}
