package com.example.castledefense;

public enum EnemyType {
    NORMAL(100),
    FAST(60),
    TANK(200),
    AIR(100);

    private final int maxHealth;

    EnemyType(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getMaxHealth() {
        return maxHealth;
    }
}
