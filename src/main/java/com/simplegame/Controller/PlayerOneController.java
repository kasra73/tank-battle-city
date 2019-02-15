package com.simplegame.Controller;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.input.ActionType;
import com.simplegame.BasicGameApp;
import com.simplegame.KeyEevntSubscriber;
import com.simplegame.KeyboardObserverManager;
import com.simplegame.TankMaker.EnemyTankMaker;
import com.simplegame.TankMaker.PlayerTankMaker;
import com.simplegame.TankMaker.TankMakerTemplate;
import com.simplegame.Util.MoveDirection;
import com.sun.istack.internal.NotNull;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.List;

public class PlayerOneController extends TankController implements KeyEevntSubscriber {
    private KeyCode moveDirection = null;
    private List<KeyCode> moveKeys = Arrays.asList(KeyCode.LEFT, KeyCode.RIGHT, KeyCode.DOWN, KeyCode.UP);

    public PlayerOneController(@NotNull String name) {
        super(name);
        KeyboardObserverManager kom = KeyboardObserverManager.getInstance();
        kom.subscribe(this);
    }

    @Override
    public void invoke(KeyCode keyCode, ActionType actionType) {
        if(actionType == ActionType.ON_ACTION_BEGIN) {
            moveByKey(keyCode);
        }
        if(actionType == ActionType.ON_ACTION_END) {
            unsetMoveDirection(keyCode);
        }
    }

    public void moveByKey(KeyCode keyCode) {
        if(moveKeys.contains(keyCode) &&
                moveDirection != null &&
                moveDirection != keyCode) {
            return;
        }
        switch (keyCode) {
            case RIGHT:
                this.setMoveDirection(MoveDirection.RIGHT);
                break;
            case LEFT:
                this.setMoveDirection(MoveDirection.LEFT);
                break;
            case DOWN:
                this.setMoveDirection(MoveDirection.DOWN);
                break;
            case UP:
                this.setMoveDirection(MoveDirection.UP);
                break;
            case SPACE:
                this.shoot();
                break;
        }
    }

    public void unsetMoveDirection(KeyCode keyCode) {
        if(moveKeys.contains(keyCode)) {
            this.setMoveDirection(null);
        }
    }

    @Override
    public void onDestroy() {
        BasicGameApp gameApp = FXGL.getAppCast();
        gameApp.getGameController().playerDestroyed(this);
    }

    @Override
    public BasicGameApp.EntityType getEntityType() {
        return BasicGameApp.EntityType.PLAYER;
    }

    @Override
    public TankMakerTemplate getTankMaker() {
        return new PlayerTankMaker(this);
    }

}
