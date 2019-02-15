package com.simplegame.CollisionHandler;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.util.Optional;
import com.simplegame.BasicGameApp;
import com.simplegame.Component.BulletControlComponent;
import com.simplegame.Component.TankControlComponent;
import com.simplegame.Controller.GameController;
import com.simplegame.Controller.TankController;

public class TankRewardCollisionHandler extends CollisionHandler {
    private final BasicGameApp g;

    /**
     * The order of types determines the order of entities in callbacks.
     *
     */
    public TankRewardCollisionHandler() {
        super(BasicGameApp.EntityType.TANK, BasicGameApp.EntityType.REWARD);
        g = (BasicGameApp) FXGL.getApp();
    }

    @Override
    protected void onCollisionBegin(Entity tank, Entity reward) {
        TankControlComponent tankComponent = tank.getComponent(TankControlComponent.class);
        TankController controller = tankComponent.getController();
        if (controller.getName().startsWith("player")) {
            GameController.getInstance().rewardCollected(reward);
            reward.removeFromWorld();
        }

    }
}
