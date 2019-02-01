import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.*;
import javafx.animation.FadeTransition;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.util.List;
import java.util.Random;

/**
 * TODO: merge with MoveControl
 *
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 */
public class PlayerController extends Component {
    private PositionComponent position;
    private BoundingBoxComponent bbox;
    private ViewComponent view;
    private RotationComponent rotation;
    private static final int TANK_SPEED = 3;

    private MoveDirection moveDir = MoveDirection.UP;
    private Entity bullet;

    public MoveDirection getMoveDirection() {
        return moveDir;
    }

    private double speed = 0;

    @Override
    public void onUpdate(double tpf) {
        speed = tpf * 60;
        if (position.getX() < 0) {
            position.setX(BasicGameApp.BLOCK_SIZE * BasicGameApp.MAP_SIZE - bbox.getWidth() - 5);
        }
        if (bbox.getMaxXWorld() > BasicGameApp.BLOCK_SIZE * BasicGameApp.MAP_SIZE) {
            position.setX(0);
        }
    }

    public void up() {
        moveDir = MoveDirection.UP;
        move(0, -TANK_SPEED*speed);
        rotation.setValue(0);
        view.getView().setScaleX(1);
    }

    public void down() {
        moveDir = MoveDirection.DOWN;
        move(0, TANK_SPEED*speed);
        rotation.setValue(180);
        view.getView().setScaleX(1);
    }

    public void left() {
        moveDir = MoveDirection.LEFT;
        move(-TANK_SPEED*speed, 0);
        rotation.setValue(-90);
    }

    public void right() {
        moveDir = MoveDirection.RIGHT;
        move(TANK_SPEED*speed, 0);
        rotation.setValue(90);
    }

    private void move(double dx, double dy) {
        if (!getEntity().isActive()) {
            return;
        }
        List<Entity> blocks = FXGL.getApp().getGameWorld().getEntitiesByType(
                BasicGameApp.EntityType.BRICK,
                BasicGameApp.EntityType.STONE,
                BasicGameApp.EntityType.BORDER);
        double mag = Math.sqrt(dx * dx + dy * dy);
        long length = Math.round(mag);
        double unitX = dx / mag;
        double unitY = dy / mag;
        for (int i = 0; i < length; i++) {
            position.translate(unitX, unitY);
            boolean collision = false;
            for (int j = 0; j < blocks.size(); j++) {
                if (blocks.get(j).getBoundingBoxComponent().isCollidingWith(bbox)) {
                    collision = true;
                    break;
                }
            }
            if (collision) {
                position.translate(-unitX, -unitY);
                break;
            }
        }
    }

    public synchronized void shoot() {
        if(bullet != null) {
            return;
        }
        BasicGameApp g = (BasicGameApp) FXGL.getApp();
        bullet = Entities.builder()
                .type(BasicGameApp.EntityType.Bullet)
                .at(position.getX() + 21, position.getY() + 21)
                .viewFromNodeWithBBox(new Circle(8))
                .with(new CollidableComponent(true))
                .with(new BulletController(moveDir))
                .buildAndAttach(g.getGameWorld());
    }

    synchronized public void removeBullet() {
        bullet.removeFromWorld();
        bullet = null;
    }
}
