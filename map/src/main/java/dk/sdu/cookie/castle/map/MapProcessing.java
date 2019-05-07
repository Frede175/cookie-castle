package dk.sdu.cookie.castle.map;

import dk.sdu.cookie.castle.common.data.Entity;
import dk.sdu.cookie.castle.common.data.Entityparts.CollisionPart;
import dk.sdu.cookie.castle.common.data.Entityparts.PositionPart;
import dk.sdu.cookie.castle.common.data.GameData;
import dk.sdu.cookie.castle.common.data.World;
import dk.sdu.cookie.castle.common.services.IEntityProcessingService;
import dk.sdu.cookie.castle.map.entities.Rock;
import dk.sdu.cookie.castle.map.entities.door.Door;
import dk.sdu.cookie.castle.map.entities.door.DoorPosition;

import java.util.Iterator;

public class MapProcessing implements IEntityProcessingService {

    private float angle = 0;
    private float numPoints = 6;
    private float radians = 3.1415f / 2 + (float) Math.random();

    @Override
    public void process(GameData gameData, World world) {
        for (Entity door : world.getEntities(Door.class)) {
            if (!door.isActive()) continue;

            CollisionPart collisionPart = door.getPart(CollisionPart.class);
            if (collisionPart.getHit()) {
                switch (collisionPart.getCollidingEntity().getEntityType()) {
                    case PLAYER:
                        // Changes current room to the door room
                        Room room = ((Door) door).getLeadsTo();
                        unloadRoom(world);
                        loadRoom(room, world);
                        PositionPart doorPos = door.getPart(PositionPart.class);
                        PositionPart playerPos = collisionPart.getCollidingEntity().getPart(PositionPart.class);
                        if (DoorPosition.TOP.getPositionPart() == doorPos) {
                            PositionPart bottom = DoorPosition.BOTTOM.getPositionPart();
                            playerPos.setPosition(bottom.getX(), bottom.getY() + 35);
                        } else if (DoorPosition.BOTTOM.getPositionPart() == doorPos) {
                            PositionPart top = DoorPosition.TOP.getPositionPart();
                            playerPos.setPosition(top.getX(), top.getY() - 35);
                        } else if (DoorPosition.LEFT.getPositionPart() == doorPos) {
                            PositionPart right = DoorPosition.RIGHT.getPositionPart();
                            playerPos.setPosition(right.getX() - 35, right.getY());
                        } else {
                            PositionPart left = DoorPosition.LEFT.getPositionPart();
                            playerPos.setPosition(left.getX() + 35, left.getY());
                        }
                        break;
                }
                collisionPart.setIsHit(false);
            }
            updateShape(door);
        }
        for (Entity rock : world.getEntities(Rock.class)) {
            if (!rock.isActive()) continue;
            updateShape(rock);
        }
    }

    private void updateShape(Entity entity) {
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
