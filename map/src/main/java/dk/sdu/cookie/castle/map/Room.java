package dk.sdu.cookie.castle.map;

import dk.sdu.cookie.castle.common.data.Point;
import dk.sdu.cookie.castle.map.entities.door.Door;
import dk.sdu.cookie.castle.map.entities.door.DoorPosition;

import java.util.ArrayList;
import java.util.List;

/**
 * Room class,
 * Used to contain all the entities for the current room in a list
 */
public class Room {
    private List<String> entityList;
    private Point point;
    private List<DoorPosition> exits;

    public void setEntityList(List<String> entityList) {
        this.entityList = entityList;
    }

    List<String> getEntityList() {
        return entityList;
    }

    Room(List<String> entityList) {
        this.entityList = entityList;
        exits = new ArrayList<>();
    }

    public void removeEntity(String entity) {
        entityList.remove(entity);
    }

    void setPoint(Point point) {
        this.point = point;
    }

    Point getPoint() {
        return point;
    }

    boolean checkIfFree(DoorPosition position) {
        return !exits.contains(position);
    }

    void setDoor(Door door) {
        exits.add(door.getPosition());
        entityList.add(door.getID());
    }

    public List<DoorPosition> getExits() {
        return exits;
    }
}
