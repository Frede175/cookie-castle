package dk.sdu.cookie.castle.common.data.Entityparts;

import dk.sdu.cookie.castle.common.data.Entity;
import dk.sdu.cookie.castle.common.data.GameData;

public class CollisionPart implements EntityPart {

    private Entity collidingEntity;
    private boolean hit;

    public Entity getCollidingEntity() {
        return collidingEntity;
    }

    public boolean getHit() {
        return hit;
    }

    public void setCollidingEntity(Entity collidingEntity) {
        this.collidingEntity = collidingEntity;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
        if (!hit) {
            collidingEntity = null;
        }
    }

    @Override
    public void process(GameData gameData, Entity entity) {
    }
}
