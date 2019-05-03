package dk.sdu.cookie.castle.common.data.Entityparts;


import dk.sdu.cookie.castle.common.data.Entity;
import dk.sdu.cookie.castle.common.data.GameData;

public class MovingPart implements EntityPart {
    private float speed;
    private boolean left, right, up, down;

    public MovingPart(float maxSpeed) {
        speed = maxSpeed;
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
        float deltaX = 0;
        float deltaY = 0;
        float buffedSpeed = speed;
        if (inventoryPart != null) {
            for (ItemPart itemPart : inventoryPart.getItemParts()) {
                if (!itemPart.isWeapon()) {
                    BuffPart buffPart = itemPart.getBuff();
                    buffedSpeed *= buffPart.getMultiplier();
                }
            }
        }

        buffedSpeed *= gameData.getDelta();

        if (left) {
            positionPart.setRadians(3.1415f);
            deltaX -= buffedSpeed;
        }

        if (right) {
            positionPart.setRadians(0);
            deltaX += buffedSpeed;
        }

        if (up) {
            positionPart.setRadians(3.1415f / 2f);
            deltaY += buffedSpeed;
        }

        if (down) {
            positionPart.setRadians(3.1415f + (3.1415f / 2f));
            deltaY -= buffedSpeed;
        }

        if (up && right) {
            positionPart.setRadians(3.1415f / 4f);
        }

        if (up && left) {
            positionPart.setRadians(((3.1415f / 2f) + (3.1415f / 4f)));
        }

        if (down && left) {
            positionPart.setRadians((3.1415f + (3.1415f / 4f)));
        }

        if (down && right) {
            positionPart.setRadians((3.1415f + (3.1415f / 4f * 3)));
        }

        if (Math.abs(deltaX) + Math.abs(deltaY) > buffedSpeed) {
            deltaX = (deltaX / (float) Math.sqrt((deltaX * deltaX) + (deltaY * deltaY))) * buffedSpeed;
            deltaY = (deltaY / (float) Math.sqrt((deltaX * deltaX) + (deltaY * deltaY))) * buffedSpeed;

            x += deltaX;
            y += deltaY;

        } else {
            x += deltaX;
            y += deltaY;
        }

        positionPart.setPosition(x, y);

    }

}
