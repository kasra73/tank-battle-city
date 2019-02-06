import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.shape.Rectangle;

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
//        Rectangle2D r = bulletController.getExplodingRect();
//        Entities.builder().at(r.getMinX(),r.getMinY()).viewFromNode(new Rectangle(
//                r.getWidth(),
//                r.getHeight()
//        )).buildAndAttach(g.getGameWorld());
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
