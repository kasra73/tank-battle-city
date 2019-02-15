package com.simplegame.Component;

import com.simplegame.BasicGameApp;
import com.simplegame.Controller.TankController;
import com.simplegame.Util.MoveDirection;
import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;

import com.almasb.fxgl.entity.RenderLayer;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.List;

public class TankControlComponent extends Component {
    // These 4 fields will be force assign by the framework (using reflection api)
    private PositionComponent position;
    private BoundingBoxComponent bbox;
    private ViewComponent view;
    private RotationComponent rotation;

    private TankController controller;
    private MoveDirection moveDirection = null;
    private MoveDirection lastMoveDirection = MoveDirection.UP;
    private Entity bullet;
    private double speed = 0;
    private static final double TANK_SPEED = 1;

    public TankControlComponent(TankController controller) {
        this.controller = controller;
    }

    @Override
    public void onUpdate(double tpf) {
        speed = tpf * 60;
        if(moveDirection == null) {
            return;
        }
        switch (moveDirection) {
            case UP:
                up();
                break;
            case DOWN:
                down();
                break;
            case LEFT:
                left();
                break;
            case RIGHT:
                right();
                break;
        }
    }

    private void up() {
        moveDirection = MoveDirection.UP;
        move(0, -TANK_SPEED * speed);
        rotation.setValue(0);
        view.getView().setScaleX(1);
    }

    private void down() {
        moveDirection = MoveDirection.DOWN;
        move(0, TANK_SPEED * speed);
        rotation.setValue(180);
        view.getView().setScaleX(1);
    }

    private void left() {
        moveDirection = MoveDirection.LEFT;
        move(-TANK_SPEED * speed, 0);
        rotation.setValue(-90);
    }

    private void right() {
        moveDirection = MoveDirection.RIGHT;
        move(TANK_SPEED * speed, 0);
        rotation.setValue(90);
    }

    synchronized private void move(double dx, double dy) {
        if (!getEntity().isActive()) {
            return;
        }
        GameApplication g = FXGL.getApp();
        List<Entity> blocks = g.getGameWorld().getEntitiesByType(
                BasicGameApp.EntityType.BRICK,
                BasicGameApp.EntityType.STONE,
                BasicGameApp.EntityType.BORDER,
                BasicGameApp.EntityType.EAGLE,
                BasicGameApp.EntityType.TANK,
                BasicGameApp.EntityType.WATER
                );
        double mag = Math.sqrt(dx * dx + dy * dy);
        long length = Math.round(mag);
        double unitX = dx / mag;
        double unitY = dy / mag;
        for (int i = 0; i < length; i++) {
            position.translate(unitX, unitY);
            boolean collision = false;
            for (Entity block : blocks) {
                if(block == entity) {
                    continue;
                }
                if (block.getBoundingBoxComponent().isCollidingWith(bbox)) {
                    collision = true;
                    break;
                }
            }
            if (collision) {
                setMoveDirection(null);
                position.translate(-unitX, -unitY);
                break;
            } else {
                g.getGameState().increment("pixelsMoved", +1);
            }
        }
    }

    public TankController getController() {
        return controller;
    }

    public synchronized void shoot() {
        if (bullet != null) {
            return;
        }
        bullet = Entities.builder()
                .type(BasicGameApp.EntityType.BULLET)
                .at(position.getX() + 23, position.getY() + 23)
                .viewFromNodeWithBBox(new Circle(6, Color.DARKSLATEGRAY))
                .renderLayer(RenderLayer.BACKGROUND)
                .with(new CollidableComponent(true))
                .with(new BulletControlComponent(lastMoveDirection, this))
                .buildAndAttach(FXGL.getGameWorld());
    }

    synchronized public void removeBullet() {
        bullet.removeFromWorld();
        bullet = null;
    }

    public MoveDirection getMoveDirection() {
        return this.moveDirection;
    }

    public synchronized void setMoveDirection(MoveDirection moveDirection) {
        if(this.moveDirection == null || moveDirection == null) {
            if(moveDirection != null) {
                this.lastMoveDirection = moveDirection;
            }
            this.moveDirection = moveDirection;
        }
    }

}
