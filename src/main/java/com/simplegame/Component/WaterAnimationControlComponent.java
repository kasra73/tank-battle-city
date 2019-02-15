package com.simplegame.Component;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.util.Duration;

public class WaterAnimationControlComponent extends Component {
    private final AnimatedTexture texture;
    private final AnimationChannel anim;
    private final AnimatedTexture centerTexture;
    private final AnimationChannel centerAnim;

    public WaterAnimationControlComponent() {
        anim = new AnimationChannel(
                "water.png",
                8,
                60,
                60,
                Duration.seconds(2),
                0,
                7);
        centerAnim = new AnimationChannel(
                "water-center.png",
                8,
                30,
                30,
                Duration.seconds(2),
                0,
                7);
        texture = new AnimatedTexture(anim);
        centerTexture = new AnimatedTexture(centerAnim);
        centerTexture.setScaleX(0.68);
        centerTexture.setScaleY(0.68);
        centerTexture.setTranslateX(15);
        centerTexture.setTranslateY(15);
    }

    @Override
    public void onAdded() {
        Group g = new Group();
        ObservableList<Node> children = g.getChildren();
        texture.loop();
        centerTexture.loop();
        children.add(texture);
        children.add(centerTexture);
        entity.getViewComponent().setView(g);
    }
}
