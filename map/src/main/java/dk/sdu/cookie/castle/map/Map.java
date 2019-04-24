package dk.sdu.cookie.castle.map;

import dk.sdu.cookie.castle.common.data.Direction;
import dk.sdu.cookie.castle.common.data.Entity;

import java.util.*;

/**
 * Map class (Singleton)
 * Used to keep track of all the rooms on a specific level
 * Also keeps track of the current room the player is in
 */
public class Map {

    private static Map map = null;

    private List<Room> listOfRooms;
    private Room currentRoom;

    //Singleton
    public static Map getInstance() {
        if (map == null) {
            map = new Map();
        }
        return map;
    }

    private Map() {
        listOfRooms = new ArrayList<>();
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

    private ArrayList<Room> createRooms(int roomCount) {
        ArrayList<Room> rooms = new ArrayList<>();
        for (int i = 0; i < roomCount; i++) {
            //TODO fix null
            List<Entity> entityList = null;
            Room room = new Room(entityList);
            rooms.add(room);
        }
        return rooms;
    }

    public void generateMap(int numberOfRooms) {


/*
        // Creates the ArrayList that contains all the free rooms.
        ArrayList<Room> freeRooms = createRooms(roomCount);

        rooms.addAll(freeRooms);

        Direction[] directions = Direction.values();

        // Sets the start room to the first free room.
        Room startRoom = freeRooms.get(0);
        startRoom.setInspected();
        startRoom.setCoordinate(new Coordinate(0, 0));
        // Creates the queue where all the rooms that needs to be processed is stored.
        Queue<Room> roomsToProcess = new LinkedList<>();
        // Adds the first free room to the queue.
        roomsToProcess.add(freeRooms.remove(0));
        // used to hold the used coordinates, for rooms not to have duplicate coordinates.
        Set<ICoordinate> usedCoordinates = new HashSet<>();
        usedCoordinates.add(new Coordinate(0, 0));
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
                Direction direction = directions[index];
                // Calculates the opponent direction.
                Direction oppoDirection = directions[(index + 2) % 4];
                // Random selecting the neighbor room.
                int neighbor = (int) (Math.random() * freeRooms.size());
                // If the room dosent have an exit at that direction
                Coordinate c = Coordinate.add((Coordinate) currentRoom.getCoordinate(), getCoordinateDirection(direction));
                if (currentRoom.getExit(direction) == null && !usedCoordinates.contains(c)) {
                    // Sets an exit with the direction and the neighbor.
                    currentRoom.setExit(direction, freeRooms.get(neighbor));
                    // sets coordinates to every freeroom.
                    freeRooms.get(neighbor).setCoordinate((Coordinate) c);
                    usedCoordinates.add(c);
                    // Sets the neighbor rooms exit to be the current room.
                    freeRooms.get(neighbor).setExit(oppoDirection, currentRoom);
                    // Adds the neighbor room to the queue.
                    roomsToProcess.add(freeRooms.get(neighbor));
                    // Remove the neighbor room from the free rooms ArrayList.
                    freeRooms.remove(freeRooms.get(neighbor));

                }
                i++;
            }
        }
*/
    }
}
