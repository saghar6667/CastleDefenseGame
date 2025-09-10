package com.example.castledefense;

import java.util.List;

public class HeavyShooterTower extends Tower {
    public HeavyShooterTower(double x, double y) {
        super(x, y);
        setHealth(150);
        setCoolDown(2.0);
        setRange(150);
        setDamage(30);

    }

    @Override
    public void update(List<Enemy> enemies) {
        setLastAttackTime(getLastAttackTime() + 0.016);
        if (getLastAttackTime() <= getCoolDown()) {
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
