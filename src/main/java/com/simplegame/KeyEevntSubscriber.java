package com.simplegame;

import com.almasb.fxgl.input.ActionType;
import javafx.scene.input.KeyCode;

public interface KeyEevntSubscriber {
    void invoke(KeyCode keyCode, ActionType actionType);
}
