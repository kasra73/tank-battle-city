import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.*;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GameLevelFactory implements EntityFactory {

    @Spawns("brick")
    public Entity newBrick(SpawnData data) {
        return Entities.builder()
                .type(BasicGameApp.EntityType.BRICK)
                .from(data)
                .with(new CollidableComponent(true))
                .bbox(new HitBox("__VIEW__", BoundingShape.box(15,15)))
                .renderLayer(RenderLayer.BACKGROUND)
                .build();
    }

    @Spawns("stone")
    public Entity newStone(SpawnData data) {
        return Entities.builder()
                .type(BasicGameApp.EntityType.STONE)
                .from(data)
                .with(new CollidableComponent(true))
                .bbox(new HitBox("__VIEW__", BoundingShape.box(15,15)))
                .renderLayer(RenderLayer.BACKGROUND)
                .build();
    }

    @Spawns("border")
    public Entity newBorder(SpawnData data) {
        int width = data.<Integer>get("width");
        int height = data.<Integer>get("height");
        return Entities.builder()
                .type(BasicGameApp.EntityType.BORDER)
                .from(data)
                .viewFromNodeWithBBox(new Rectangle(width, height, Color.GRAY))
                .with(new CollidableComponent(true))
                .renderLayer(RenderLayer.BACKGROUND)
                .build();
    }

    @Spawns("player")
    public Entity newPlayer(SpawnData data) {
        return Entities.builder()
                .from(data)
                .type(BasicGameApp.EntityType.PLAYER)
                .viewFromTextureWithBBox("tank.png")
                .renderLayer(RenderLayer.DEFAULT)
                .with(new CollidableComponent(true))
                .with(new PlayerController())
                .build();
    }

    @Spawns("enemy")
    public Entity newEnemy(SpawnData data) {
        return Entities.builder()
                .from(data)
                .type(BasicGameApp.EntityType.ENEMY)
                .type(BasicGameApp.EntityType.TANK)
                .viewFromNodeWithBBox(FXGL.getAssetLoader()
                        .loadTexture("tank.png")
                        .multiplyColor(Color.rgb(200,200,55)))
                .with(new CollidableComponent(true))
                .with(new PlayerController())
                .build();
    }

    @Spawns("eagle")
    public Entity newEagle(SpawnData data) {
        return Entities.builder()
                .from(data)
                .type(BasicGameApp.EntityType.EAGLE)
                .viewFromTextureWithBBox("eagle.png")
                .with(new CollidableComponent(true))
                .with(new PlayerController())
                .build();
    }

}
