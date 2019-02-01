import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.extra.physics.handlers.CollectibleHandler;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.settings.GameSettings;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.Map;

public class BasicGameApp extends GameApplication {
    private Entity bullet;

    public static final int BLOCK_SIZE = 15;
    public static final int MAP_SIZE = 60;

    public enum EntityType {
        PLAYER, BRICK, STONE, Bullet, BORDER, EAGLE
    }

    public PlayerController getPlayerControl() {
        return getPlayer().getComponent(PlayerController.class);
    }
    public Entity getPlayer() {
        return getGameWorld().getSingleton(EntityType.PLAYER).get();
    }

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setConfigClass(Config.class);
        settings.setWidth(810);
        settings.setHeight(750);
        settings.setTitle("Tank Battle City");
        settings.setVersion("v0.1");
    }

    @Override
    protected void initGame() {
        PhysicsComponent physics = new PhysicsComponent();

        getGameWorld().addEntityFactory(new GameLevelFactory());
        getGameWorld().setLevelFromMap("level1.tmx");
    }

    @Override
    protected void initPhysics() {
        getPhysicsWorld().addCollisionHandler(new BulletStoneCollisionHandler());
        getPhysicsWorld().addCollisionHandler(new BulletBrickCollisionHandler());
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.Bullet, EntityType.BORDER) {
            // order of types is the same as passed into the constructor
            @Override
            protected void onCollisionBegin(Entity bullet, Entity border) {
                getPlayerControl().removeBullet();
            }
        });
    }

    @Override
    protected void initInput() {
        Input input = getInput();
        getInput().addAction(new UserAction("Up") {
            @Override
            protected void onAction() {
                getPlayerControl().up();
            }
        }, KeyCode.UP);

        getInput().addAction(new UserAction("Down") {
            @Override
            protected void onAction() {
                getPlayerControl().down();
            }
        }, KeyCode.DOWN);

        getInput().addAction(new UserAction("Left") {
            @Override
            protected void onAction() {
                getPlayerControl().left();
            }
        }, KeyCode.LEFT);

        getInput().addAction(new UserAction("Right") {
            @Override
            protected void onAction() {
                getPlayerControl().right();
            }
        }, KeyCode.RIGHT);

        input.addAction(new UserAction("Shoot") {
            @Override
            protected void onAction() {
                getPlayerControl().shoot();
            }
        }, KeyCode.SPACE);
    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("pixelsMoved", 0);
    }

    @Override
    protected void initUI() {
//        getGameScene().setBackgroundColor(Color.WHITE);
//        Text textPixels = new Text();
//        textPixels.setTranslateX(50); // x = 50
//        textPixels.setTranslateY(100); // y = 100
//        textPixels.textProperty()
//                .bind(getGameState().intProperty("pixelsMoved").asString());
//        Texture brickTexture = getAssetLoader().loadTexture("brick.png");
//        brickTexture.setTranslateX(50);
//        brickTexture.setTranslateY(450);
//        Texture menuTexture = getAssetLoader().loadTexture("border.png");
//        getGameScene().addUINodes(menuTexture, textPixels, brickTexture);
    }

    public static void main(String[] args) {
        launch(args);
    }
}