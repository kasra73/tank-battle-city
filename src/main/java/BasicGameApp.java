import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.InputModifier;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.settings.GameSettings;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.Map;

public class BasicGameApp extends GameApplication {
    public static final int BLOCK_SIZE = 15;
    public static final int MAP_SIZE = 60;

    public enum EntityType {
        PLAYER, BRICK, STONE, Bullet, BORDER, EAGLE, ENEMY, TANK
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
        settings.setWidth(870);
        settings.setHeight(740);
        settings.setTitle("Tank Battle City");
        settings.setVersion("v0.1");
    }

    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(new GameLevelFactory());
        getGameWorld().setLevelFromMap("level1.tmx");
        Entities.builder()
                .type(EntityType.ENEMY)
                .type(EntityType.TANK)
                .at(16,16)
                .rotate(180)
                .viewFromTextureWithBBox("tank_pink.png")
                .with(new CollidableComponent(true))
                .buildAndAttach(getGameWorld());
        Entities.builder()
                .type(EntityType.ENEMY)
                .type(EntityType.TANK)
                .at(256,16)
                .rotate(90)
                .viewFromTextureWithBBox("tank_big_green.png")
                .with(new CollidableComponent(true))
                .buildAndAttach(getGameWorld());
        getGameWorld().spawn("enemy", new SpawnData(376,16));
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
        input.addAction(new UserAction("Up") {
            @Override
            protected void onAction() {
                getPlayerControl().up();
            }
        }, KeyCode.UP);

        input.addAction(new UserAction("Down") {
            @Override
            protected void onAction() {
                getPlayerControl().down();
            }
        }, KeyCode.DOWN);

        input.addAction(new UserAction("Left") {
            @Override
            protected void onAction() {
                getPlayerControl().left();
            }
        }, KeyCode.LEFT);

        input.addAction(new UserAction("Right") {
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
        Text textPixels = new Text();
        textPixels.setTranslateX(830); // x = 50
        textPixels.setTranslateY(100); // y = 100
        textPixels.textProperty()
                .bind(getGameState().intProperty("pixelsMoved").asString());
        Rectangle r = new Rectangle(60, 745, Color.GRAY);
        r.setX(810);
        getGameScene().addUINodes(r, textPixels);
    }

    public static void main(String[] args) {
        launch(args);
    }
}