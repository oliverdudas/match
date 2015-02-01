package com.dudas.game.gameobject;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by OLO on 24. 1. 2015
 */
public class Gem extends AbstractGameObject {

    private GemType type;
    private GemState state;
    private Vector2 fallTo;

    public Gem(GemType type, float positionX, float positionY) {
        super(positionX, positionY);
        this.type = type;
        this.state = GemState.READY;
        this.fallTo = new Vector2(0, 0);
    }

    public GemType getType() {
        return type;
    }

    public void setType(GemType type) {
        this.type = type;
    }

    public int getIndex() {
        return (int) (position.x * 9 + position.y);
    }

    public void setIndex(int index) {
        position.x = index / 9;
        position.y = index % 9;
    }

    public void setFallTo(float x, float y) {
        fallTo.set(x, y);
    }

    public boolean isFalling() {
        return fallTo.x != 0;
    }

    public void updateFalling(float moveSpeed) {
        position.y -= moveSpeed;
        if (position.y <= fallTo.y) {
            position.y = fallTo.y;
            fallTo.setZero();
        }
    }

    public enum GemState {
        READY,
        BLOCKED
    }

    public void block() {
        state = GemState.BLOCKED;
    }

    public void setReady() {
        state = GemState.READY;
    }

    public boolean isReady() {
        return GemState.READY.equals(state);
    }

    public boolean isBlocked() {
        return GemState.BLOCKED.equals(state);
    }

    public enum GemType {
        RED,
        GREEN,
        BLUE,
        PURPLE,
        YELLOW,
        ORANGE,
        EMPTY
    }

    public void changeTypeRandomly() {
        if (this.getType().equals(Gem.GemType.EMPTY)) {
            this.setType(getRandomGemType());
        }
    }

    private static GemType getRandomGemType() {
        return GemType.values()[MathUtils.random(0, GemType.values().length - 2)]; // -1 to exclude EMPTY
    }

    public static Gem createNewRandomGem() {
        return new Gem(getRandomGemType(), 0, 0); //TODO: remove position info
    }
}
