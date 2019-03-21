package dk.sdu.cookie.castle.common.data;

public interface ICollidable {
    boolean CollidesWith(ICollidable other);
    void handleCollision(ICollidable other);
    void reactToCollision(ICollidable other);
}
