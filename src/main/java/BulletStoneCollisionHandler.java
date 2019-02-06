import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;

public class BulletStoneCollisionHandler extends CollisionHandler {

    public BulletStoneCollisionHandler() {
        super(BasicGameApp.EntityType.Bullet, BasicGameApp.EntityType.STONE);
    }

    @Override
    protected void onCollisionBegin(Entity bullet, Entity stone) {
        BasicGameApp g = (BasicGameApp) FXGL.getApp();
        g.getPlayerControl().removeBullet();
    }
}
