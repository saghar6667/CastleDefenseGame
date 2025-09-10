package com.example.castledefense;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class TowerView extends ImageView {
    public TowerView(Tower tower) {
        setFitWidth(40);
        setFitHeight(40);

        if (tower instanceof FastShooterTower) {
            setImage(new Image(
                    Objects.requireNonNull(getClass().getResourceAsStream("/images/fastTower.png"))));
        } else if (tower instanceof HeavyShooterTower) {
            setImage(new Image(
                    Objects.requireNonNull(getClass().getResourceAsStream("/images/heavyTower.png"))));
        }

        setX(tower.getX() - 20);
        setY(tower.getY() - 20);
    }
}
