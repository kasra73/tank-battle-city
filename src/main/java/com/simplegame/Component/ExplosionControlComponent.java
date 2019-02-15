package com.simplegame.Component;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.util.Duration;

public class ExplosionControlComponent extends Component {
    private int speed = 0;
    private AnimatedTexture texture;
    private AnimationChannel anim;

    public ExplosionControlComponent() {
        anim = new AnimationChannel(
                "explosion.png",
                4,
                60,
                60,
                Duration.seconds(1),
                0,
                20);
        texture = new AnimatedTexture(anim);
    }

    @Override
    public void onAdded() {
        entity.getViewComponent().setAnimatedTexture(texture, false, true);
    }

}
