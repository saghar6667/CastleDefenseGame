package com.example.castledefense;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameEngine extends Pane {
    private List<Point2D> enemyPath;
    private List<Point2D> buildZones;
    private final List<Point2D> placeTowers = new ArrayList<>();
    private final List<Enemy> enemies = new ArrayList<>();
    private final List<Tower> towers = new ArrayList<>();
    private final Castle castle = new Castle(new Point2D(600, 400), 100);
    private int totalSpawned = 0;
    private int enemiesReachedEnd = 0;
    private boolean waveIsFinished = false;
    private Timeline gameLoop;
    private GameMap map;

    public GameEngine(GameMap map) {
        this.map = map;
        initializePath();
        initializeBuildZones();
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

    private void spawnEnemies() {
        Timeline spawn = new Timeline(new KeyFrame(Duration.seconds(2), e -> {
            EnemyType type = chooseRandomType();
            Enemy enemy = new Enemy(enemyPath, type);
            enemies.add(enemy);
            totalSpawned++;
        }));
        spawn.setCycleCount(10);
        spawn.setOnFinished(e -> waveIsFinished = true);
        spawn.play();
    }

    public void startGameLoop() {
        gameLoop = new Timeline(new KeyFrame(Duration.millis(30), e -> {
            updateGame();
        }));
        gameLoop.setCycleCount(Animation.INDEFINITE);
        gameLoop.play();
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

    private void redrawEnemies() {
        getChildren().removeIf(node -> node instanceof EnemyView);
        for (Enemy enemy : enemies) {
            EnemyView view = new EnemyView(enemy);
            getChildren().add(view);
        }
    }

    private void updateGame() {
        for (Enemy enemy : enemies) {
            enemy.update();
            if (enemy.hasReachedEnd()) {
                castle.takeDamage(enemy.getDamage());
            }
        }

        for(Tower tower: towers) {
            tower.update(enemies);
        }

        enemies.removeIf(Enemy::isDead);
        map.updateVisuals();

        double breachRatio = (double) enemiesReachedEnd / totalSpawned;
        if (breachRatio >= 0.2) {
            map.showGameOver();
            gameLoop.stop();
        } else if (waveIsFinished && enemies.isEmpty()) {
            map.showVictory();
            gameLoop.stop();
        }
    }

    private EnemyType chooseRandomType() {
        EnemyType[] types = EnemyType.values();
        return types[new Random().nextInt(types.length)];
    }

}
