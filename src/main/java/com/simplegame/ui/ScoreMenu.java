package com.simplegame.ui;

import com.almasb.fxgl.app.FXGL;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ScoreMenu extends Group {
    public ScoreMenu() {
        ObservableList<Node> children = this.getChildren();

        Text scoreText = new Text();
        scoreText.setText("Score: ");
        children.add(scoreText);

        Text textPixels = new Text();
        textPixels.setX(40);
        textPixels.textProperty()
                .bind(FXGL.getGameState().intProperty("score").asString());
        children.add(textPixels);
    }
}
