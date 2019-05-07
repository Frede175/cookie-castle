package dk.sdu.cookie.castle.map.entities.door;

import dk.sdu.cookie.castle.common.data.Entityparts.PositionPart;
import dk.sdu.cookie.castle.common.data.Point;

public enum DoorPosition {
    TOP(new Point(0, 1)),
    RIGHT(new Point(1, 0)),
    BOTTOM(new Point(0, -1)),
    LEFT(new Point(-1, 0));

    private PositionPart positionPart;
    private Point pointDirection;

    DoorPosition(Point pointDirection) {
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
        int margin = 150; // This is the margin in pixels from the edges
        int verticalOffset = 20; // Fix vertical alignment for top and bottom

        switch (this) {
            case TOP:
                positionPart.setX(displayWidth / 2);
                positionPart.setY(displayHeight - margin + verticalOffset);
                positionPart.setRadians(0);
                break;
            case RIGHT:
                positionPart.setX(displayWidth - margin);
                positionPart.setY(displayHeight / 2);
                positionPart.setRadians(1.5708f);
                break;
            case BOTTOM:
                positionPart.setX(displayWidth / 2);
                positionPart.setY(margin - verticalOffset);
                positionPart.setRadians(3.14159f);
                break;
            case LEFT:
                positionPart.setX(margin);
                positionPart.setY(displayHeight / 2);
                positionPart.setRadians(4.71239f);
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

    public Point getPointDirection() {
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
