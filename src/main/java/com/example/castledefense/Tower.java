package com.example.castledefense;

import javafx.scene.image.ImageView;
import java.util.List;

public abstract class Tower {
    protected double x, y;
    protected long lastAttackTime;
    protected long cooldown;
    protected int damage;
    protected int health;
    protected double range;
    protected ImageView view;

    public Tower(double x, double y) {
        this.x = x;
        this.y = y;
        this.lastAttackTime = 0;
    }

    public abstract void update(List<Enemy> enemies, GameMap map, long currentTime);

    protected boolean isInRange(Enemy e) {
        double dx = e.getX() - x;
        double dy = e.getY() - y;
        return Math.hypot(dx, dy) <= range;
    }

    public ImageView getView() { return view; }
}