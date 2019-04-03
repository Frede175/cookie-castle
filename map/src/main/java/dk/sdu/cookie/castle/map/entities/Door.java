package dk.sdu.cookie.castle.map.entities;

import dk.sdu.cookie.castle.common.data.Entity;
import dk.sdu.cookie.castle.common.data.EntityType;
import dk.sdu.cookie.castle.map.Room;

import static dk.sdu.cookie.castle.common.data.EntityType.STATIC_OBSTACLE;

public class Door extends Entity {
    EntityType type = STATIC_OBSTACLE;
    Room leadsTo;


    public Door(Room connection) {
        this.leadsTo = connection;
    }



    //Getters and setter:

    public EntityType getType() {
        return type;
    }

    public void setType(EntityType type) {
        this.type = type;
    }

    public Room getLeadsTo() {
        return leadsTo;
    }

    public void setLeadsTo(Room leadsTo) {
        this.leadsTo = leadsTo;
    }
}
