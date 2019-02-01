import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.Entities;
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
        super(BasicGameApp.EntityType.Bullet, BasicGameApp.EntityType.BRICK);
        g = (BasicGameApp) FXGL.getApp();
    }

    @Override
    protected void onCollisionBegin(Entity bullet, Entity brick) {
        BulletController bulletController = bullet.getComponent(BulletController.class);
//        Entities.builder()
//                .viewFromNode(bulletController.getExplodingRect())
//                .buildAndAttach(g.getGameWorld());
        List<Entity> entitiesInRange = g.getGameWorld()
                .getEntitiesInRange(bulletController.getExplodingRect());
        g.getPlayerControl().removeBullet();
        for (Entity e: entitiesInRange) {
            if(e.isType(BasicGameApp.EntityType.BRICK)) {
                e.removeFromWorld();
            }
        }
    }
}
