package dk.sdu.cookie.castle.common.util;

public class Vector2f {
    private float x, y;

    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Vector2f add(Vector2f other) {
        return new Vector2f(x + other.x, y + other.y);
    }

    public Vector2f subtract(Vector2f other) {
        return new Vector2f(x - other.x, y - other.y);
    }

    public Vector2f perp() {
        return new Vector2f(-y , x);
    }

    public Vector2f normalize() {
        float len = (float) Math.sqrt(x*x + y*y);
        return new Vector2f(x / len, y / len);
    }

    public float dot(Vector2f other) {
        return x * other.x + y * other.y;
    }

    public Vector2f invert() {
        return new Vector2f(-x, -y);
    }

    public Vector2f mult(float d) {
        return new Vector2f(x * d, y * d);
    }

}