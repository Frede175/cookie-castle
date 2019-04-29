package dk.sdu.cookie.castle.common.data;

public class Point {
    private float x, y;

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * adds two coordinates together
     *
     * @param a first coordinate
     * @param b second coordinate
     * @return third new coordinate which is the sum of a + b
     */
    public static Point add(Point a, Point b) {
        return new Point(a.getX() + b.getX(), a.getY() + b.getY());
    }

}
