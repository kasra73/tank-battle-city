package com.simplegame.ui;

import com.almasb.fxgl.app.FXGL;
import javafx.beans.property.IntegerProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class GraphicalRemainingTanksCounter extends GridPane {

    private IntegerProperty remainingTanks;
    private ArrayList<Node> tanks;

    public GraphicalRemainingTanksCounter() {
        this.remainingTanks = FXGL.getGameState().intProperty("remainingTanksCount");
        this.setWidth(100);
        this.setAlignment(Pos.CENTER);
        //Setting the padding
        this.setPadding(new Insets(5, 5, 5, 5));
        this.createPane();
        remainingTanks.addListener((observableValue, number, t1) -> {
            createPane();
        });
    }

    private void createPane() {
        int tanksCount = remainingTanks.getValue();
        tanks = new ArrayList<>();
        this.getChildren().clear();
        for (int i = 0; i < tanksCount; i++) {
            ImageView t = new ImageView(FXGL.getAssetLoader().loadImage("menu/tank.png"));
            tanks.add(t);
            this.add(t, i%3, i/3);
        }
    }
}
