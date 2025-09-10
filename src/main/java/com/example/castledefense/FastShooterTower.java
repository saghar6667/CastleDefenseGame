package com.example.castledefense;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.List;

public class FastShooterTower extends Tower {

    public FastShooterTower(double x, double y) {
        super(x, y);
        this.cooldown = 500;
        this.damage = 10;
        this.health = 60;
        this.range = 120;
        this.view = new ImageView(new Image("/images/towers/tower_fast.png"));
        view.setFitWidth(48);
        view.setFitHeight(48);
        view.setX(x);
        view.setY(y);
    }

    @Override
    public void update(List<Enemy> enemies, GameMap map, long currentTime) {
        if (currentTime - lastAttackTime < cooldown || health <= 0) return;

        for (Enemy enemy : enemies) {
            if (enemy.getType() != EnemyType.AIR && isInRange(enemy)) {
                Platform.runLater(() -> {
                    Projectile p = new Projectile(x, y, enemy);
                    map.getChildren().add(p);
                    p.launch(map, damage);
                });
                lastAttackTime = currentTime;
                break;
            }
        }
    }
}