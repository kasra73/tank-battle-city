package com.simplegame.Controller;

import com.almasb.fxgl.app.FXGL;
import com.simplegame.BasicGameApp;
import com.simplegame.TankMaker.EnemyTankMaker;
import com.simplegame.TankMaker.TankMakerTemplate;
import com.simplegame.Util.MoveDirection;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

public class EnemyTankController extends TankController {
    public enum Position { LEFT, CENTER, RIGHT }

    public EnemyTankController(Position position, String name) {
        super(name);
        Point2D initLocation = null;
        switch (position) {
            case LEFT:
                initLocation = new Point2D(18,18);
                break;
            case RIGHT:
                initLocation = new Point2D(738,18);
                break;
            case CENTER:
                initLocation = new Point2D(378,18);
                break;
        }
        this.setInitLocation(initLocation);
    }

    @Override
    public void onDestroy() {
        BasicGameApp gameApp = FXGL.getAppCast();
        FXGL.getGameState().increment("score", 200);
        gameApp.getGameController().enemyDestroyed(this);
    }

    @Override
    public BasicGameApp.EntityType getEntityType() {
        return BasicGameApp.EntityType.ENEMY;
    }

    @Override
    public TankMakerTemplate getTankMaker() {
        return new EnemyTankMaker(this, Color.LIGHTBLUE);
    }

    public void move(MoveDirection moveDirection) {
        this.setMoveDirection(moveDirection);
    }

    public void setMoveDirection(MoveDirection next) {

    }
}
