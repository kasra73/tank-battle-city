package com.simplegame.TankMaker;

import com.almasb.fxgl.ai.AIControl;
import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.Texture;
import com.simplegame.Component.TankControlComponent;
import com.simplegame.Controller.GameController;
import com.simplegame.Controller.TankController;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import java.util.LinkedHashMap;

public class EnemyTankMaker extends TankMakerTemplate {
    private Color color;
    LinkedHashMap<String, Object> enemy;

    public EnemyTankMaker(TankController controller, Color color) {
        super(controller);
        this.color = color;
    }

    @Override
    protected Component[] getTankComponents() {
        Component[] components = new Component[2];
        components[0] = new TankControlComponent(controller);
        controller.setTankControlComponent((TankControlComponent)components[0]);
        components[1] = new AIControl("random.tree");
        return components;
    }

    @Override
    protected Node makeNode() {
        color = Color.valueOf((String)enemy.get("color"));
        Texture texture = FXGL.getAssetLoader()
                .loadTexture("tank_white.png");
        texture = texture.multiplyColor(color);
        if((Boolean) enemy.get("have-bonus")) {
            Texture light = FXGL.getAssetLoader()
                    .loadTexture("bonus.png");
            light.setTranslateX(-8);
            light.setTranslateY(-8);
            Group g = new Group(light, texture);
            return g;
        }
        return texture;
    }

    @Override
    protected void doneMaking(Entity entity) {
        entity.setProperty("have-bonus", enemy.get("have-bonus"));
    }

    @Override
    protected void init() {
        enemy = GameController
                .getInstance()
                .popEnemy();
    }
}
