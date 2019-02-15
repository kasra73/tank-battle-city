package com.simplegame.TankMaker;

import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.RenderLayer;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.simplegame.BasicGameApp;
import com.simplegame.Controller.TankController;
import javafx.collections.ObservableList;
import javafx.scene.Node;

abstract public class TankMakerTemplate {
    protected TankController controller;

    public TankMakerTemplate(TankController controller) {
        this.controller = controller;
    }

    public final Entity makeTank() {
        init();
        Node node = makeNode();
        Component[] components = getTankComponents();
        Entities.EntityBuilder eBuilder = Entities.builder()
                .type(BasicGameApp.EntityType.TANK)
                .at(controller.getInitLocation())
                .viewFromNode(node)
                .bbox(new HitBox("TANK_BBOX", BoundingShape.box(56, 56)))
                .renderLayer(RenderLayer.DEFAULT)
                .with(new CollidableComponent(true))
                .with(components);
        Entity entity = eBuilder.build();
        ObservableList<Node> nodes = entity.getView().getNodes();
        if (nodes.size() > 1) {
            nodes.remove(1);
        }
        doneMaking(entity);
        return entity;
    }

    abstract protected Component[] getTankComponents();
    abstract protected Node makeNode();
    abstract protected void doneMaking(Entity entity);
    abstract protected void init();

}
