package dk.sdu.cookie.castle.common.data.Entityparts;

import dk.sdu.cookie.castle.common.data.Entity;
import dk.sdu.cookie.castle.common.data.GameData;
import dk.sdu.cookie.castle.common.util.Vector2f;

public class BulletMovingPart implements EntityPart {


    private float speed = 10;

    @Override
    public void process(GameData gameData, Entity entity) {
        PositionPart positionPart = entity.getPart(PositionPart.class);

        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();

        Vector2f vector2f = new Vector2f(radians).mult(speed);

        x += vector2f.getX();
        y += vector2f.getY();

        positionPart.setPosition(x,y);
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
}
