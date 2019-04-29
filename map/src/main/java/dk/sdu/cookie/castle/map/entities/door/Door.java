package dk.sdu.cookie.castle.map.entities.door;

import dk.sdu.cookie.castle.common.data.Entity;
import dk.sdu.cookie.castle.common.data.EntityType;
import dk.sdu.cookie.castle.map.Room;

import static dk.sdu.cookie.castle.common.data.EntityType.STATIC_OBSTACLE;

// Door class, static obstacle,  used to transport player from room to room
// Keeps track of what room it leads to
public class Door extends Entity {
    private Room leadsTo;


    public Door(Room connection) {
        this.leadsTo = connection;
        setShapeX(new float[6]);
        setShapeY(new float[6]);
    }



    //Getters and setter:

    public Room getLeadsTo() {
        return leadsTo;
    }

    public void setLeadsTo(Room leadsTo) {
        this.leadsTo = leadsTo;
    }


}
