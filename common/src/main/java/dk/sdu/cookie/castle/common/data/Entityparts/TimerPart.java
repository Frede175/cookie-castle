package dk.sdu.cookie.castle.common.data.Entityparts;


import dk.sdu.cookie.castle.common.data.Entity;
import dk.sdu.cookie.castle.common.data.GameData;

public class TimerPart implements EntityPart {

    private boolean isExpired = false;
    private float expiration;

    public TimerPart(float weaponRange) {
        expiration = conversion(weaponRange);
    }

    public float getExpiration() {
        return expiration;
    }

    public void setExpiration(float expiration) {
        this.expiration = expiration;
    }

    public void reduceExpiration(float delta) {
        this.expiration -= delta;
    }

    public boolean isExpired() {
        return isExpired;
    }

    public float conversion(float convertable) {
        return  convertable/30;
    }

    @Override
    public void process(GameData gameData, Entity entity) {
        if (expiration > 0) {
            reduceExpiration(gameData.getDelta());
            System.out.println(gameData.getDelta());
        } else {
            isExpired = true;
        }
    }

}
