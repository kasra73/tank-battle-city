package com.simplegame.Controller;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.simplegame.BasicGameApp;
import javafx.util.Duration;

import java.util.*;
import java.util.List;

public class GameController {
    private static GameController instance;

    private HashMap<String, TankController> controllers = new HashMap<>();
    private LinkedList<LinkedHashMap<String, Object>> enemies;

    private GameController() { }

    public static GameController getInstance() {
        if (instance == null) {
            instance = new GameController();
        }
        return instance;
    }

    public TankController getController(String key) {
        return controllers.get(key);
    }


    public void init() {
        this.initControllers();
        this.enemies = FXGL.getAssetLoader().loadJSON("enemies.json", LinkedList.class);
        FXGL.getGameState().setValue("remainingTanksCount", enemies.size());
    }

    private void initControllers() {
        TankController tankController;
        tankController = new EnemyTankController(EnemyTankController.Position.LEFT, "left");
        controllers.put("left", tankController);
        tankController = new EnemyTankController(EnemyTankController.Position.CENTER, "center");
        controllers.put("center", tankController);
        tankController = new EnemyTankController(EnemyTankController.Position.RIGHT, "right");
        controllers.put("right", tankController);
        tankController = new PlayerOneController("player-one");
        controllers.put("player-one", tankController);
    }

    public TankController getControllers(String key) {
        return controllers.get(key);
    }

    synchronized public void enemyDestroyed(EnemyTankController enemyTankController) {
        Random randTime = new Random();
        int nextspawn = randTime.nextInt() % 2000 + 3000;
        FXGL.getMasterTimer().runOnceAfter(() -> {
            if(!enemies.isEmpty()) {
                Entity entity = enemyTankController.getTankMaker().makeTank();
                FXGL.getGameWorld().addEntity(entity);
                FXGL.getGameState().increment("remainingTanksCount", -1);
            } else {
                checkWin();
            }
        }, Duration.millis(nextspawn));
    }

    public LinkedHashMap<String, Object> popEnemy() {
        return enemies.pop();
    }


    public void playerDestroyed(PlayerOneController playerOneController) {
        Entity entity = playerOneController.getTankMaker().makeTank();
        FXGL.getGameWorld().addEntity(entity);
        FXGL.getGameState().increment("remainingLives", -1);
    }

    private void checkWin() {
        List<Entity> enemiesEntities = FXGL.getGameWorld()
                .getEntitiesByType(BasicGameApp.EntityType.TANK);
        if(enemiesEntities.size() < 2) {
            FXGL.getDisplay().showMessageBox("Level Complete!", () -> {
                System.out.println("Dialog closed!");
            });
        }
    }

    public void spawnReward() {
        Random random = new Random();
        int x = (random.nextInt() % 54) * BasicGameApp.BLOCK_SIZE + 16;
        int y = (random.nextInt() % 54) * BasicGameApp.BLOCK_SIZE + 16;
        FXGL.getGameWorld().spawn("reward", new SpawnData(x, y));
    }
}
