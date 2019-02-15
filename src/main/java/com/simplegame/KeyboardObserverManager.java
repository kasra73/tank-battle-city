package com.simplegame;

import com.almasb.fxgl.input.ActionType;
import javafx.scene.input.KeyCode;

import java.util.HashSet;
import java.util.Set;

public class KeyboardObserverManager {
    private static KeyboardObserverManager instance;
    private final static KeyCode[] keyCodes = new KeyCode[]{
            KeyCode.UP,
            KeyCode.DOWN,
            KeyCode.RIGHT,
            KeyCode.LEFT,
            KeyCode.SPACE
    };

    private Set<KeyEevntSubscriber> subscribers = new HashSet<>();

    private KeyboardObserverManager() { }

    public static KeyboardObserverManager getInstance() {
        if (instance == null) {
            instance = new KeyboardObserverManager();
        }
        return instance;
    }

    public static KeyCode[] getKeyCodes() {
        return keyCodes;
    }

    public void subscribe(KeyEevntSubscriber subscriber) {
        subscribers.add(subscriber);
    }

    public void unsubscribe(KeyEevntSubscriber subscriber) {
        subscribers.remove(subscriber);
    }

    public void notify(KeyCode keyCode, ActionType actionType) {
        for (KeyEevntSubscriber subscriber : subscribers) {
            subscriber.invoke(keyCode, actionType);
        }
    }
}
