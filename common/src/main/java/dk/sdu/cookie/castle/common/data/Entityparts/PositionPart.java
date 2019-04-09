package dk.sdu.cookie.castle.common.data.Entityparts;

import dk.sdu.cookie.castle.common.data.Entity;
import dk.sdu.cookie.castle.common.data.GameData;

public class PositionPart implements EntityPart {

    private float x;
    private float y;
    private float radians;

    public PositionPart(float x, float y, float radians) {
        this.x = x;
        this.y = y;
        this.radians = radians;
    }

    public float getRadians() {
        return radians;
    }

    public void setRadians(float radians) {
        this.radians = radians;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float newX) {
        x = newX;
    }

    public void setY(float newY) {
        y = newY;
    }

    public void setPosition(float newX, float newY) {
        x = newX;
        y = newY;
    }

    @Override
    public void process(GameData gameData, Entity entity) {
    }
}
