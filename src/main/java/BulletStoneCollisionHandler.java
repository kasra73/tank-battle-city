import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;

public class BulletStoneCollisionHandler extends CollisionHandler {
    /**
     * The order of types determines the order of entities in callbacks.
     *
     * @param a entity type of the first entity
     * @param b entity type of the second entity
     */
    public BulletStoneCollisionHandler() {
        super(BasicGameApp.EntityType.Bullet, BasicGameApp.EntityType.STONE);
    }

    @Override
    protected void onCollisionBegin(Entity bullet, Entity stone) {
        BasicGameApp g = (BasicGameApp) FXGL.getApp();
        g.getPlayerControl().removeBullet();
    }
}
