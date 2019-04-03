package dk.sdu.cookie.castle.common.data.Entityparts;

import dk.sdu.cookie.castle.common.data.Entity;
import dk.sdu.cookie.castle.common.data.GameData;

public class ShootingPart implements EntityPart{

    boolean isShooting;
    boolean canShoot;
    private float nextShoot;
    private float UPDATE_TIME;
    private String ID;

    public ShootingPart(String ID, float attackspeed) {
        canShoot = true;
        this.ID = ID;
        UPDATE_TIME = 1/attackspeed;
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

    public void setCanShoot(boolean canShoot) {
        this.canShoot = canShoot;
    }

    public boolean isCanShoot() {
        return canShoot;
    }

    @Override
    public void process(GameData gameData, Entity entity) {
        if (!canShoot) {
            nextShoot -= gameData.getDelta();
            if (nextShoot <= 0) {
                nextShoot = UPDATE_TIME;
                canShoot = true;
            }
        }
    }
}
