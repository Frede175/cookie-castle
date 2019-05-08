package dk.sdu.cookie.castle.map;

import dk.sdu.cookie.castle.common.data.EntityType;
import dk.sdu.cookie.castle.common.data.Entityparts.CollisionPart;
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
    private List<String> entityList;
    private Vector2f point;
    private List<DoorPosition> exits;


    public void setEntityList(List<String> entityList) {
        this.entityList = entityList;
    }

    public List<String> getEntityList() {
        return entityList;
    }

    public Room(List<String> entityList) {
        this.entityList = entityList;
        exits = new ArrayList<>();
    }

    public void removeEntity(String entity) {
        entityList.remove(entity);
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public Point getPoint() {
        return point;
    }

    public boolean checkIfFree(DoorPosition position) {
        return !exits.contains(position);
    }

    public Door setDoor(DoorPosition position, Room room) {
        exits.add(position);
        Door door = new Door(room);
        door.setRadius(26);
        door.add(position.getPositionPart());
        door.add(new CollisionPart());
        door.setEntityType(EntityType.DOOR);
        entityList.add(door.getID());
        return door;
    }

    public List<DoorPosition> getExits() {
        return exits;
    }
}
