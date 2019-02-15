package com.simplegame.ui;

import com.almasb.fxgl.app.FXGL;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ShowLifeMenu extends Group {
    public ShowLifeMenu() {
        ObservableList<Node> children = this.getChildren();
        ImageView flag = new ImageView(FXGL.getAssetLoader().loadImage("menu/flag.png"));
        children.add(flag);

        Text textPixels = new Text();
        textPixels.setTranslateX(30); // x = 50
        textPixels.setTranslateY(70); // y = 100
        textPixels.setFont(new Font(45));
        textPixels.textProperty()
                .bind(FXGL.getGameState().intProperty("remainingLives").asString());
        children.add(textPixels);
    }
}
