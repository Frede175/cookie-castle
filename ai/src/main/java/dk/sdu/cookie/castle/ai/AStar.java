package dk.sdu.cookie.castle.ai;

import dk.sdu.cookie.castle.common.data.Entity;
import dk.sdu.cookie.castle.common.data.Entityparts.PositionPart;
import dk.sdu.cookie.castle.common.data.GameData;
import dk.sdu.cookie.castle.common.data.Point;
import dk.sdu.cookie.castle.common.data.World;
import dk.sdu.cookie.castle.common.services.AIService;

import java.util.*;

public class AStar implements AIService {

    private final float STRAIGHT_COST = 1f;
    private final float DIAGONAL_COST = (float) Math.sqrt(2);
    private boolean[] grid;
    private PriorityQueue<Node> fringe;
    private Set<Node> usedNodes;
    private final int SIZE_OF_CELL =  25;
    private final int WIDTH_OF_GRID = 40;
    private final int HEIGHT_OF_GRID = 20;

    public AStar() {
        fringe = new PriorityQueue<Node>();
        usedNodes = new HashSet<>();
        grid = new boolean[WIDTH_OF_GRID*HEIGHT_OF_GRID];
    }

    @Override
    public LinkedList<Point> calculateRoute(Point start, Point end) {
        LinkedList<Point> route = new LinkedList<>();
        Point startGrid = toGrid(start);
        Point endGrid = toGrid(end);
        Node startNode = new Node(startGrid, calculateHeuristic(startGrid, endGrid), 0, null);
        Node endNode = new Node(endGrid, 0, 0, null);
        findNeighbours(startNode, endNode);
        Node nextNode = fringe.poll();
        while(!fringe.isEmpty() && !nextNode.equals(endNode)) {
            findNeighbours(nextNode, endNode);
            usedNodes.add(nextNode);
            nextNode = fringe.poll();
        }
        if(nextNode.equals(endNode)) {
            route.addFirst(end);
            nextNode = nextNode.getParent();
            while (nextNode.getParent() != null) {
                route.addFirst(new Point(nextNode.getPoint().getX() * SIZE_OF_CELL, nextNode.getPoint().getY() * SIZE_OF_CELL));
                nextNode = nextNode.getParent();
            }
        }
        for (Point p : route) {
            System.out.println("x: " + p.getX() + " y: " + p.getY());
        }
        return route;
    }

    public void setGrid(boolean[] grid) {
        this.grid = grid;
    }

    private double calculateHeuristic(Point start, Point end) {
        return Math.sqrt(((end.getX() - start.getX())*(end.getX() - start.getX())) + (end.getY() - start.getY())*(end.getY() - start.getY()));
    }

