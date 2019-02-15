package com.simplegame.TankMaker;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.Texture;
import com.simplegame.Component.TankControlComponent;
import com.simplegame.Controller.TankController;
import javafx.scene.Node;
import javafx.scene.paint.Color;

public class PlayerTankMaker extends TankMakerTemplate {

    public PlayerTankMaker(TankController controller) {
        super(controller);
    }

    @Override
    protected Component[] getTankComponents() {
        Component[] components = new Component[1];
        components[0] = new TankControlComponent(controller);
        controller.setTankControlComponent((TankControlComponent)components[0]);
        return components;
    }

    @Override
    protected Node makeNode() {
        Texture texture = FXGL.getAssetLoader()
                .loadTexture("tank_white.png");
        texture = texture.multiplyColor(Color.YELLOW);
        return texture;
    }

    @Override
    protected void doneMaking(Entity entity) { }

    @Override
    protected void init() { }
}
