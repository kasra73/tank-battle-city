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

public class BulletTankCollisionHandler extends CollisionHandler {
    private final BasicGameApp g;

    /**
     * The order of types determines the order of entities in callbacks.
     *
     */
    public BulletTankCollisionHandler() {
        super(BasicGameApp.EntityType.BULLET, BasicGameApp.EntityType.TANK);
        g = (BasicGameApp) FXGL.getApp();
    }

    @Override
    protected void onCollisionBegin(Entity bullet, Entity tank) {
        TankControlComponent tankComponent = tank.getComponent(TankControlComponent.class);
        BulletControlComponent bcc = bullet.getComponent(BulletControlComponent.class);
        TankController controller = tankComponent.getController();
        if(bcc.getShooterTankControlComponent() == tankComponent) {
            return;
        }
        if(controller != null) {
            controller.onDestroy();
        }
        Optional<Boolean> haveBonus = tank
                .getProperties()
                .getValueOptional("have-bonus");
        if(haveBonus.isPresent() && haveBonus.get()) {
            GameController.getInstance().spawnReward();
        }
        tank.removeFromWorld();
        bcc.removeBullet();
        g.getGameWorld().spawn(
                "explosion",
                new SpawnData(tank.getX(),tank.getY()));

    }
}
