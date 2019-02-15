package com.simplegame.CollisionHandler;

import com.simplegame.BasicGameApp;
import com.simplegame.Component.BulletControlComponent;
import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;

import java.util.List;

public class BulletBrickCollisionHandler extends CollisionHandler {
    private final BasicGameApp g;

    /**
     * The order of types determines the order of entities in callbacks.
     *
     */
    public BulletBrickCollisionHandler() {
        super(BasicGameApp.EntityType.BULLET, BasicGameApp.EntityType.BRICK);
        g = (BasicGameApp) FXGL.getApp();
    }

    @Override
    protected void onCollisionBegin(Entity bullet, Entity brick) {
        BulletControlComponent bulletControlComponent = bullet.getComponent(BulletControlComponent.class);
//        Rectangle2D r = bulletControlComponent.getExplodingRect();
//        Entities.builder().at(r.getMinX(),r.getMinY()).viewFromNode(new Rectangle(
//                r.getWidth(),
//                r.getHeight()
//        )).buildAndAttach(g.getGameWorld());
        List<Entity> entitiesInRange = g.getGameWorld()
                .getEntitiesInRange(bulletControlComponent.getExplodingRect());
        BulletControlComponent component = bullet.getComponent(BulletControlComponent.class);
        component.removeBullet();
        for (Entity e: entitiesInRange) {
            if(e.isType(BasicGameApp.EntityType.BRICK)) {
                e.removeFromWorld();
            }
        }
    }
}
