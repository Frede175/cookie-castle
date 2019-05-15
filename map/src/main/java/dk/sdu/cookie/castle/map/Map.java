package dk.sdu.cookie.castle.map;

import dk.sdu.cookie.castle.common.data.EntityType;
import dk.sdu.cookie.castle.common.data.Entityparts.CollisionPart;
import dk.sdu.cookie.castle.common.data.Entityparts.PositionPart;
import dk.sdu.cookie.castle.common.data.World;
import dk.sdu.cookie.castle.common.enemy.EnemyType;
import dk.sdu.cookie.castle.common.enemy.IEnemyCreate;
import dk.sdu.cookie.castle.common.item.IItemCreate;
import dk.sdu.cookie.castle.common.item.ItemType;
import dk.sdu.cookie.castle.common.util.Vector2f;
import dk.sdu.cookie.castle.map.entities.EntityPreset;
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
    private static IItemCreate itemCreate;

    private RoomPresetGenerator roomPresetGenerator;

    private List<Room> listOfRooms;
    private Room currentRoom;

    // Singleton
    static Map getInstance() {
        if (map == null) {
            map = new Map();
        }
        return map;
    }

    // This somehow needs to be public
    public Map() {
        listOfRooms = new ArrayList<>();
        roomPresetGenerator = new RoomPresetGenerator();
    }

    void setCurrentRoom(Room room) {
        currentRoom = room;
    }

    Room getCurrentRoom() {
        return currentRoom;
    }

    List<Room> getListOfRooms() {
        return listOfRooms;
    }

    public void setListOfRooms(List<Room> listOfRooms) {
        this.listOfRooms = listOfRooms;
    }

    private ArrayList<Room> createRooms(int roomCount, World world) {
        ArrayList<Room> rooms = new ArrayList<>();

        for (int i = 0; i < roomCount; i++) {
            RoomPreset roomPreset = roomPresetGenerator.getRandomRoomPreset();
            rooms.add(createRoom(roomPreset, world));
        }

        return rooms;
    }

    private Room createRoom(RoomPreset preset, World world) {
        List<String> entities = createEntities(preset.getEntityPositions(), world);

        return new Room(entities);
    }

    private List<String> createEntities(java.util.Map<EntityPreset, List<PositionPart>> entities, World world) {
        List<String> returnEntities = new ArrayList<>();

        for (java.util.Map.Entry<EntityPreset, List<PositionPart>> presetListEntry : entities.entrySet()) {
            for (PositionPart position : presetListEntry.getValue()) {
                returnEntities.add(createEntity(presetListEntry.getKey(), position, world));
            }
        }

        return returnEntities;
    }

    private String createEntity(EntityPreset entityPreset, PositionPart position, World world) {
        String entityId = null;

        switch (entityPreset) {
            case ENEMY:
                if (enemyCreate == null) break;

                EnemyType enemyType = EnemyType.values()[(int) (Math.random() * EnemyType.values().length)];
                entityId = enemyCreate.createEnemy(position.getX(), position.getY(), enemyType, world).getID();
                break;
            case ITEM:
                if (itemCreate == null) break;

                ItemType itemType = ItemType.values()[(int) (Math.random() * ItemType.values().length)];
                entityId = itemCreate.createItem(position.getX(), position.getY(), itemType, world).getID();
                break;
            case ROCK:
                Rock rock = createRock(position.getX(), position.getY());
                world.addEntity(rock);
                entityId = rock.getID();
                break;
        }

        return entityId;
    }

    private Rock createRock(float x, float y) {
        float[] shapeX = new float[8];
        float[] shapeY = new float[8];
        float radians = 3.1415f * (float) Math.random();

        Rock rock = new Rock();

        rock.add(new PositionPart(x, y, radians));
        rock.add(new CollisionPart());
        rock.setEntityType(EntityType.STATIC_OBSTACLE);
        rock.setCurrentTexture(MapPlugin.getAssetId("rock"));
        rock.setShapeY(shapeY);
        rock.setShapeX(shapeX);

        return rock;
    }

    void generateMap(int numberOfRooms, World world) {
        // Creates the ArrayList that contains all the free rooms.
        ArrayList<Room> freeRooms = createRooms(numberOfRooms, world);

        listOfRooms.addAll(freeRooms);

        DoorPosition[] doorPositions = DoorPosition.values();

        // Sets the start room to the first free room.
        Room startRoom = freeRooms.get(0);
        startRoom.setPoint(new Vector2f(0, 0));
        // Creates the queue where all the rooms that needs to be processed is stored.
        Queue<Room> roomsToProcess = new LinkedList<>();
        // Adds the first free room to the queue.
        roomsToProcess.add(freeRooms.remove(0));
        // used to hold the used coordinates, for rooms not to have duplicate coordinates.
        Set<Vector2f> usedPoints = new HashSet<>();
        usedPoints.add(new Vector2f(0, 0));
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
                DoorPosition doorPosition = doorPositions[index];
                // Calculates the opposite direction.
                DoorPosition oppositeDirection = doorPosition.getOpposite();
                // Random selecting the neighbor room.
                int neighbor = (int) (Math.random() * freeRooms.size());
                // If the room doesn't have an exit at that direction
                Vector2f p = currentRoom.getPoint().add(doorPosition.getPointDirection());
                if (currentRoom.checkIfFree(doorPosition) && !usedPoints.contains(p)) {
                    Door currentRoomDoor = new Door(doorPosition, freeRooms.get(neighbor));
                    currentRoomDoor.setCurrentTexture(MapPlugin.getAssetId("door"));
                    currentRoom.setDoor(currentRoomDoor);
                    world.addEntity(currentRoomDoor);
                    // sets coordinates to every free room.
                    freeRooms.get(neighbor).setPoint(p);
                    usedPoints.add(p);
                    // Sets the neighbor rooms exit to be the current room.
                    Door neighborRoomDoor = new Door(oppositeDirection, currentRoom);
                    neighborRoomDoor.setCurrentTexture(MapPlugin.getAssetId("door"));
                    freeRooms.get(neighbor).setDoor(neighborRoomDoor);

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

    boolean isEnemyPluginActive() {
        return enemyCreate != null;
    }

    boolean isItemPluginActive() {
        return itemCreate != null;
    }

    public void installEnemyCreate(IEnemyCreate iEnemyCreate) {
        enemyCreate = iEnemyCreate;
    }

    public void uninstallEnemyCreate() {
        enemyCreate = null;
    }

    public void installItemCreate(IItemCreate iItemCreate) {
        itemCreate = iItemCreate;
    }

    public void uninstallItemCreate() {
        itemCreate = null;
    }
}
