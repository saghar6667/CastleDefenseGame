package com.example.castledefense;

import java.util.List;

public abstract class Tower {
    private double x, y;
    private double range;
    private int health;
    private int damage;
    private double lastAttackTime = 0;
    private double coolDown;

    public Tower(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public abstract void update(List<Enemy> enemies);

    public void shoot(Enemy target) {
        target.takeDamage(damage);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void takeDamage(int amount) {
        health -= amount;
    }

    public boolean isDestroyed() {
        return health <= 0;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getHealth() {
        return health;
    }

    public void setCoolDown(double coolDown) {
        this.coolDown = coolDown;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setLastAttackTime(double lastAttackTime) {
        this.lastAttackTime = lastAttackTime;
    }

    public double getLastAttackTime() {
        return lastAttackTime;
    }

    public double getCoolDown() {
        return coolDown;
    }

    public double getRange() {
        return range;
    }
}
