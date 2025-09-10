package com.example.castledefense;

import java.util.List;

public class FastShooterTower extends Tower {
    public FastShooterTower(double x, double y) {
        super(x, y);
        setHealth(60);
        setCoolDown(0.5);
        setRange(100);
        setDamage(10);
    }

    @Override
    public void update(List<Enemy> enemies, GameMap map, long currentTime) {
        if (currentTime - getLastAttackTime() < getCoolDown()) {
            return;
        }
        for (Enemy )
    }

    private boolean isInRange(Enemy enemy) {
        double dx = enemy.getX() - getX();
        double dy = enemy.getY() - getY();
        return Math.sqrt(dx * dx + dy * dy) <= getRange();
    }
}
