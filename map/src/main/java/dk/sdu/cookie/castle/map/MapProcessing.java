package dk.sdu.cookie.castle.map;

import dk.sdu.cookie.castle.common.data.Entity;
import dk.sdu.cookie.castle.common.data.EntityType;
import dk.sdu.cookie.castle.common.data.Entityparts.CollisionPart;
import dk.sdu.cookie.castle.common.data.Entityparts.PositionPart;
import dk.sdu.cookie.castle.common.data.GameData;
import dk.sdu.cookie.castle.common.data.World;
import dk.sdu.cookie.castle.common.enemy.Enemy;
import dk.sdu.cookie.castle.common.item.Item;
import dk.sdu.cookie.castle.common.services.IEntityProcessingService;
import dk.sdu.cookie.castle.common.util.Vector2f;
import dk.sdu.cookie.castle.map.entities.EntityPreset;
import dk.sdu.cookie.castle.map.entities.Rock;
import dk.sdu.cookie.castle.map.entities.door.Door;

import java.util.ArrayList;
import java.util.Iterator;

public class MapProcessing implements IEntityProcessingService {
    private Map map = Map.getInstance();

    @Override
    public void process(GameData gameData, World world) {
        if (map.isEnemyLoaded()) {
            reloadEntities(EntityPreset.ENEMY, world);
            map.setEnemyLoaded(false);
        }
        if (map.isItemLoaded()) {
            reloadEntities(EntityPreset.ITEM, world);
            map.setItemLoaded(false);
        }
        handleEntities(world);
    }

    private void reloadEntities(EntityPreset entityPreset, World world) {
        for (Room room : map.getListOfRooms()) {
            ArrayList<String> entities = new ArrayList<>();
            for (PositionPart pos : room.getRoomPreset().getEntityPositions(entityPreset)) {
                entities.add(map.createEntity(entityPreset, pos, world));
            }
            room.setEntities(entities);
        }
        for (String s : map.getCurrentRoom().getEntities()) {
            world.getEntity(s).setIsActive(true);
        }
    }

    private void handleEntities(World world) {
        for (Entity entity : world.getEntities()) {
            if (!entity.isActive()) continue;

            if (entity.getClass() == Door.class) {
                handleDoorCollision((Door) entity, world);
                updateDoor(entity);
            }

            if (entity.getClass() == Rock.class) {
                updateRock(entity);
            }
        }
    }

    private void handleDoorCollision(Door door, World world) {
        CollisionPart collisionPart = door.getPart(CollisionPart.class);

        if (!collisionPart.getIsHit()) return;

        Entity player = collisionPart.getCollidingEntity();
        if (player.getEntityType() == EntityType.PLAYER) {
            // Changes current room to the door room
            Room room = door.getLeadsTo();
            unloadRoom(world);
            loadRoom(room, world);

            // Updating player position
            PositionPart playerPositionPart = player.getPart(PositionPart.class);
            PositionPart spawnPositionPart = door.getPosition().getOpposite().getSpawnPosition();
            Vector2f playerPos = new Vector2f(playerPositionPart.getX(), playerPositionPart.getY());
            Vector2f newPlayerPos = new Vector2f(spawnPositionPart.getX(), spawnPositionPart.getY());
            Vector2f diff = newPlayerPos.subtract(playerPos);
            playerPositionPart.setPosition(newPlayerPos.getX(), newPlayerPos.getY());
            for (int i = 0; i < player.getShapeX().length; i++) {
                player.getShapeX()[i] = player.getShapeX()[i] + diff.getX();
                player.getShapeY()[i] = player.getShapeY()[i] + diff.getY();
            }
            player.updateMinMax();
        }

        collisionPart.setIsHit(false);
    }

