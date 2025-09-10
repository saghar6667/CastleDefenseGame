package com.example.castledefense;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.List;

public class AntiAircraftTower extends Tower {

    public AntiAircraftTower(double x, double y) {
        super(x, y);
        this.cooldown = 1000;
        this.damage = 30;
        this.health = 100;
        this.range = 200;
        this.view = new ImageView(new Image("/images/towers/tower_aa.png"));
        view.setFitWidth(48);
        view.setFitHeight(48);
        view.setX(x);
        view.setY(y);
    }

    @Override
    public void update(List<Enemy> enemies, GameMap map, long currentTime) {
        if (currentTime - lastAttackTime < cooldown || health <= 0) return;

        for (Enemy e : enemies) {
            if (e.getType() == EnemyType.AIR && isInRange(e)) {
                Platform.runLater(() -> {
                    Projectile p = new Projectile(x, y, e);
                    map.getChildren().add(p);
                    p.launch(map, damage);
                });
                lastAttackTime = currentTime;
                break;
            }
        }
    }
}