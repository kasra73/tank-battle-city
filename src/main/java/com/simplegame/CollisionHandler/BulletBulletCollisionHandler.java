package com.simplegame.CollisionHandler;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.simplegame.BasicGameApp;
import com.simplegame.Component.BulletControlComponent;

public class BulletBulletCollisionHandler extends CollisionHandler {
    private final BasicGameApp g;

    /**
     * The order of types determines the order of entities in callbacks.
     *
     */
    public BulletBulletCollisionHandler() {
        super(BasicGameApp.EntityType.BULLET, BasicGameApp.EntityType.BULLET);
        g = (BasicGameApp) FXGL.getApp();
    }

    @Override
    protected void onCollisionBegin(Entity bullet1, Entity bullet2) {
        BulletControlComponent b1 = bullet1.getComponent(BulletControlComponent.class);
        BulletControlComponent b2 = bullet2.getComponent(BulletControlComponent.class);
        if(b1.getShooterTankControlComponent() == b2.getShooterTankControlComponent()) {
            return;
        }
        b1.removeBullet();
        b2.removeBullet();
    }
}
