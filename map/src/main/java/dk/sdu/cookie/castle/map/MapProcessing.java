package dk.sdu.cookie.castle.map;

import dk.sdu.cookie.castle.common.data.Entity;
import dk.sdu.cookie.castle.common.data.Entityparts.CollisionPart;
import dk.sdu.cookie.castle.common.data.Entityparts.PositionPart;
import dk.sdu.cookie.castle.common.data.GameData;
import dk.sdu.cookie.castle.common.data.World;
import dk.sdu.cookie.castle.common.services.IEntityProcessingService;
import dk.sdu.cookie.castle.map.entities.door.Door;

public class MapProcessing implements IEntityProcessingService {

    private float angle = 0;
    private float numPoints = 6;
    private float radians = 3.1415f / 2 + (float) Math.random();

    @Override
    public void process(GameData gameData, World world) {
        for (Entity door : world.getEntities(Door.class)) {
            CollisionPart collisionPart = door.getPart(CollisionPart.class);
            if (collisionPart.getHit()) {
                switch (collisionPart.getCollidingEntity().getEntityType()) {
                    case PLAYER:
                        // Changes current room to the door room
                        Room room = ((Door) door).getLeadsTo();
                        unloadRoom(world);
                        loadRoom(room, world);
                        PositionPart doorPos = door.getPart(PositionPart.class);

                        System.out.println("Go to " + room.toString());
                        break;
                }
                collisionPart.setIsHit(false);
            }
            updateShape(door);
        }
    }

    private void updateShape(Entity entity) {
        float[] shapex = entity.getShapeX();
        float[] shapey = entity.getShapeY();
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();

        for (int i = 0; i < numPoints; i++) {
            shapex[i] = x + (float) Math.cos(angle + radians) * 26;
            shapey[i] = y + (float) Math.sin(angle + radians) * 26;
            angle += 2 * 3.1415f / numPoints;
        }

        entity.setShapeX(shapex);
        entity.setShapeY(shapey);
    }

    /**
     * Unloads the room
     * Clears all the entities in the current room from the world
     * @param world
     */
    private void unloadRoom(World world) {
        for (Entity entity : Map.getInstance().getCurrentRoom().getEntityList()) {
            if(world.getEntities().contains(entity)){
                world.getEntities().remove(entity);
                continue;
            } else {
                Map.getInstance().getCurrentRoom().removeEntity(entity);
            }
        }
    }

    /**
     * loads the new room
     * Inserting all entities from the room being loaded into the "world"
     * @param nextRoom
     * @param world
     */
    private void loadRoom(Room nextRoom, World world) {
        for (Entity e : nextRoom.getEntityList()) {
            world.addEntity(e);
        }
        Map.getInstance().setCurrentRoom(nextRoom);
    }


}
