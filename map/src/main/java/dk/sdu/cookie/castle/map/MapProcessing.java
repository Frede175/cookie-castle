package dk.sdu.cookie.castle.map;

import dk.sdu.cookie.castle.common.data.Entity;
import dk.sdu.cookie.castle.common.data.EntityType;
import dk.sdu.cookie.castle.common.data.Entityparts.CollisionPart;
import dk.sdu.cookie.castle.common.data.Entityparts.LifePart;
import dk.sdu.cookie.castle.common.data.Entityparts.PositionPart;
import dk.sdu.cookie.castle.common.data.GameData;
import dk.sdu.cookie.castle.common.data.World;
import dk.sdu.cookie.castle.common.services.IEntityProcessingService;
import dk.sdu.cookie.castle.map.entities.Door;

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
                        Map.getInstance().setCurrentRoom(room);
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


    // der skal laves en func der skal tage imod currentRoom, Items, checke hvilke der skal ligge/ikke skal ligge i world
    // skal fjerne de ting, der er blevet fjernet fra world, fra current room.
    // Til sidst skal alle entiteter som ligger i currentRoom fjernes fra wold (da disse er gemt i Rummet)
    // Indsæt alle entities fra det nye currentRoom i World

}
