package com.example.castledefense;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.List;

public class Enemy {
    protected double x, y;
    protected int health;
    protected double speed;
    protected EnemyType type;
    protected List<Point2D> path;
    protected int currentTarget = 0;
    protected ImageView view;

    public Enemy(double x, double y, int health, double speed, EnemyType type, List<Point2D> path) {
        this.x = x;
        this.y = y;
        this.health = health;
        this.speed = speed;
        this.type = type;
        this.path = path;
        this.view = new ImageView(new Image("/images/enemies/enemy_ground.png"));
        view.setFitWidth(48);
        view.setFitHeight(48);
        view.setX(x);
        view.setY(y);
    }

    public void update() {
        if (currentTarget >= path.size()) return;
        Point2D target = path.get(currentTarget);
        double dx = target.getX() - x;
        double dy = target.getY() - y;
        double dist = Math.hypot(dx, dy);

        if (dist < speed) {
            x = target.getX();
            y = target.getY();
            currentTarget++;
        } else {
            x += speed * dx / dist;
            y += speed * dy / dist;
        }

        view.setX(x);
        view.setY(y);
    }

    public boolean reachedCastle() {
        return currentTarget >= path.size();
    }

    public void takeDamage(int dmg) {
        health -= dmg;
    }

    public boolean isDead() { return health <= 0; }
    public ImageView getView() { return view; }
    public EnemyType getType() { return type; }
    public double getX() { return x; }
    public double getY() { return y; }
}