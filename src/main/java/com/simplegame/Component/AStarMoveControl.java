package com.simplegame.Component;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.PositionComponent;
import com.almasb.fxgl.extra.ai.pathfinding.AStarGrid;
import com.almasb.fxgl.extra.ai.pathfinding.AStarNode;
import com.simplegame.BasicGameApp;
import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.List;

public class AStarMoveControl extends Component {

    private List<AStarNode> path = new ArrayList<>();

    private PositionComponent position;

    public boolean isDone() {
        return path.isEmpty();
    }

    public void moveTo(Point2D point) {

        position = getEntity().getPositionComponent();

        if (position.getValue().equals(point))
            return;

        AStarGrid grid = ((BasicGameApp) FXGL.getApp()).getGrid();

        int startX = (int)(position.getX() / BasicGameApp.BLOCK_SIZE);
        int startY = (int)(position.getY() / BasicGameApp.BLOCK_SIZE);

        int targetX = (int)((point.getX() + 8) / BasicGameApp.BLOCK_SIZE);
        int targetY = (int)((point.getY() + 8) / BasicGameApp.BLOCK_SIZE);

        path = grid.getPath(startX, startY, targetX, targetY);

        // we use A*, so no need for that
        getEntity().getComponentOptional(TankControlComponent.class).ifPresent(c -> c.pause());
    }

    @Override
    public void onUpdate(double tpf) {
        if (path.isEmpty())
            return;

        double speed = tpf * 60 * 5;

        AStarNode next = path.get(0);

        int nextX = next.getX() * BasicGameApp.BLOCK_SIZE;
        int nextY = next.getY() * BasicGameApp.BLOCK_SIZE;

        double dx = nextX - position.getX();
        double dy = nextY - position.getY();

        if (Math.abs(dx) <= speed)
            position.setX(nextX);
        else
            position.translateX(speed * Math.signum(dx));

        if (Math.abs(dy) <= speed)
            position.setY(nextY);
        else
            position.translateY(speed * Math.signum(dy));

        if (position.getX() == nextX && position.getY() == nextY) {
            path.remove(0);
        }

        if (path.isEmpty()) {
            getEntity().getComponentOptional(TankControlComponent.class).ifPresent(c -> c.resume());
        }
    }
}
