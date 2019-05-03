package dk.sdu.cookie.castle.map;

import dk.sdu.cookie.castle.common.data.EntityType;
import dk.sdu.cookie.castle.common.data.Entityparts.*;
import dk.sdu.cookie.castle.common.data.Point;
import dk.sdu.cookie.castle.common.data.World;
import dk.sdu.cookie.castle.common.enemy.EnemyType;
import dk.sdu.cookie.castle.common.enemy.IEnemyCreate;
import dk.sdu.cookie.castle.map.entities.Rock;
import dk.sdu.cookie.castle.map.entities.door.Door;
import dk.sdu.cookie.castle.map.entities.door.DoorPosition;

import java.util.*;

/**
 * Map class (Singleton)
 * Used to keep track of all the rooms on a specific level
 * Also keeps track of the current room the player is in
 */
public class Map {
    private static Map map = null;

    private static IEnemyCreate enemyCreate;

    private RoomPresetGenerator roomPresetGenerator;

    private List<Room> listOfRooms;
    private Room currentRoom;

    //Singleton
    public static Map getInstance() {
        if (map == null) {
            map = new Map();
        }
        return map;
    }

    public Map() {
        listOfRooms = new ArrayList<>();
        roomPresetGenerator = new RoomPresetGenerator();
    }

    public void setCurrentRoom(Room room) {
        currentRoom = room;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public List<Room> getListOfRooms() {
        return listOfRooms;
    }

    public void setListOfRooms(List<Room> listOfRooms) {
        this.listOfRooms = listOfRooms;
    }

    private ArrayList<Room> createRooms(int roomCount, World world) {
        ArrayList<Room> rooms = new ArrayList<>();
        for (int i = 0; i < roomCount; i++) {
            List<String> entityList = new ArrayList<>();
            RoomPreset roomPreset = roomPresetGenerator.getRandomRoomPreset();
            if (enemyCreate != null) {
                for (PositionPart positionPart : roomPreset.getEnemyPositions()) {
                    // TODO add random enemy type
                    entityList.add(enemyCreate.createEnemy(positionPart.getX(), positionPart.getY(), EnemyType.RANGED, world));
                }
            }
            // TODO do the same as enemyCreate with itemCreate
            for (PositionPart positionPart : roomPreset.getRockPositions()) {
                Rock rock = createRock(positionPart.getX(), positionPart.getY());
                world.addEntity(rock);
                entityList.add(rock.getID());
            }
            Room room = new Room(entityList);
            rooms.add(room);
        }
        return rooms;
    }

    private Rock createRock(float x, float y) {
        float[] shapeX = new float[6];
        float[] shapeY = new float[6];
        float radians = 3.1415f / 2;

        Rock rock = new Rock();
        rock.setRadius(15);
        rock.add(new PositionPart(x, y, radians));
        rock.add(new CollisionPart());
        rock.setEntityType(EntityType.STATIC_OBSTACLE);

        rock.setShapeY(shapeY);
        rock.setShapeX(shapeX);

        return rock;
    }

    public void generateMap(int numberOfRooms, World world) {
        // Creates the ArrayList that contains all the free rooms.
        ArrayList<Room> freeRooms = createRooms(numberOfRooms, world);

        listOfRooms.addAll(freeRooms);

        DoorPosition[] doorPositions = DoorPosition.values();

        // Sets the start room to the first free room.
        Room startRoom = freeRooms.get(0);
        startRoom.setPoint(new Point(0, 0));
        // Creates the queue where all the rooms that needs to be processed is stored.
        Queue<Room> roomsToProcess = new LinkedList<>();
        // Adds the first free room to the queue.
        roomsToProcess.add(freeRooms.remove(0));
        // used to hold the used coordinates, for rooms not to have duplicate coordinates.
        Set<Point> usedPoints = new HashSet<>();
        usedPoints.add(new Point(0, 0));
        // As long as the queue is not empty.
        while (!roomsToProcess.isEmpty()) {
            // Gets the next room in the queue.
            Room currentRoom = roomsToProcess.poll();
            int i = 0;
            // Generates a random number of exits the room has.
            int exitCount = (int) (Math.random() * 4) + 1;
            // As long as there is free rooms left and the room needs more exits.
            while (i <= exitCount && !freeRooms.isEmpty()) {
                // Generates the random direction.
                int index = (int) (Math.random() * 4);
                DoorPosition direction = doorPositions[index];
                // Calculates the opponent direction.
                DoorPosition oppoDirection = doorPositions[(index + 2) % 4];
                // Random selecting the neighbor room.
                int neighbor = (int) (Math.random() * freeRooms.size());
                // If the room dosent have an exit at that direction
                Point p = Point.add(currentRoom.getPoint(), getPointDirection(direction));
                if (currentRoom.checkIfFree(direction) && !usedPoints.contains(p)) {
                    Door currentRoomDoor = currentRoom.setDoor(direction, freeRooms.get(neighbor));
                    world.addEntity(currentRoomDoor);
                    // sets coordinates to every freeroom.
                    freeRooms.get(neighbor).setPoint(p);
                    usedPoints.add(p);
                    // Sets the neighbor rooms exit to be the current room.
                    Door neighborRoomDoor = freeRooms.get(neighbor).setDoor(oppoDirection, currentRoom);
                    world.addEntity(neighborRoomDoor);
                    // Adds the neighbor room to the queue.
                    roomsToProcess.add(freeRooms.get(neighbor));
                    // Remove the neighbor room from the free rooms ArrayList.
                    freeRooms.remove(freeRooms.get(neighbor));

                }
                i++;
            }
        }

    }

    private Point getPointDirection(DoorPosition d) {
        switch (d) {
            case BOTTOM:
                return new Point(0, -1);
            case TOP:
                return new Point(0, 1);
            case RIGHT:
                return new Point(1, 0);
            case LEFT:
                return new Point(-1, 0);
            default:
                throw new AssertionError(d.name());

        }

    }

    public void installEnemyCreate(IEnemyCreate iEnemyCreate) {;
        enemyCreate = iEnemyCreate;
    }

    public void uninstallEnemyCreate() {
        this.enemyCreate = null;
    }
}
