package com.example.castledefense;

import java.util.List;

public class FastShooterTower extends Tower {
    public FastShooterTower(double x, double y) {
        super(x, y);
        setHealth(50);
        setCoolDown(0.5);
        setRange(100);
        setDamage(10);
    }

    @Override
    public void update(List<Enemy> enemies) {
        setLastAttackTime(getLastAttackTime() + 1);
        if (getLastAttackTime() >= getCoolDown()) {
            for (Enemy enemy : enemies) {
                if (isInRange(enemy)) {
                    shoot(enemy);
                    setLastAttackTime(0);
                    break;
                }
            }
        }
    }

    private boolean isInRange(Enemy enemy) {
        double dx = enemy.getX() - getX();
        double dy = enemy.getY() - getY();
        return Math.sqrt(dx * dx + dy * dy) <= getRange();
    }
}
