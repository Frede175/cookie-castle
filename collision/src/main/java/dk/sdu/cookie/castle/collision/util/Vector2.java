package dk.sdu.cookie.castle.collision.util;

import java.util.Vector;

public class Vector2 {
    private float x, y;

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Vector2 add(Vector2 other) {
        return new Vector2(x + other.x, y + other.y);
    }

    public Vector2 subtract(Vector2 other) {
        return new Vector2(x - other.x, y - other.y);
    }

    public Vector2 perp() {
        return new Vector2(-y , x);
    }

    public Vector2 normalize() {
        float len = (float) Math.sqrt(x*x + y*y);
        return new Vector2(x / len, y / len);
    }

    public float dot(Vector2 other) {
        return x * other.x + y * other.y;
    }

    public Vector2 invert() {
        return new Vector2(-x, -y);
    }

    public Vector2 mult(float d) {
        return new Vector2(x * d, y * d);
    }

}