    private void findNeighbours(Node parent, Node end) {
        ArrayList<Node> neighbors = new ArrayList<>();
        int index = WIDTH_OF_GRID * (int) parent.getPoint().getY() + (int) parent.getPoint().getX();
        if (index % WIDTH_OF_GRID != WIDTH_OF_GRID - 1 && !grid[index + 1]) {
            neighbors.add(createNode(1, 0, parent, end, STRAIGHT_COST));
        }
        if (index % WIDTH_OF_GRID != 0 && !grid[index - 1]) {

            neighbors.add(createNode(-1, 0, parent, end, STRAIGHT_COST));
        }
        if (index - WIDTH_OF_GRID + 1 >= 0 && index % WIDTH_OF_GRID != WIDTH_OF_GRID - 1 && !grid[index - WIDTH_OF_GRID + 1]) {
            neighbors.add(createNode(1, -1, parent, end, DIAGONAL_COST));
        }
        if (index - WIDTH_OF_GRID >= 0 && !grid[index - WIDTH_OF_GRID]) {
            neighbors.add(createNode(0, -1, parent, end, STRAIGHT_COST));
        }
        if (index % WIDTH_OF_GRID != 0  && index - WIDTH_OF_GRID >= 0 && !grid[index - WIDTH_OF_GRID - 1]) {
            neighbors.add(createNode(-1, -1, parent, end, DIAGONAL_COST));
        }
        if (index % WIDTH_OF_GRID != 0  && index + WIDTH_OF_GRID <= WIDTH_OF_GRID * HEIGHT_OF_GRID - 1 && !grid[index + WIDTH_OF_GRID - 1]) {
            neighbors.add(createNode(-1, 1, parent, end, DIAGONAL_COST));
        }
        if (index + WIDTH_OF_GRID <= WIDTH_OF_GRID * HEIGHT_OF_GRID - 1 && !grid[index + WIDTH_OF_GRID]) {
            neighbors.add(createNode(0, 1, parent, end, STRAIGHT_COST));
        }
        if (index % WIDTH_OF_GRID != WIDTH_OF_GRID - 1 && index + WIDTH_OF_GRID <= WIDTH_OF_GRID * HEIGHT_OF_GRID - 1 && !grid[index + WIDTH_OF_GRID]) {
            neighbors.add(createNode(1, 1, parent, end, DIAGONAL_COST));
        }
        for (Node node : neighbors) {
            if (!fringe.contains(node)) {
                fringe.add(node);
            } else {
                for (Iterator<Node> it = fringe.iterator(); it.hasNext();) {
                    Node temp = it.next();
                    if (temp.equals(node)) {
                        if (temp.compareTo(node) > 0) {
                            temp.setCost(node.getCost());
                            temp.setHeuristic(node.getHeuristic());
                            break;
                        }
                    }
                }
            }
        }
    }

    public void updateGrid(World world, GameData gameData) {
        for (Entity entity : world.getEntities()) {
            switch (entity.getEntityType()) {
                case STATIC_OBSTACLE:
                case REMOVABLE_OBSTACLE:
                case WALL:
                    PositionPart positionPart = entity.getPart(PositionPart.class);
                    float radius = entity.getRadius();
                    Point min = toGrid(new Point(positionPart.getX() - radius, positionPart.getY() - radius));
                    Point max = toGrid(new Point(positionPart.getX() + radius, positionPart.getY() + radius));
                    System.out.println("min x " + min.getX() + " min y " + min.getY() + " max x " + max.getX() + " max y " + max.getY());
                    for (float y = min.getY(); y <= max.getY(); y++) {
                        for (float x = min.getX(); x <= max.getX(); x++) {
                            if (y < 0 || y > gameData.getDisplayHeight() || x < 0 || x > gameData.getDisplayWidth())
                                continue;
                            grid[WIDTH_OF_GRID * (int) y + (int) x] = true;
                        }
                    }
                    break;
            }
        }
    }

    private Point toGrid(Point point) {
        // TODO If you place an entity off the map, then in the edge there will be a blocked path
        int xGridCoordinate = (int) point.getX() / SIZE_OF_CELL;
        int yGridCoordinate = (int) point.getY() / SIZE_OF_CELL;
        if(xGridCoordinate < 0) {
            xGridCoordinate = 0;
        }
        if(xGridCoordinate >= WIDTH_OF_GRID) {
            xGridCoordinate = WIDTH_OF_GRID - 1;
        }
        if(yGridCoordinate < 0) {
            yGridCoordinate = 0;
        }
        if(yGridCoordinate >= HEIGHT_OF_GRID) {
            yGridCoordinate = HEIGHT_OF_GRID - 1;
        }
        return new Point(xGridCoordinate, yGridCoordinate);
    }

    private Point toGame(int index) {
        int y = index / WIDTH_OF_GRID * SIZE_OF_CELL + SIZE_OF_CELL / 2;
        int x = index % WIDTH_OF_GRID * SIZE_OF_CELL + SIZE_OF_CELL / 2;
        return new Point(x, y);
    }



    private Node createNode(int x, int y, Node parent, Node end, float cost) {
        Point newPoint = new Point(parent.getPoint().getX() + x, parent.getPoint().getY() + y);
        return new Node(newPoint, calculateHeuristic(newPoint, end.getPoint()), cost + parent.getCost(), parent);
    }
}
