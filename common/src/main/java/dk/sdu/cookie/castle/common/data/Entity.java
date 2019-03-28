package dk.sdu.cookie.castle.common.data;

import dk.sdu.cookie.castle.common.assets.Asset;
import dk.sdu.cookie.castle.common.data.Entityparts.EntityPart;
import dk.sdu.cookie.castle.common.assets.AssetHelper;

import java.io.Serializable;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Entity implements Serializable {
    private final UUID ID = UUID.randomUUID();

    private float[] shapeX = new float[4];
    private float[] shapeY = new float[4];
    private float radius;
    private Map<Class, EntityPart> parts;
    private EntityType entityType;
    private Map<String, Asset> assets;
    private String currentTextureId;

    public Entity(Class c, Map<String, Asset> assets) {
        initializeAssets(c, assets);
        parts = new ConcurrentHashMap<>();
    }

    public Entity() {
    }

    private void initializeAssets(Class c, Map<String, Asset> assets) {
        if (assets.size() > 0) {
            AssetHelper.loadAssetData(c, assets);
        }

        this.assets = assets;
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
        this.radius = r;
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
        this.entityType = ent;
    }

    public EntityType getEntityType() {
        return this.entityType;
    }

    public Map<String, Asset> getAssets() {
        return assets;
    }

    public void setCurrentTextureId(String name) {
        System.out.println("setting texture id from name: " + name);
        currentTextureId = assets.entrySet()
                .stream()
                .filter(asset -> name.equals(asset.getValue().getName()))
                .findFirst()
                .get()
                .getKey();
    }

    public String getCurrentTextureId() {
        return currentTextureId;
    }
}
