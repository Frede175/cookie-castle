package dk.sdu.cookie.castle.collision.util;

import dk.sdu.cookie.castle.common.util.Vector2f;

public class MTV {
    private Vector2f axis;
    private float distance;

    public MTV(Vector2f axis, float distance) {
        this.axis = axis;
        this.distance = distance;
    }

    public Vector2f getAxis() {
        return axis;
    }

    public float getDistance() {
        return distance;
    }
}
