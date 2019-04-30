package dk.sdu.cookie.castle.common.data.Entityparts;

import dk.sdu.cookie.castle.common.data.Entity;
import dk.sdu.cookie.castle.common.data.GameData;

public class TimerPart implements EntityPart {


    private boolean isExpired = false;

    /**
     * Duration in seconds
     */
    private float duration;

    public TimerPart(float duration) {
        this.duration = duration;
    }

    public float getDuration() {
        return duration;
    }

    public boolean isExpired() {
        return isExpired;
    }

    @Override
    public void process(GameData gameData, Entity entity) {
        if (duration > 0) {
            duration -= gameData.getDelta();
            System.out.println("Duration: " + duration);
        } else {
            isExpired = true;
        }
    }
}
