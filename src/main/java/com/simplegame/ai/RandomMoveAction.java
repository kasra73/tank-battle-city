package com.simplegame.ai;

import com.almasb.fxgl.ai.GoalAction;
import com.almasb.fxgl.ai.btree.annotation.TaskAttribute;
import com.almasb.fxgl.ai.utils.random.ConstantIntegerDistribution;
import com.almasb.fxgl.ai.utils.random.IntegerDistribution;
import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.time.LocalTimer;
import com.simplegame.Component.TankControlComponent;
import com.simplegame.Util.MoveDirection;
import javafx.util.Duration;

public class RandomMoveAction extends GoalAction {

    @TaskAttribute
    public IntegerDistribution times = ConstantIntegerDistribution.ONE;
    private int t;
    private LocalTimer timer = FXGL.newLocalTimer();
    private MoveDirection[] directions;

    public RandomMoveAction() {
        directions = MoveDirection.values();
    }

    @Override
    public void start() {
        super.start();
        t = times.nextInt();
        timer.capture();
    }

    @Override
    public void reset() {
        times = ConstantIntegerDistribution.ONE;
        t = 0;
        super.reset();
    }

    @Override
    public boolean reachedGoal() {
        return timer.elapsed(Duration.seconds(t));
    }

    @Override
    public void onUpdate(double tpf) {
        if(FXGLMath.getRandom().nextInt()%60 == 0) {
            getEntity().getComponent(TankControlComponent.class).shoot();
        }
        if(FXGLMath.getRandom().nextInt()%25 == 0) {
            getEntity().getComponent(TankControlComponent.class)
                    .setMoveDirection(null);
        } else {
            getEntity().getComponent(TankControlComponent.class)
                    .setMoveDirection(FXGLMath.random(directions).get());
        }
    }
}
