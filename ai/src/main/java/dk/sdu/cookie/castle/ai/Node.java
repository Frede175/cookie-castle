package dk.sdu.cookie.castle.ai;

import dk.sdu.cookie.castle.common.data.Point;

public class Node implements Comparable<Node> {
    private Point point;
    private double heuristic;
    private float cost;
    private Node parent;

    public Node(Point point, double heuristic, float cost, Node parent) {
        this.point = point;
        this.heuristic = heuristic;
        this.cost = cost;
        this.parent = parent;
    }

    public Point getPoint() {
        return point;
    }

    public double getHeuristic() {
        return heuristic;
    }

    public float getCost() {
        return cost;
    }

    public Node getParent() {
        return parent;
    }

    @Override
    public int compareTo(Node o) {
        return Double.compare(cost + heuristic, o.cost + o.heuristic);
    }

    public void setHeuristic(double heuristic) {
        this.heuristic = heuristic;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Node) {
            Node other = ((Node) o);
            return point.getX() == other.getPoint().getX() && point.getY() == other.getPoint().getY();
        }
        return false;
    }
}
