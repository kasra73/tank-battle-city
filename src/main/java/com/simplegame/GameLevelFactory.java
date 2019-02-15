package com.simplegame;

import com.simplegame.Component.ExplosionControlComponent;
import com.almasb.fxgl.entity.*;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.simplegame.Component.WaterAnimationControlComponent;
import com.simplegame.Controller.GameController;
import com.simplegame.Controller.TankController;
import com.simplegame.Exception.NoControllerException;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GameLevelFactory implements EntityFactory {

    @Spawns("brick")
    public Entity newBrick(SpawnData data) {
        return Entities.builder()
                .type(BasicGameApp.EntityType.BRICK)
                .from(data)
                .with(new CollidableComponent(true))
                .bbox(new HitBox("__VIEW__", BoundingShape.box(15,15)))
                .renderLayer(RenderLayer.BACKGROUND)
                .build();
    }

    @Spawns("stone")
    public Entity newStone(SpawnData data) {
        return Entities.builder()
                .type(BasicGameApp.EntityType.STONE)
                .from(data)
                .with(new CollidableComponent(true))
                .bbox(new HitBox("__VIEW__", BoundingShape.box(15,15)))
                .renderLayer(RenderLayer.BACKGROUND)
                .build();
    }

    @Spawns("border")
    public Entity newBorder(SpawnData data) {
        int width = data.<Integer>get("width");
        int height = data.<Integer>get("height");
        return Entities.builder()
                .type(BasicGameApp.EntityType.BORDER)
                .from(data)
                .viewFromNodeWithBBox(new Rectangle(width, height, Color.GRAY))
                .with(new CollidableComponent(true))
                .renderLayer(RenderLayer.BACKGROUND)
                .build();
    }

    @Spawns("reward")
    public Entity newReward(SpawnData data) {
        return Entities.builder()
                .from(data)
                .type(BasicGameApp.EntityType.REWARD)
                .viewFromTextureWithBBox("bill.png")
                .with(new CollidableComponent(true))
                .renderLayer(RenderLayer.TOP)
                .build();
    }

    @Spawns("tank")
    public Entity newTank(SpawnData data) throws NoControllerException {
        TankController controller;
        if(data.hasKey("control")) {
            controller = GameController
                    .getInstance()
                    .getController(data.get("control"));
        } else {
            throw new NoControllerException("no suitable controller for this tank");
        }
        return controller.getTankMaker().makeTank();
    }

    @Spawns("eagle")
    public Entity newEagle(SpawnData data) {
        return Entities.builder()
                .from(data)
                .type(BasicGameApp.EntityType.EAGLE)
                .renderLayer(RenderLayer.DEFAULT)
                .viewFromTextureWithBBox("eagle.png")
                .with(new CollidableComponent(true))
                .build();
    }

    @Spawns("water")
    public Entity newWater(SpawnData data) {
        return Entities.builder()
                .from(data)
                .type(BasicGameApp.EntityType.WATER)
                .renderLayer(RenderLayer.BOTTOM)
                .bbox(new HitBox(
                        "WATER_ZONE",
                        BoundingShape.box(60, 60)))
                .with(new WaterAnimationControlComponent())
                .build();
    }

    @Spawns("grass")
    public Entity newGrass(SpawnData data) {
        return Entities.builder()
                .from(data)
                .type(BasicGameApp.EntityType.GRASS)
                .renderLayer(RenderLayer.TOP)
                .viewFromTexture("grass.png")
                .build();
    }

    @Spawns("explosion")
    public Entity newExplosion(SpawnData data) {
        return Entities.builder()
                .from(data)
                .renderLayer(RenderLayer.DEFAULT)
                .with(new ExplosionControlComponent())
                .build();
    }

}
