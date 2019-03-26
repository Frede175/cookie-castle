package dk.sdu.cookie.castle.collision.util;

public class MTV {
    private Vector2 axis;
    private float distance;

    public MTV(Vector2 axis, float distance) {
        this.axis = axis;
        this.distance = distance;
    }

    public Vector2 getAxis() {
        return axis;
    }

    public float getDistance() {
        return distance;
    }
}
