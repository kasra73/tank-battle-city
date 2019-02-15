package com.simplegame.CollisionHandler;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.simplegame.BasicGameApp;
import com.simplegame.Component.BulletControlComponent;

public class BulletStoneCollisionHandler extends CollisionHandler {

    public BulletStoneCollisionHandler() {
        super(BasicGameApp.EntityType.BULLET, BasicGameApp.EntityType.STONE);
    }

    @Override
    protected void onCollisionBegin(Entity bullet, Entity stone) {
        BasicGameApp g = (BasicGameApp) FXGL.getApp();
        BulletControlComponent component = bullet.getComponent(BulletControlComponent.class);
        component.removeBullet();
    }
}
