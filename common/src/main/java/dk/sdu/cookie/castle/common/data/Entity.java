package dk.sdu.cookie.castle.common.data;

import dk.sdu.cookie.castle.common.assets.Asset;
import dk.sdu.cookie.castle.common.data.Entityparts.EntityPart;
import dk.sdu.cookie.castle.common.assets.AssetLoader;
import dk.sdu.cookie.castle.common.util.Vector2f;

import java.io.Serializable;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Entity class
 * All classes/elements in the game that have to be visually shown in the game are a subclass of Entity.
 * This is the super-class to all "objects" in the game, and therefore contains a list of Entity-parts
 * The entityParts are specifically given to each subclass in their specific plugin.
 */
public class Entity implements Serializable {
    private final UUID ID = UUID.randomUUID();
    private boolean isActive = false;

    private float[] shapeX = new float[4];
    private float[] shapeY = new float[4];
    private Vector2f min, max;
    private Map<Class, EntityPart> parts;
    private EntityType entityType;
    private String currentTextureId;

    public Entity() {
        parts = new ConcurrentHashMap<>();
        //updateMinMax();
    }

    public void add(EntityPart part) {
        parts.put(part.getClass(), part);
    }

    public void remove(Class partClass) {
        parts.remove(partClass);
    }

    public <E extends EntityPart> E getPart(Class partClass) {
        return (E) parts.get(partClass);
    }

    public Vector2f getMax() {
        return max;
    }

    public Vector2f getMin() {
        return min;
    }

    public void updateMinMax() {
        float minX = Float.POSITIVE_INFINITY, minY = Float.POSITIVE_INFINITY, maxX = Float.NEGATIVE_INFINITY, maxY = Float.NEGATIVE_INFINITY;

        for (int i = 0; i < shapeX.length; i++) {
            float x = shapeX[i];
            float y = shapeY[i];
            if (x < minX) minX = x;
            if (x > maxX) maxX = x;
            if (y < minY) minY = y;
            if (y > maxY) maxY = y;
        }

        min = new Vector2f(minX, minY);
        max = new Vector2f(maxX, maxY);
    }

    public String getID() {
        return ID.toString();
    }

    public float[] getShapeX() {
        return shapeX;
    }

    public void setShapeX(float[] shapeX) {
        this.shapeX = shapeX;
    }

    public float[] getShapeY() {
        return shapeY;
    }

    public void setShapeY(float[] shapeY) {
        this.shapeY = shapeY;
    }

    public void setEntityType(EntityType ent) {
        entityType = ent;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public void setCurrentTexture(String id) {
        currentTextureId = id;
    }

    public String getCurrentTextureId() {
        return currentTextureId;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setIsActive(boolean active) {
        isActive = active;
    }
}
