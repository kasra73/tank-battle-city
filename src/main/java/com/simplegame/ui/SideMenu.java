package com.simplegame.ui;

import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

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

        ScoreMenu scoreMenu = new ScoreMenu();
        scoreMenu.setTranslateX(0);
        scoreMenu.setTranslateY(600);
        children.add(scoreMenu);
    }
}
