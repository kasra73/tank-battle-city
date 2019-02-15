package com.simplegame.Util;

public enum MoveDirection {
    UP, RIGHT, DOWN, LEFT;

    public MoveDirection next() {
        int index = ordinal() + 1;

        if (index == values().length) {
            index = 0;
        }

        return values()[index];
    }

    public MoveDirection prev() {
        int index = ordinal() - 1;

        if (index == -1) {
            index = values().length - 1;
        }

        return values()[index];
    }
}
