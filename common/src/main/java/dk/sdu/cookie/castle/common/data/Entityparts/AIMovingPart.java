package dk.sdu.cookie.castle.common.data.Entityparts;

import dk.sdu.cookie.castle.common.data.Entity;
import dk.sdu.cookie.castle.common.data.GameData;
import dk.sdu.cookie.castle.common.data.Point;
import dk.sdu.cookie.castle.common.util.Vector2f;

import java.util.LinkedList;

public class AIMovingPart implements EntityPart {

    private final float UPDATE_TIME = 0.5f;

    private float speed;
    private LinkedList<Point> route = new LinkedList<>();
    private boolean update = true;
    private boolean shouldMove = true;

    private float nextUpdate = UPDATE_TIME;

    public boolean needUpdate() {
        return update;
    }

    public void setRoute(LinkedList<Point> route) {
        update = false;
        this.route = route;
    }

    public void setShouldMove(boolean shouldMove) {
        this.shouldMove = shouldMove;
    }

    public AIMovingPart(float maxSpeed) {
        speed = maxSpeed;
    }

    @Override
    public void process(GameData gameData, Entity entity) {
        if (shouldMove) {
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

            float deltaSpeed = speed * gameData.getDelta();

            PositionPart positionPart = entity.getPart(PositionPart.class);
            float x = positionPart.getX();
            float y = positionPart.getY();

            Vector2f to = new Vector2f(route.getFirst().getX(), route.getFirst().getY());

            while (Math.abs(x - to.getX()) <= deltaSpeed && Math.abs(y - to.getY()) <= deltaSpeed) {
                route.removeFirst();
                if (route.isEmpty()) return;
                to = new Vector2f(route.getFirst().getX(), route.getFirst().getY());
            }

            Vector2f pos = new Vector2f(x, y);

            Vector2f delta = to.subtract(pos).normalize().mult(deltaSpeed);

            x += delta.getX();
            y += delta.getY();

            positionPart.setRadians((float) Math.atan2(delta.getY(), delta.getX()));

            positionPart.setPosition(x, y);
        }
    }
}
