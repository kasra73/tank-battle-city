package com.simplegame.Controller;

import com.simplegame.BasicGameApp;
import com.simplegame.Component.TankControlComponent;
import com.simplegame.TankMaker.TankMakerTemplate;
import com.simplegame.Util.MoveDirection;
import com.sun.istack.internal.NotNull;
import javafx.geometry.Point2D;

public abstract class TankController {
    private TankControlComponent tankControlComponent;
    private String name = "";

    private Point2D initLocation;

    public TankController(@NotNull String name) {
        this.name = name;
        this.initLocation = new Point2D(286,735);
    }

    public String getName() {
        return name;
    }

    public void setMoveDirection(MoveDirection moveDirection) {
        this.tankControlComponent.setMoveDirection(moveDirection);
    }

    protected void shoot() {
        this.tankControlComponent.shoot();
    }

    public void setTankControlComponent(TankControlComponent tankControlComponent) {
        this.tankControlComponent = tankControlComponent;
    }

    public TankControlComponent getTankControlComponent() {
        return tankControlComponent;
    }

    public void setInitLocation(Point2D initLocation) {
        this.initLocation = initLocation;
    }

    public Point2D getInitLocation() {
        return initLocation;
    }

    public abstract void onDestroy();

    public abstract BasicGameApp.EntityType getEntityType();

    public abstract TankMakerTemplate getTankMaker();

}
