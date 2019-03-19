package dk.sdu.cookie.castle.common.data.Entityparts;


import dk.sdu.cookie.castle.common.data.Buff;
import dk.sdu.cookie.castle.common.data.Entity;
import dk.sdu.cookie.castle.common.data.GameData;

public class MovingPart implements EntityPart {
    private float speed;
    private boolean left, right, up, down;

    public MovingPart(float maxSpeed) {
        this.speed = maxSpeed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    @Override
    public void process(GameData gameData, Entity entity) {
        PositionPart positionPart = entity.getPart(PositionPart.class);
        InventoryPart inventoryPart = entity.getPart(InventoryPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float buffedSpeed = speed;
        if(inventoryPart != null) {
            for (ItemPart itemPart : inventoryPart.getItemParts()) {
                if(!itemPart.isWeapon()) {
                    BuffPart buffPart = itemPart.getBuff();
                    buffedSpeed += buffPart.getSpecificBuff(Buff.MOVEMENT_SPEED);
                }
            }
        }

        if (left) {
            x -= buffedSpeed;
        }

        if (right) {
            x += buffedSpeed;
        }

        if (up) {
            y += buffedSpeed;
        }

        if (down) {
            y -= buffedSpeed;
        }

        positionPart.setPosition(x, y);

    }

}
