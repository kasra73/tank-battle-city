package com.simplegame.ai;

import com.almasb.fxgl.ai.GoalAction;
import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.extra.ai.pathfinding.NodeState;
import com.almasb.fxgl.time.LocalTimer;
import com.simplegame.BasicGameApp;
import com.simplegame.Component.TankControlComponent;
import com.simplegame.Util.MoveDirection;
import javafx.util.Duration;

import java.util.Comparator;

public class RandomAStarMoveAction extends GoalAction {
    private LocalTimer timer = FXGL.newLocalTimer();

    public RandomAStarMoveAction() {
        super("RandomMove");
    }

    @Override
    public void start() {
        timer.capture();
        getEntity().getComponent(TankControlComponent.class)
                .setMoveDirection(FXGLMath.random(MoveDirection.values()).get());
    }

    @Override
    public boolean reachedGoal() {
        return timer.elapsed(Duration.seconds(2));
    }

    @Override
    public void end() {
        FXGL.<BasicGameApp>getAppCast()
                .getGrid()
                .getNodes()
                .stream()
                .filter(n -> n.getState() == NodeState.WALKABLE)
                .min(Comparator
                        .comparingDouble(n -> getEntity()
                        .getPosition()
                        .distance(n.getX() * BasicGameApp.BLOCK_SIZE + 5,
                                n.getY() * BasicGameApp.BLOCK_SIZE + 5)))
                .ifPresent(n -> {
                    getEntity().setPosition(n.getX() * BasicGameApp.BLOCK_SIZE, n.getY() * BasicGameApp.BLOCK_SIZE);
                });
    }

    @Override
    public void onUpdate(double v) {

    }
}
