package com.simplegame.ui;

import com.almasb.fxgl.app.FXGL;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class SideMenu extends Group {
    public SideMenu() {
        ObservableList<Node> children = this.getChildren();

        Rectangle r = new Rectangle(70, 805, Color.GRAY);
        children.add(r);

        GraphicalRemainingTanksCounter grtc = new GraphicalRemainingTanksCounter();
        grtc.setTranslateY(50);
        children.add(grtc);

        ShowLifeMenu showLifeMenu = new ShowLifeMenu();
        showLifeMenu.setTranslateY(240);
        children.add(showLifeMenu);

        Text textPixels = new Text();
        textPixels.setTranslateX(30); // x = 50
        textPixels.setTranslateY(600); // y = 100
        textPixels.textProperty()
                .bind(FXGL.getGameState().intProperty("pixelsMoved").asString());
        children.add(textPixels);
    }
}
