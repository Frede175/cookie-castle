package dk.sdu.cookie.castle.collision.util;

public class Projection {
    private float min, max;

    public Projection(float min, float max) {
        this.min = min;
        this.max = max;
    }

    public boolean overlap(Projection other) {
        return !(max < other.min || other.max < min);
    }

    public float getOverlap(Projection other) {
        if (overlap(other)) {
            if (max < other.max) {
                return max - other.min;
            }
            return other.max - min;
        }
        return 0;
    }

    public float getMin() {
        return min;
    }

    public float getMax() {
        return max;
    }
}
