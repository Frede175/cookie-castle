package dk.sdu.cookie.castle.map;

import dk.sdu.cookie.castle.common.data.Entity;
import dk.sdu.cookie.castle.common.data.Entityparts.CollisionPart;
import dk.sdu.cookie.castle.common.data.Entityparts.PositionPart;
import dk.sdu.cookie.castle.common.data.GameData;
import dk.sdu.cookie.castle.common.data.World;
import dk.sdu.cookie.castle.common.services.IEntityProcessingService;
import dk.sdu.cookie.castle.map.entities.Rock;
import dk.sdu.cookie.castle.map.entities.door.Door;

import java.util.Iterator;

public class MapProcessing implements IEntityProcessingService {

    private float angle = 0;
    private float radians = 3.1415f / 2 + (float) Math.random();

    @Override
    public void process(GameData gameData, World world) {
        for (Entity door : world.getEntities(Door.class)) {
            if (!door.isActive()) continue;

            handleDoorCollision((Door) door, world);
            updateShape(door);
        }

        for (Entity rock : world.getEntities(Rock.class)) {
            if (!rock.isActive()) continue;
            updateShape(rock);
        }
    }

    private void handleDoorCollision(Door door, World world) {
        CollisionPart collisionPart = door.getPart(CollisionPart.class);

        if (!collisionPart.getIsHit()) return;

        switch (collisionPart.getCollidingEntity().getEntityType()) {
            case PLAYER:
                // Changes current room to the door room
                Room room = door.getLeadsTo();
                unloadRoom(world);
                loadRoom(room, world);
                PositionPart playerPos = collisionPart.getCollidingEntity().getPart(PositionPart.class);
                playerPos.setPosition(door.getPosition().getOpposite().getSpawnPosition());
                break;
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
        for (Iterator<String> it = Map.getInstance().getCurrentRoom().getEntityList().iterator(); it.hasNext(); ) {
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
        for (String s : nextRoom.getEntityList()) {
            world.getEntity(s).setIsActive(true);
        }
        Map.getInstance().setCurrentRoom(nextRoom);
    }
}
