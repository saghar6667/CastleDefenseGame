package com.example.castledefense;

import javafx.application.Platform;
import javafx.geometry.Point2D;
import java.util.*;
import java.util.concurrent.*;

public class GameEngine {
    private GameMap map;
    private List<Enemy> enemies = new ArrayList<>();
    private List<Tower> towers = new ArrayList<>();
    private ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
    private int totalSpawned = 0;
    private int enemiesReachedCastle = 0;
    private boolean gameEnded = false;

    public GameEngine(GameMap map) {
        this.map = map;
        map.setEngine(this);
    }

    public void startGame() {
        spawnEnemies();
        startGameLoop();
    }

    private void spawnEnemies() {
        executor.scheduleAtFixedRate(() -> Platform.runLater(() -> {
            Enemy enemy = new Enemy(2 * 64, 2 * 64, 100, 1.5, EnemyType.GROUND, map.getPath());
            enemies.add(enemy);
            map.getChildren().add(enemy.getView());
            totalSpawned++;
        }), 0, 2000, TimeUnit.MILLISECONDS);
    }

    private void startGameLoop() {
        executor.scheduleAtFixedRate(() -> {
            long now = System.currentTimeMillis();

            for (Enemy enemy : enemies) {
                enemy.update();
                if (enemy.reachedCastle()) {
                    enemiesReachedCastle++;
                    enemy.takeDamage(9999);
                }
            }

            for (Tower t : towers) t.update(enemies, map, now);

            Platform.runLater(() -> {
                enemies.removeIf(e -> {
                    if (e.isDead()) {
                        map.getChildren().remove(e.getView());
                        return true;
                    }
                    return false;
                });

                double breachRatio = totalSpawned == 0 ? 0 : (double) enemiesReachedCastle / totalSpawned;
                if (!gameEnded) {
                    if (breachRatio >= 0.1) {
                        gameEnded = true;
                        executor.shutdown();
                        System.out.println("Game Over");
                    } else if (enemies.isEmpty() && breachRatio < 0.1 && totalSpawned > 0) {
                        gameEnded = true;
                        executor.shutdown();
                        System.out.println("Victory");
                    }
                }
            });
        }, 0, 50, TimeUnit.MILLISECONDS);
    }

    public void addTower(Tower tower) {
        towers.add(tower);
        map.getChildren().add(tower.getView());
    }
}