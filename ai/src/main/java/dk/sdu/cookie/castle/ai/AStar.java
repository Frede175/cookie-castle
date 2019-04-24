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
    private final int SIZE_OF_CELL = 25;
    private final int WIDTH_OF_GRID = 40;
    private final int HEIGHT_OF_GRID = 20;

    AStar() {
        fringe = new PriorityQueue<>();
        usedNodes = new HashSet<>();
        grid = new boolean[WIDTH_OF_GRID * HEIGHT_OF_GRID];
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

        //Loop that keeps updating the fringe for the next move towards the position of the player
        while (!fringe.isEmpty() && !nextNode.equals(endNode)) {
            findNeighbours(nextNode, endNode);
            usedNodes.add(nextNode);
            nextNode = fringe.poll();
        }
        //Check if the next node is the end-node (the node where the player is/was)
        if (nextNode.equals(endNode)) {
            route.addFirst(end);
            nextNode = nextNode.getParent();
            // while the next node has a parent, keep adding the next nodes parent to the route towards the player
            while (nextNode.getParent() != null) {
                route.addFirst(new Point(nextNode.getPoint().getX() * SIZE_OF_CELL, nextNode.getPoint().getY() * SIZE_OF_CELL));
                nextNode = nextNode.getParent();
            }
        }
        //printing loop
        for (Point point : route) {
            System.out.println(point.getX() + ", " + point.getY());
        }


        return route;
    }


    /**
     * Calculating the heuristic for calculating the route to the player
     * @param start point
     * @param end point
     * @return void
     */
    private double calculateHeuristic(Point start, Point end) {
        return Math.sqrt(((end.getX() - start.getX()) * (end.getX() - start.getX())) + (end.getY() - start.getY()) * (end.getY() - start.getY()));
    }

    /**
     * Finding the neighbours of a given node, based on the path from start to end
     * to be able to find our way towards the player/end of the path
     * @param parent
     * @param end
     */
    private void findNeighbours(Node parent, Node end) {
        ArrayList<Node> neighbors = new ArrayList<>();
        int index = WIDTH_OF_GRID * (int) parent.getPoint().getY() + (int) parent.getPoint().getX();

        //Linked if-statements for finding neighbours of a given node, based on the math of the grid
        //the AI uses on top of the map
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
        if (index % WIDTH_OF_GRID != 0 && index - WIDTH_OF_GRID >= 0 && !grid[index - WIDTH_OF_GRID - 1]) {
            neighbors.add(createNode(-1, -1, parent, end, DIAGONAL_COST));
        }
        if (index % WIDTH_OF_GRID != 0 && index + WIDTH_OF_GRID <= WIDTH_OF_GRID * HEIGHT_OF_GRID - 1 && !grid[index + WIDTH_OF_GRID - 1]) {
            neighbors.add(createNode(-1, 1, parent, end, DIAGONAL_COST));
        }
        if (index + WIDTH_OF_GRID <= WIDTH_OF_GRID * HEIGHT_OF_GRID - 1 && !grid[index + WIDTH_OF_GRID]) {
            neighbors.add(createNode(0, 1, parent, end, STRAIGHT_COST));
        }
        if (index % WIDTH_OF_GRID != WIDTH_OF_GRID - 1 && index + WIDTH_OF_GRID <= WIDTH_OF_GRID * HEIGHT_OF_GRID - 1 && !grid[index + WIDTH_OF_GRID]) {
            neighbors.add(createNode(1, 1, parent, end, DIAGONAL_COST));
        }

        //Loop for checking wether a node is previously walked on
        for (Node node : neighbors) {
            //If it is not in the previously marked nodes, and is not in the fringe, it is added to the fringe
            if (usedNodes.contains(node)) continue;
            if (!fringe.contains(node)) {
                fringe.add(node);
            //checks for each node in the fringe if there is another node in the fringe that is better
            //compared to the one we are standing on, based on the cost and heuristic of the pathfinding
            } else {
                for (Node temp : fringe) {
                    if (temp.equals(node)) {
                        if (temp.compareTo(node) > 0) {
                            temp.updateNode(node);
                            break;
                        }
                    }
                }
            }
        }
    }

    /**
     * Updates the grid based on the obstacles/objects that are loaded in when a new room is loaded
     * Also places the walls of the room
     * @param world
     * @param gameData
     */
    void updateGrid(World world, GameData gameData) {
        for (Entity entity : world.getEntities()) {
            switch (entity.getEntityType()) {
                case STATIC_OBSTACLE:
                case REMOVABLE_OBSTACLE:
                case WALL:
                    PositionPart positionPart = entity.getPart(PositionPart.class);
                    float radius = entity.getRadius();
                    Point min = toGrid(new Point(positionPart.getX() - radius, positionPart.getY() - radius));
                    Point max = toGrid(new Point(positionPart.getX() + radius, positionPart.getY() + radius));
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


    /**
     * Method for calculating a point to a grid coordinate #quickMaths
     * @param point
     * @return Point
     */
    private Point toGrid(Point point) {
        // TODO If you place an entity off the map, then in the edge there will be a blocked path
        int xGridCoordinate = (int) point.getX() / SIZE_OF_CELL;
        int yGridCoordinate = (int) point.getY() / SIZE_OF_CELL;
        if (xGridCoordinate < 0) {
            xGridCoordinate = 0;
        }
        if (xGridCoordinate >= WIDTH_OF_GRID) {
            xGridCoordinate = WIDTH_OF_GRID - 1;
        }
        if (yGridCoordinate < 0) {
            yGridCoordinate = 0;
        }
        if (yGridCoordinate >= HEIGHT_OF_GRID) {
            yGridCoordinate = HEIGHT_OF_GRID - 1;
        }
        return new Point(xGridCoordinate, yGridCoordinate);
    }

    private Point toGame(int index) {
        int y = index / WIDTH_OF_GRID * SIZE_OF_CELL + SIZE_OF_CELL / 2;
        int x = index % WIDTH_OF_GRID * SIZE_OF_CELL + SIZE_OF_CELL / 2;
        return new Point(x, y);
    }

    /**
     * Creates a node with a heuristic for the AStar to use when needed
     * @param x parents x-value
     * @param y parents y-value
     * @param parent parent-node reference
     * @param end the end-point of the astar
     * @param cost to the goal
     * @return
     */
    private Node createNode(int x, int y, Node parent, Node end, float cost) {
        Point newPoint = new Point(parent.getPoint().getX() + x, parent.getPoint().getY() + y);
        return new Node(newPoint, calculateHeuristic(newPoint, end.getPoint()), cost + parent.getCost(), parent);
    }
}
