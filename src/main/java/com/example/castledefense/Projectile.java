package com.example.castledefense;

import javafx.animation.TranslateTransition;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class Projectile extends Circle {
    private Enemy target;
    private int damage;
    private GameMap map;

    public Projectile(double startX, double startY, Enemy target) {
        super(startX, startY, 5, Color.ORANGE);
        this.target = target;
    }

    public void launch(GameMap map, int damage) {
        this.map = map;
        this.damage = damage;

        double dx = target.getX() - getCenterX();
        double dy = target.getY() - getCenterY();
        double distance = Math.sqrt(dx * dx + dy * dy);
        double durationMs = distance * 5;

        TranslateTransition transition = new TranslateTransition(Duration.millis(durationMs), this);
        transition.setByX(dx);
        transition.setByY(dy);
        transition.setOnFinished(e -> {
            target.takeDamage(damage);
            map.getChildren().remove(this);
        });
        transition.play();
    }
}
