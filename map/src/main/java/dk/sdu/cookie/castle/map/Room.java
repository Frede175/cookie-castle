package dk.sdu.cookie.castle.map;

import dk.sdu.cookie.castle.common.data.Entity;

import java.util.ArrayList;
import java.util.List;



/**
 * Room class,
 * Used to contain all the entities for the current room in a list
 */
public class Room {
    private List<Entity> entityList;

    public void setEntityList(List<Entity> entityList) {
        this.entityList = entityList;
    }

    public List<Entity> getEntityList() {
        return entityList;
    }

    public Room(List<Entity> entityList) {
        this.entityList = entityList;
    }

    public void removeEntity(Entity entity) {
        entityList.remove(entity);
    }
}
