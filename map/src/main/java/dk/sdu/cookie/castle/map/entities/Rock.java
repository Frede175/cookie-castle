package dk.sdu.cookie.castle.map.entities;

import dk.sdu.cookie.castle.common.data.Entity;
import static dk.sdu.cookie.castle.common.data.EntityType.STATIC_OBSTACLE;

//Rock class, static obstacle
public class Rock extends Entity {

    public Rock(){
        setEntityType(STATIC_OBSTACLE);
    }

}
