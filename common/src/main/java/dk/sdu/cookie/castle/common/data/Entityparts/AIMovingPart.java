package dk.sdu.cookie.castle.common.data.Entityparts;

import dk.sdu.cookie.castle.common.data.Entity;
import dk.sdu.cookie.castle.common.data.GameData;
import dk.sdu.cookie.castle.common.data.Point;
import dk.sdu.cookie.castle.common.util.Vector2f;

import java.util.LinkedList;

public class AIMovingPart implements EntityPart {

    private final float UPDATE_TIME = 0.5f;

    private float speed;
    private LinkedList<Point> route;
    private boolean update = true;

    private float nextUpdate = UPDATE_TIME;

    public boolean needUpdate() {
        return update;
    }

    public void setRoute(LinkedList<Point> route) {
        update = false;
        this.route = route;
    }

    public AIMovingPart(float maxSpeed) {
        speed = maxSpeed;
    }

    @Override
    public void process(GameData gameData, Entity entity) {
        if (!update) {
            nextUpdate -= gameData.getDelta();
            if (nextUpdate <= 0) {
                nextUpdate = UPDATE_TIME;
                update = true;
            }
        }


        if (route.isEmpty()) {
            return;
        }

        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();

        Vector2f to = new Vector2f(route.getFirst().getX(), route.getFirst().getY());

        Vector2f pos = new Vector2f(x, y);

        Vector2f delta = to.subtract(pos).normalize().mult(speed * gameData.getDelta());

        x += delta.getX();
        y += delta.getY();

        if (Math.abs(x - to.getX()) <= speed && Math.abs(y - to.getY()) <= speed) {
            route.removeFirst();
        }

        positionPart.setPosition(x, y);
    }

}
