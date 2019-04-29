package dk.sdu.cookie.castle.common.data.Entityparts;


import dk.sdu.cookie.castle.common.data.Constants;
import dk.sdu.cookie.castle.common.data.Entity;
import dk.sdu.cookie.castle.common.data.GameData;

public class TimerPart implements EntityPart {

    private boolean isExpired = false;
    private float expiration;

    public TimerPart(float weaponRange) {
        expiration = weaponRange / Constants.BULLET_SPEED;
    }

    public float getExpiration() {
        return expiration;
    }

    public boolean isExpired() {
        return isExpired;
    }

    @Override
    public void process(GameData gameData, Entity entity) {
        if (expiration > 0) {
            expiration -= gameData.getDelta();
        } else {
            isExpired = true;
        }
    }

}
