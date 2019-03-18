package dk.sdu.cookie.castle.common.data.Entityparts;


import dk.sdu.cookie.castle.common.data.Entity;
import dk.sdu.cookie.castle.common.data.GameData;

/**
 *
 * @author Alexander
 */
public class PositionPart implements EntityPart {

    private float x;
    private float y;

    public PositionPart(float x, float y) {
        this.x = x;
        this.y = y;

    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    
    public void setX(float newX) {
        this.x = newX;
    }
    
    public void setY(float newY) {
        this.y = newY;
    }

    public void setPosition(float newX, float newY) {
        this.x = newX;
        this.y = newY;
    }

    @Override
    public void process(GameData gameData, Entity entity) {
    }
    
    
    
    
}