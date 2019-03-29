package dk.sdu.cookie.castle.common.data.Entityparts;

import dk.sdu.cookie.castle.common.data.Direction;
import dk.sdu.cookie.castle.common.data.Entity;
import dk.sdu.cookie.castle.common.data.GameData;
import dk.sdu.cookie.castle.common.data.Point;

import java.util.LinkedList;

import static java.lang.Math.sqrt;

public class AIMovingPart implements EntityPart {

    private float speed;

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    private Direction direction;
    private LinkedList<Point> route;

    public AIMovingPart(float maxSpeed) {
        this.speed = maxSpeed;
    }

    @Override
    public void process(GameData gameData, Entity entity) {
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();

        float deltaX = 0;
        float deltaY = 0;

        switch (direction) {
            case UP:
                deltaY += speed;
                break;
            case DOWN:
                deltaY -= speed;
                break;
            case LEFT:
                deltaX -= speed;
                break;
            case RIGHT:
                deltaX += speed;
                break;
            case UP_LEFT:
                deltaY += speed;
                deltaX -= speed;
                break;
            case UP_RIGHT:
                deltaY += speed;
                deltaX += speed;
                break;
            case DOWN_LEFT:
                deltaY -= speed;
                deltaX -= speed;
                break;
            case DOWN_RIGHT:
                deltaY -= speed;
                deltaX += speed;
                break;
            default:
                System.out.println("No direction");
                break;
        }

        if(direction == Direction.UP_LEFT || direction == Direction.UP_RIGHT || direction == Direction.DOWN_LEFT || direction == Direction.DOWN_RIGHT) {
            deltaX = (deltaX/ (float) sqrt((deltaX*deltaX) + (deltaY*deltaY))) * speed;
            deltaY = (deltaY/ (float) sqrt((deltaX*deltaX) + (deltaY*deltaY))) * speed;
        }

        x += deltaX;
        y += deltaY;

        positionPart.setPosition(x, y);
    }

}
