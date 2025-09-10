package com.example.castledefense;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PlaneEnemy extends Enemy {

    public PlaneEnemy(double x, double y) {
        super(x, y, 50, 3.0, EnemyType.AIR, null);
        this.view = new ImageView(new Image("/images/enemies/enemy_plane.png"));
        view.setFitWidth(48);
        view.setFitHeight(48);
        view.setX(x);
        view.setY(y);
    }

    @Override
    public void update() {
        x += speed;
        y += Math.sin(x / 50.0) * 2;
        view.setX(x);
        view.setY(y);
    }
}