package dk.sdu.cookie.castle.map;

import dk.sdu.cookie.castle.common.data.Entity;

import java.util.ArrayList;
import java.util.List;

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
}
