package com.simplegame;

import com.almasb.fxgl.extra.ai.pathfinding.AStarGrid;
import com.almasb.fxgl.extra.ai.pathfinding.NodeState;
import com.almasb.fxgl.input.ActionType;
import com.simplegame.CollisionHandler.BulletBrickCollisionHandler;
import com.simplegame.CollisionHandler.BulletBulletCollisionHandler;
import com.simplegame.CollisionHandler.BulletStoneCollisionHandler;
import com.simplegame.CollisionHandler.BulletTankCollisionHandler;
import com.simplegame.Component.BulletControlComponent;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.settings.GameSettings;
import com.simplegame.Controller.GameController;
import com.simplegame.Util.Config;
import com.simplegame.ui.SideMenu;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;

import java.util.Map;

public class BasicGameApp extends GameApplication {
    public static final int BLOCK_SIZE = 15;
    public static final int MAP_SIZE = 60;
    public static final int MAP_WIDTH = 870;
    public static final int MAP_HEIGHT = 800;
    private GameController gameController;
    private Thread gameControlThread;

    public enum EntityType {
        PLAYER, BRICK, STONE, BULLET, BORDER, EAGLE, ENEMY, WATER, GRASS, REWARD, TANK
    }

    public GameController getGameController() {
        return gameController;
    }

    private AStarGrid grid;

    public AStarGrid getGrid() {
        return grid;
    }

    private void initGrid() {
        // init the A* underlying grid and mark nodes where blocks are as not walkable
        grid = new AStarGrid(MAP_SIZE, MAP_SIZE);
        getGameWorld().getEntitiesByType(
                EntityType.BRICK,
                EntityType.STONE,
                EntityType.BORDER)
                .stream()
//                .map(Entity::getPosition)
                .forEach(e -> {
                    Point2D point = e.getPosition();
                    int x = (int) (point.getX() / e.getWidth());
                    int y = (int) (point.getY() / e.getHeight());
                    grid.setNodeState(x, y, NodeState.NOT_WALKABLE);
                });
    }

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setConfigClass(Config.class);
        settings.setWidth(MAP_WIDTH);
        settings.setHeight(MAP_HEIGHT);
        settings.setTitle("Tank Battle City");
        settings.setVersion("v0.1");
    }

    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(new GameLevelFactory());
        gameController = GameController.getInstance();
        gameController.init();
        getGameWorld().setLevelFromMap("level1.tmx");
        initGrid();
        getAssetLoader().cache();
//        gameControlThread = new Thread(gameController);
//        gameControlThread.start();
    }

    @Override
    protected void initPhysics() {
        getPhysicsWorld().addCollisionHandler(new BulletStoneCollisionHandler());
        getPhysicsWorld().addCollisionHandler(new BulletBrickCollisionHandler());
        getPhysicsWorld().addCollisionHandler(new BulletTankCollisionHandler());
        getPhysicsWorld().addCollisionHandler(new BulletBulletCollisionHandler());
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.BULLET, EntityType.BORDER) {
            // order of types is the same as passed into the constructor
            @Override
            protected void onCollisionBegin(Entity bullet, Entity border) {
                BulletControlComponent component = bullet.getComponent(BulletControlComponent.class);
                component.removeBullet();
            }
        });
    }

    @Override
    protected void initInput() {
        Input input = getInput();
        KeyboardObserverManager km = KeyboardObserverManager.getInstance();
        KeyCode[] keyCodes = KeyboardObserverManager.getKeyCodes();
        for (KeyCode keyCode : keyCodes) {
            input.addAction(new UserAction(keyCode.getName()) {
                @Override
                protected void onActionBegin() { km.notify(keyCode, ActionType.ON_ACTION_BEGIN); }

                @Override
                protected void onAction() {
                    km.notify(keyCode, ActionType.ON_ACTION);
                }

                @Override
                protected void onActionEnd() { km.notify(keyCode, ActionType.ON_ACTION_END); }
            }, keyCode);
        }
    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("remainingTanksCount", 0);
        vars.put("remainingLives", 2);
        vars.put("pixelsMoved", 0);
    }

    @Override
    protected void initUI() {
        SideMenu s = new SideMenu();
        s.setTranslateX(800);
        getGameScene().addUINodes(s);
    }

    public static void main(String[] args) {
        launch(args);
    }
}