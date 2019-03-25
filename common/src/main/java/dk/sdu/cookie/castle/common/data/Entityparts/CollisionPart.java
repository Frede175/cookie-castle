package dk.sdu.cookie.castle.common.data.Entityparts;

import dk.sdu.cookie.castle.common.data.Entity;
import dk.sdu.cookie.castle.common.data.GameData;

public class CollisionPart implements EntityPart {

    private Entity collidingEntity;
    private Boolean isHit;

    public Entity getCollidingEntity() {
        return collidingEntity;
    }

    public Boolean getHit() {
        return isHit;
    }

    public void setCollidingEntity(Entity collidingEntity) {
        this.collidingEntity = collidingEntity;
    }

    public void setHit(Boolean hit) {
        isHit = hit;
        if (!isHit) {
            collidingEntity = null;
        }
    }

    @Override
    public void process(GameData gameData, Entity entity) {
    }
}
