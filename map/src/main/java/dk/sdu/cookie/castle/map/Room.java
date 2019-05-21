package dk.sdu.cookie.castle.map;

import dk.sdu.cookie.castle.common.data.Entity;
import dk.sdu.cookie.castle.common.util.Vector2f;
import dk.sdu.cookie.castle.map.entities.door.Door;
import dk.sdu.cookie.castle.map.entities.door.DoorPosition;

import java.util.ArrayList;
import java.util.List;

/**
 * Room class,
 * Used to contain all the entities for the current room in a list
 */
public class Room {
    private List<String> entities;
    private Vector2f point;
    private List<DoorPosition> exits;
    private RoomPreset roomPreset;

    Room(List<String> entities, RoomPreset roomPreset) {
        this.entities = entities;
        exits = new ArrayList<>();
        this.roomPreset = roomPreset;
    }

    List<String> getEntities() {
        return entities;
    }

    void setEntities(List<String> entities) {
        this.entities = entities;
    }

    public void addEntity(String entity) {
        entities.add(entity);
    }

    public void removeEntity(String entity) {
        entities.remove(entity);
    }

    void setPoint(Vector2f point) {
        this.point = point;
    }

    Vector2f getPoint() {
        return point;
    }

    boolean checkIfFree(DoorPosition position) {
        return !exits.contains(position);
    }

    void setDoor(Door door) {
        exits.add(door.getPosition());
        addEntity(door.getID());
    }

    public List<DoorPosition> getExits() {
        return exits;
    }

    public RoomPreset getRoomPreset() {
        return roomPreset;
    }
}
