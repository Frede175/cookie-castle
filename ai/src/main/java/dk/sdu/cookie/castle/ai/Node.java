package dk.sdu.cookie.castle.ai;

import dk.sdu.cookie.castle.common.util.Vector2f;

// The node class is used for the AStar AI
// Nodes contain the information for the AStar, to find the best choice of movement towards the goal
public class Node implements Comparable<Node> {
    private Vector2f Vector2f;
    private double heuristic;
    private float cost;
    private Node parent;

    public Node(Vector2f Vector2f, double heuristic, float cost, Node parent) {
        this.Vector2f = Vector2f;
        this.heuristic = heuristic;
        this.cost = cost;
        this.parent = parent;
    }

    public Vector2f getPoint() {
        return Vector2f;
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

    public void updateNode(Node node) {
        cost = node.cost;
        heuristic = node.heuristic;
        parent = node.parent;
    }

    //Comparator for nodes, to be able to compare the nodes with each other, based on the cost and heuristic
    @Override
    public boolean equals(Object o) {
        if (o instanceof Node) {
            Node other = ((Node) o);
            return Vector2f.getX() == other.getPoint().getX() && Vector2f.getY() == other.getPoint().getY();
        }
        return false;
    }
}
