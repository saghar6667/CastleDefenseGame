package com.example.castledefense;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class EnemyView extends Group {
    private Circle body;
    private Rectangle healthBarBackground;
    private Rectangle healthBarForeGround;

    public EnemyView(Enemy enemy) {
        double radius = 10;

        body = new Circle(radius);
        body.setFill(getColorForType(enemy.getType()));
        body.setCenterX(enemy.getX());
        body.setCenterY(enemy.getY());

        healthBarBackground = new Rectangle(20, 3);
        healthBarBackground.setFill(Color.RED);
        healthBarBackground.setX(enemy.getX() - 10);
        healthBarBackground.setY(enemy.getY() - 15);

        double healthRatio = (double) enemy.getHealth() / enemy.getMaxHealth();
        healthBarForeGround = new Rectangle(20 * healthRatio, 3);
        healthBarForeGround.setFill(Color.LIMEGREEN);
        healthBarForeGround.setX(enemy.getX() - 10);
        healthBarForeGround.setY(enemy.getY() - 15);

        getChildren().addAll(body, healthBarBackground, healthBarForeGround);

    }

    private Color getColorForType(EnemyType type) {
        return switch (type) {
            case NORMAL -> Color.CRIMSON;
            case FAST -> Color.ORANGE;
            case TANK -> Color.DARKGREEN;
        };
    }
}
