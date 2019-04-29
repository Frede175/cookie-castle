package dk.sdu.cookie.castle.common.data;

import dk.sdu.cookie.castle.common.assets.Asset;
import dk.sdu.cookie.castle.common.data.Entityparts.EntityPart;
import dk.sdu.cookie.castle.common.assets.AssetLoader;

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

    private float[] shapeX = new float[4];
    private float[] shapeY = new float[4];
    private float radius;
    private Map<Class, EntityPart> parts;
    private EntityType entityType;
    private Map<String, Asset> assets;
    private Map<String, String> assetReferences;
    private String currentTextureId;

    public Entity() {
        parts = new ConcurrentHashMap<>();
    }

    public void initializeAssets(Map<String, Asset> assets) {
        System.out.println("Initializing assets for class: " + this.getClass());
        if (assets.size() > 0) {
            assetReferences = AssetLoader.loadAssets(this.getClass(), assets);
            this.assets = assets;
        }
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

    public void setRadius(float r) {
        radius = r;
    }

    public float getRadius() {
        return radius;
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

    public Map<String, Asset> getAssets() {
        return assets;
    }

    public void setCurrentTexture(String name) {
        if (assetReferences == null) {
            System.out.println("Assets have not been loaded");
            return;
        }

        currentTextureId = assetReferences.get(name);
        System.out.println("setting texture id: " + currentTextureId + " name: " + name);
    }

    public String getCurrentTextureId() {
        return currentTextureId;
    }
}
