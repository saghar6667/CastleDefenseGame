package com.example.castledefense;


import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.List;

public class Enemy {
    private double x, y;
    private int pathIndex = 0;
    private final List<Point2D> path;
    private double speed;
    private final EnemyType type;
    private int health;
    private int maxHealth;
    private static final int TILE_SIZE = 40;
    private boolean isDead;
    private int damage;

    public Enemy(List<Point2D> path, GameEngine engine) {
        this.path = path;
        Point2D start = path.getFirst();
        this.x = (start.getX() * TILE_SIZE) + ((double) TILE_SIZE / 2);
        this.y = (start.getY() * TILE_SIZE) + ((double) TILE_SIZE / 2);;
        this.type = engine.chooseRandomType();
        this.maxHealth = type.getMaxHealth();
        this.health = maxHealth;
        this.isDead = false;

        switch (type) {
            case NORMAL -> {
                this.speed = 1.5;
                this.damage = 10;
            }
            case FAST -> {
                this.speed = 2.5;
                this.damage = 8;
            }
            case TANK -> {
                this.speed = 0.8;
                this.damage = 20;
            }
        }
    }

    public void update() {
        if (pathIndex >= path.size()) {
            return;
        }

        Point2D target = path.get(pathIndex);
        double targetX = (target.getX() * TILE_SIZE) + ((double) TILE_SIZE / 2);
        double targetY = (target.getY() * TILE_SIZE) + ((double) TILE_SIZE / 2);

        double dx = targetX - x;
        double dy = targetY - y;
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance < speed) {
            x = targetX;
            y = targetY;
            pathIndex++;
        } else {
            x += (dx / distance) * speed;
            y += (dy / distance) * speed;
        }
    }

    public void draw(GraphicsContext gc) {
        switch (type) {
            case NORMAL -> gc.setFill(Color.CRIMSON);
            case FAST -> gc.setFill(Color.ORANGE);
            case TANK -> gc.setFill(Color.LIGHTGREEN);
        }
        gc.fillOval(x - 10, y -10, 20, 20);
    }

    public boolean hasReachedEnd() {
        return pathIndex >= path.size() - 1;
    }

    public EnemyType getType() {
        return type;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getDamage() {
        return damage;
    }

    public void takeDamage(int amount) {
        health -= amount;

        if (health <= 0) {
            health = 0;
            isDead = true;
        }
    }

    public boolean isDead() {
        return isDead;
    }

}
