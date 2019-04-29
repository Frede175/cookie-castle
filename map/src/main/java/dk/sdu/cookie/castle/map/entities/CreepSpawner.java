package dk.sdu.cookie.castle.map.entities;

import dk.sdu.cookie.castle.common.data.Entity;
import static dk.sdu.cookie.castle.common.data.EntityType.REMOVABLE_OBSTACLE;

public class CreepSpawner extends Entity {

    public CreepSpawner(){
        setEntityType(REMOVABLE_OBSTACLE);
    }
}
