package dk.sdu.cookie.castle.common.data.Entityparts;

import dk.sdu.cookie.castle.common.data.Entity;
import dk.sdu.cookie.castle.common.data.GameData;

public class ShootingPart implements EntityPart {

    private boolean isShooting;
    private boolean canShoot;
    private float nextShot;
    private float updateTime;

    public ShootingPart(float attackSpeed) {
        updateTime = 1 / attackSpeed;
        nextShot = 0;
    }

    public boolean isShooting() {
        return isShooting;
    }

    public void setShooting(boolean shooting) {
        isShooting = shooting;
    }

    public void setCanShoot(boolean canShoot) {
        this.canShoot = canShoot;
    }

    public boolean canShoot() {
        return canShoot;
    }

    /**
     * Updates the updateTime variable to the new attackspeed. Resulting in different lengths between the shots.
     *
     * @param attackSpeed: The attack speed of the weapon in rounds per second.
     */
    public void updateShootingSpeed(float attackSpeed) {
        updateTime = 1 / attackSpeed;
    }

    @Override
    public void process(GameData gameData, Entity entity) {
        if (!canShoot) {
            nextShot -= gameData.getDelta();
            if (nextShot <= 0) {
                nextShot = updateTime;
                canShoot = true;
            }
        }
    }
}
