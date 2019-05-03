package dk.sdu.cookie.castle.common.data.Entityparts;


import dk.sdu.cookie.castle.common.data.Constants;
import dk.sdu.cookie.castle.common.data.Entity;
import dk.sdu.cookie.castle.common.data.GameData;

public class TimerPart implements EntityPart {

    private boolean isExpired = false;
    private float duration;
    private boolean hasStarted = false;

    public TimerPart(float weaponRange) {
        duration = weaponRange / Constants.BULLET_SPEED;
    }

    public float getDuration() {
        return duration;
    }

    public boolean isExpired() {
        return isExpired;
    }

    public void setHasStarted(boolean hasStarted) {
        this.hasStarted = hasStarted;
    }

    @Override
    public void process(GameData gameData, Entity entity) {
        if (hasStarted) {
            if (duration > 0) {
                duration -= gameData.getDelta();
                System.out.println("Duration: " + duration);
            } else {
                isExpired = true;
            }
        }
    }
}
