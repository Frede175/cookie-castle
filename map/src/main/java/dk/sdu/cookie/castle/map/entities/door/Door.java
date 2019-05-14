package dk.sdu.cookie.castle.map.entities.door;

import dk.sdu.cookie.castle.common.data.Entity;
import dk.sdu.cookie.castle.common.data.Entityparts.CollisionPart;
import dk.sdu.cookie.castle.map.Room;

import static dk.sdu.cookie.castle.common.data.EntityType.DOOR;

// Door class, static obstacle,  used to transport player from room to room
// Keeps track of what room it leads to
public class Door extends Entity {
    private Room leadsTo;
    private DoorPosition position;

    public Door(DoorPosition position, Room connection) {
        this.position = position;
        super.add(position.getPositionPart());
        super.add(new CollisionPart());
        super.setEntityType(DOOR);
        this.leadsTo = connection;
        setShapeX(new float[6]);
        setShapeY(new float[6]);
    }

    public Room getLeadsTo() {
        return leadsTo;
    }

    public void setLeadsTo(Room leadsTo) {
        this.leadsTo = leadsTo;
    }

    public DoorPosition getPosition() {
        return position;
    }
}
