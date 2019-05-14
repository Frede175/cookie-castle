package dk.sdu.cookie.castle.map.entities.door;

import dk.sdu.cookie.castle.common.data.Entityparts.PositionPart;
import dk.sdu.cookie.castle.common.util.Vector2f;

public enum DoorPosition {
    TOP(new Vector2f(0, 1)),
    RIGHT(new Vector2f(1, 0)),
    BOTTOM(new Vector2f(0, -1)),
    LEFT(new Vector2f(-1, 0));

    private PositionPart positionPart;
    private Vector2f pointDirection;

    DoorPosition(Vector2f pointDirection) {
        positionPart = new PositionPart(0, 0, 0);
        this.pointDirection = pointDirection;
    }

    public PositionPart getPositionPart() {
        return positionPart;
    }

    /**
     * Set the possible centered door locations and account for game area margin
     *
     * @param displayWidth  Display pixel width
     * @param displayHeight Display pixel height
     */
    public void setPosition(int displayWidth, int displayHeight) {
        int marginX = 136; // This is the margin for left and right edges
        int marginY = 106; // This is the margin for top and bottom edges

        switch (this) {
            case TOP:
                positionPart.setX(displayWidth / 2);
                positionPart.setY(displayHeight - marginY);
                positionPart.setRadians(0);
                break;
            case RIGHT:
                positionPart.setX(displayWidth - marginX);
                positionPart.setY(displayHeight / 2);
                positionPart.setRadians((float) Math.PI * 1.5f);
                break;
            case BOTTOM:
                positionPart.setX(displayWidth / 2);
                positionPart.setY(marginY);
                positionPart.setRadians((float) Math.PI);
                break;
            case LEFT:
                positionPart.setX(marginX);
                positionPart.setY(displayHeight / 2);
                positionPart.setRadians((float) Math.PI / 2);
                break;
        }
    }

    /**
     * Get opposite DoorPosition
     *
     * @return DoorPosition
     */
    public DoorPosition getOpposite() {
        switch (this) {
            case TOP:
                return BOTTOM;
            case RIGHT:
                return LEFT;
            case BOTTOM:
                return TOP;
            case LEFT:
                return RIGHT;
            default:
                throw new AssertionError("DoorPosition error: Could not get opposite");
        }
    }

    public Vector2f getPointDirection() {
        return pointDirection;
    }

    /**
     * Get offset player spawn position
     *
     * @return PositionPart
     */
    public PositionPart getSpawnPosition() {
        int offset = 35;

        switch (this) {
            case TOP:
                return new PositionPart(positionPart.getX(), positionPart.getY() - offset, 0);
            case RIGHT:
                return new PositionPart(positionPart.getX() - offset, positionPart.getY(), 0);
            case BOTTOM:
                return new PositionPart(positionPart.getX(), positionPart.getY() + offset, 0);
            case LEFT:
                return new PositionPart(positionPart.getX() + offset, positionPart.getY(), 0);
            default:
                throw new AssertionError("DoorPosition error: Could not get spawn");
        }
    }
}
