package dk.sdu.cookie.castle.item;

import dk.sdu.cookie.castle.common.data.Entity;
import dk.sdu.cookie.castle.common.data.EntityType;
import dk.sdu.cookie.castle.common.data.Entityparts.CollisionPart;
import dk.sdu.cookie.castle.common.data.Entityparts.ItemPart;
import dk.sdu.cookie.castle.common.data.Entityparts.PositionPart;
import dk.sdu.cookie.castle.common.data.GameData;
import dk.sdu.cookie.castle.common.data.World;
import dk.sdu.cookie.castle.common.item.Item;
import dk.sdu.cookie.castle.common.services.IEntityProcessingService;

public class ItemProcessing implements IEntityProcessingService {
    
    private float radians = 0;

    @Override
    public void process(GameData gameData, World world) {
        for (Entity item : world.getEntities(Item.class)) {
            if (!item.isActive()) continue;

            PositionPart positionPart = item.getPart(PositionPart.class);
            CollisionPart collisionPart = item.getPart(CollisionPart.class);
            ItemPart itemPart = item.getPart(ItemPart.class);

            if (collisionPart.getIsHit() && collisionPart.getCollidingEntity().getEntityType() == EntityType.PLAYER) {
                world.removeEntity(item);
            }

            positionPart.process(gameData, item);
            itemPart.process(gameData, item);

            updateShape(item);
        }
    }

    private void updateShape(Entity entity) {
        float[] shapeX = entity.getShapeX();
        float[] shapeY = entity.getShapeY();
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();

        switch (((Item) entity).getItemType()) {
            case ENERGY_DRINK:
                shapeX[0] = x + (float) Math.cos(radians + Math.PI / 8 * 3) * 28;
                shapeY[0] = y + (float) Math.sin(radians + Math.PI / 8 * 3) * 28;
                shapeX[1] = x + (float) Math.cos(radians - Math.PI / 8 * 3) * 28;
                shapeY[1] = y + (float) Math.sin(radians - Math.PI / 8 * 3) * 28;
                shapeX[2] = x + (float) Math.cos(radians - Math.PI / 8 * 5) * 28;
                shapeY[2] = y + (float) Math.sin(radians - Math.PI / 8 * 5) * 28;
                shapeX[3] = x + (float) Math.cos(radians + Math.PI / 8 * 5) * 28;
                shapeY[3] = y + (float) Math.sin(radians + Math.PI / 8 * 5) * 28;
                break;
            case PROTEIN_SHAKE:
                shapeX[0] = x + (float) Math.cos(radians + Math.PI / 8 * 3) * 25;
                shapeY[0] = y + (float) Math.sin(radians + Math.PI / 8 * 3) * 25;
                shapeX[1] = x + (float) Math.cos(radians - Math.PI / 8 * 3) * 25;
                shapeY[1] = y + (float) Math.sin(radians - Math.PI / 8 * 3) * 25;
                shapeX[2] = x + (float) Math.cos(radians - Math.PI / 8 * 5) * 25;
                shapeY[2] = y + (float) Math.sin(radians - Math.PI / 8 * 5) * 25;
                shapeX[3] = x + (float) Math.cos(radians + Math.PI / 8 * 5) * 25;
                shapeY[3] = y + (float) Math.sin(radians + Math.PI / 8 * 5) * 25;
                break;
            case SUGAR:
                shapeX[0] = x + (float) Math.cos(radians - Math.PI / 16) * 23;
                shapeY[0] = y + (float) Math.sin(radians - Math.PI / 16) * 23;
                shapeX[1] = x + (float) Math.cos(radians - Math.PI / 2) * 20;
                shapeY[1] = y + (float) Math.sin(radians - Math.PI / 2) * 20;
                shapeX[2] = x + (float) Math.cos(radians - Math.PI + Math.PI / 16) * 23;
                shapeY[2] = y + (float) Math.sin(radians - Math.PI + Math.PI / 16) * 23;
                shapeX[3] = x + (float) Math.cos(radians + Math.PI / 2) * 23;
                shapeY[3] = y + (float) Math.sin(radians + Math.PI / 2) * 23;
                break;
        }

        entity.setShapeX(shapeX);
        entity.setShapeY(shapeY);
        entity.updateMinMax();
    }
}

