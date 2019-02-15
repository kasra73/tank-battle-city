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

public class BulletEagleCollisionHandler extends CollisionHandler {
    private final BasicGameApp g;

    /**
     * The order of types determines the order of entities in callbacks.
     *
     */
    public BulletEagleCollisionHandler() {
        super(BasicGameApp.EntityType.BULLET, BasicGameApp.EntityType.EAGLE);
        g = (BasicGameApp) FXGL.getApp();
    }

    @Override
    protected void onCollisionBegin(Entity bullet, Entity eagle) {
        BulletControlComponent bcc = bullet.getComponent(BulletControlComponent.class);
        bcc.removeBullet();
        eagle.removeFromWorld();
        g.getGameWorld().spawn(
                "explosion",
                new SpawnData(eagle.getX(), eagle.getY()));
    }
}
