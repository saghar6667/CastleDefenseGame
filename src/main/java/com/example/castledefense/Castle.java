package com.example.castledefense;

import javafx.geometry.Point2D;

public class Castle {
    private Point2D position;
    private int health;

    public Castle(Point2D position, int health) {
        this.position = position;
        this.health = health;
    }

    public Point2D getPosition() {
        return position;
    }

    public int getHealth() {
        return health;
    }

    public void takeDamage(int amount) {
        health -= amount;
        if (health < 0) {
            health = 0;
        }
    }

    public boolean isDestroyed() {
        return health <= 0;
    }
}
