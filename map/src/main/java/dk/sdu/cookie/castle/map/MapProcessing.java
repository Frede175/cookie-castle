package dk.sdu.cookie.castle.map;

import dk.sdu.cookie.castle.common.data.Entity;
import dk.sdu.cookie.castle.common.data.EntityType;
import dk.sdu.cookie.castle.common.data.Entityparts.CollisionPart;
import dk.sdu.cookie.castle.common.data.Entityparts.PositionPart;
import dk.sdu.cookie.castle.common.data.GameData;
import dk.sdu.cookie.castle.common.data.World;
import dk.sdu.cookie.castle.common.services.IEntityProcessingService;
import dk.sdu.cookie.castle.map.entities.Rock;
import dk.sdu.cookie.castle.map.entities.door.Door;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

public class MapProcessing implements IEntityProcessingService {
    private Map map = Map.getInstance();
    private float angle = 0;
    private float radians = 3.1415f / 2 + (float) Math.random();

    @Override
    public void process(GameData gameData, World world) {
        handleEntities(world);
    }

    private void handleEntities(World world) {
        java.util.Map<String, Entity> entities = new ConcurrentHashMap<>();

        for (Entity entity : world.getEntities()) {
            if (!entity.isActive()) continue;

            if (map.getCurrentRoom().getDefaultState().containsKey(entity)) entities.put(entity.getID(), entity);

            if (entity.getClass() == Door.class) {
                handleDoorCollision((Door) entity, world);
                updateShape(entity);
            }

            if (entity.getClass() == Rock.class) {
                updateShape(entity);
            }
        }

//        saveRoomState(entities);
    }

//    private void saveRoomState(java.util.Map<String, Entity> activeEntities) {
//        for (Iterator<String> it = map.getCurrentRoom().getEntities().iterator(); it.hasNext(); ) {
////        for (Iterator<java.util.Map.Entry<Entity, PositionPart>> it = map.getCurrentRoom().getDefaultState().entrySet().iterator(); it.hasNext(); ) {
//            String e = it.next();
//
//            // Check if entity has been removed from the world
//            if (!activeEntities.containsValue(e)) {
//                // Remove bullets
//                if (e.getEntityType().equals(EntityType.ENEMY_BULLET) || e.getEntityType().equals(EntityType.PLAYER_BULLET))
//                // Bypass state saving if bundles are unloaded
//                if (map.isEnemyPluginActive() && e.getEntityType().equals(EntityType.ENEMY)) {
//                    System.out.println("Removing enemy");
//                    it.remove();
//                }
//                if (map.isItemPluginActive() && e.getEntityType().equals(EntityType.ITEM)) it.remove();
//            }
//        }
//    }

    private void handleDoorCollision(Door door, World world) {
        CollisionPart collisionPart = door.getPart(CollisionPart.class);

        if (!collisionPart.getIsHit()) return;

        if (collisionPart.getCollidingEntity().getEntityType().equals(EntityType.PLAYER)) {
            // Changes current room to the door room
            Room room = door.getLeadsTo();
            unloadRoom(world);
            loadRoom(room, world);
            PositionPart playerPos = collisionPart.getCollidingEntity().getPart(PositionPart.class);
            playerPos.setPosition(door.getPosition().getOpposite().getSpawnPosition());
        }

        collisionPart.setIsHit(false);
    }

    private void updateShape(Entity entity) {
        float numPoints = 6;
        float[] shapeX = entity.getShapeX();
        float[] shapeY = entity.getShapeY();
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();

        for (int i = 0; i < numPoints; i++) {
            shapeX[i] = x + (float) Math.cos(angle + radians) * 26;
            shapeY[i] = y + (float) Math.sin(angle + radians) * 26;
            angle += 2 * 3.1415f / numPoints;
        }

        entity.setShapeX(shapeX);
        entity.setShapeY(shapeY);
        entity.updateMinMax();
    }

    /**
     * Unloads the room
     * Clears all the entities in the current room from the world
     *
     * @param world
     */
    private void unloadRoom(World world) {
        for (Iterator<String> it = map.getCurrentRoom().getEntities().iterator(); it.hasNext(); ) {
            String ID = it.next();

            if (world.containsEntity(ID)) {
//                world.getEntity(ID).setIsActive(false);
                world.removeEntity(ID);
                it.remove();
            }
        }
    }

    /**
     * loads the new room
     * Inserting all entities from the room being loaded into the "world"
     *
     * @param nextRoom
     * @param world
     */
    private void loadRoom(Room nextRoom, World world) {
        world.removeBullets();

        for (java.util.Map.Entry<Entity, PositionPart> e : nextRoom.getDefaultState().entrySet()) {
            PositionPart p = e.getKey().getPart(PositionPart.class);
            p.setPosition(e.getValue());
            e.getKey().setIsActive(true);
            world.addEntity(e.getKey());
        }
//        for (String s : nextRoom.getEntities()) {
//            world.getEntity(s).setIsActive(true);
//        }
        map.setCurrentRoom(nextRoom);
    }
}
