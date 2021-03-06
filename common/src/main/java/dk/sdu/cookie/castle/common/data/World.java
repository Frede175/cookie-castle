package dk.sdu.cookie.castle.common.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The World class is the overall object that has a list of all the entities in the world at a given time
 * this will be all the entities that have to be shown in the current room of the map
 */
public class World {

    private final Map<String, Entity> entityMap = new ConcurrentHashMap<>();

    public String addEntity(Entity entity) {
        entityMap.put(entity.getID(), entity);
        return entity.getID();
    }

    public void removeEntity(String entityID) {
        entityMap.remove(entityID);
    }

    public void removeEntity(Entity entity) {
        entityMap.remove(entity.getID());
    }

    public Collection<Entity> getEntities() {
        return entityMap.values();
    }

    public Map<String, Entity> getEntityMap() {
        return entityMap;
    }

    public <E extends Entity> List<Entity> getEntities(Class<E>... entityTypes) {
        List<Entity> r = new ArrayList<>();
        for (Entity e : getEntities()) {
            for (Class<E> entityType : entityTypes) {
                if (entityType.equals(e.getClass())) {
                    r.add(e);
                }
            }
        }
        return r;
    }

    public boolean containsEntity(String ID) {
        return entityMap.containsKey(ID);
    }

    public Entity getEntity(String ID) {
        return entityMap.get(ID);
    }

    public void removeBullets() {
        for (Entity e : getEntities()) {
            if (e.getEntityType().equals(EntityType.PLAYER_BULLET) || e.getEntityType().equals(EntityType.ENEMY_BULLET)) {
                removeEntity(e.getID());
            }
        }
    }
}