    private void updateDoor(Entity entity) {
        float[] shapeX = entity.getShapeX();
        float[] shapeY = entity.getShapeY();
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();

        shapeX[0] = x + (float) Math.cos(radians + 0.2f) * 35.69f;
        shapeY[0] = y + (float) Math.sin(radians + 0.2f) * 35.69f;
        shapeX[1] = x + (float) Math.cos(radians - 0.2f) * 35.69f;
        shapeY[1] = y + (float) Math.sin(radians - 0.2f) * 35.69f;
        shapeX[2] = x + (float) Math.cos(radians - 0.25f) * 27;
        shapeY[2] = y + (float) Math.sin(radians - 0.25f) * 27;
        shapeX[3] = x + (float) Math.cos(radians - 0.44f) * 28.8f;
        shapeY[3] = y + (float) Math.sin(radians - 0.44f) * 28.8f;
        shapeX[4] = x + (float) Math.cos(radians - 0.65f) * 20.1f;
        shapeY[4] = y + (float) Math.sin(radians - 0.65f) * 20.1f;
        shapeX[5] = x + (float) Math.cos(radians - 0.40f) * 17.2f;
        shapeY[5] = y + (float) Math.sin(radians - 0.40f) * 17.2f;
        shapeX[6] = x + (float) Math.cos(radians - Math.PI + 0.2f) * 35.69f;
        shapeY[6] = y + (float) Math.sin(radians - Math.PI + 0.2f) * 35.69f;
        shapeX[7] = x + (float) Math.cos(radians - Math.PI - 0.2f) * 35.69f;
        shapeY[7] = y + (float) Math.sin(radians - Math.PI - 0.2f) * 35.69f;

        entity.updateMinMax();
    }

    private void updateRock(Entity entity) {
        float[] shapeX = entity.getShapeX();
        float[] shapeY = entity.getShapeY();
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();

        shapeX[0] = x + (float) Math.cos(radians + 0.5f) * 26.16f;
        shapeY[0] = y + (float) Math.sin(radians + 0.5f) * 19.35f;
        shapeX[1] = x + (float) Math.cos(radians - 0.34f) * 28.59f;
        shapeY[1] = y + (float) Math.sin(radians - 0.34f) * 21.19f;
        shapeX[2] = x + (float) Math.cos(radians - 0.89f) * 25.68f;
        shapeY[2] = y + (float) Math.sin(radians - 0.89f) * 19.03f;
        shapeX[3] = x + (float) Math.cos(radians - 2.18f) * 25.24f;
        shapeY[3] = y + (float) Math.sin(radians - 2.18f) * 18.7f;
        shapeX[4] = x + (float) Math.cos(radians - 2.75f) * 28.05f;
        shapeY[4] = y + (float) Math.sin(radians - 2.75f) * 20.76f;
        shapeX[5] = x + (float) Math.cos(radians + 2.75f) * 30.27f;
        shapeY[5] = y + (float) Math.sin(radians + 2.75f) * 22.78f;
        shapeX[6] = x + (float) Math.cos(radians + 2.44f) * 31.08f;
        shapeY[6] = y + (float) Math.sin(radians + 2.44f) * 22.97f;
        shapeX[7] = x + (float) Math.cos(radians + 1.67f) * 20.59f;
        shapeY[7] = y + (float) Math.sin(radians + 1.67f) * 15.24f;

        entity.updateMinMax();
    }

    /**
     * Unloads the room
     * Clears all the entities in the current room from the world
     *
     * @param world
     */
    private void unloadRoom(World world) {
        world.removeBullets();

        for (Iterator<String> it = map.getCurrentRoom().getEntities().iterator(); it.hasNext(); ) {
            String ID = it.next();

            if (world.containsEntity(ID)) {
                world.getEntity(ID).setIsActive(false);
            } else {
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
        for (Iterator<String> it = nextRoom.getEntities().iterator(); it.hasNext(); ) {
            String ID = it.next();

            if (world.containsEntity(ID)) {
                world.getEntity(ID).setIsActive(true);
            } else {
                it.remove();
            }
        }

        map.setCurrentRoom(nextRoom);
    }
}
