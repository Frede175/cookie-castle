package dk.sdu.cookie.castle.common.data.Entityparts;

import dk.sdu.cookie.castle.common.data.Entity;
import dk.sdu.cookie.castle.common.data.GameData;

public class ShootingPart implements EntityPart{

    boolean isShooting;
    public String ID;

    public ShootingPart(String ID) {
        this.ID = ID;
    }

    public boolean isShooting() {
        return this.isShooting;
    }

    public void setShooting(boolean shooting) {
        isShooting = shooting;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    @Override
    public void process(GameData gameData, Entity entity) {

    }
}
