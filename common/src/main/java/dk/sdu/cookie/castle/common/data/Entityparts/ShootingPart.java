package dk.sdu.cookie.castle.common.data.Entityparts;

import dk.sdu.cookie.castle.common.data.Entity;
import dk.sdu.cookie.castle.common.data.GameData;

public class ShootingPart implements EntityPart{

    boolean isShooting;

    public void setShooting(boolean shooting) {
        isShooting = shooting;
    }
    public boolean isShooting() {
        return this.isShooting;
    }
    
    @Override
    public void process(GameData gameData, Entity entity) {

    }
}
