package dk.sdu.cookie.castle.player;

import dk.sdu.cookie.castle.common.data.Entity;
import dk.sdu.cookie.castle.common.data.EntityType;
import dk.sdu.cookie.castle.common.data.Entityparts.*;
import dk.sdu.cookie.castle.common.data.GameData;
import dk.sdu.cookie.castle.common.data.World;
import dk.sdu.cookie.castle.common.services.IGamePluginService;


public class PlayerPlugin implements IGamePluginService {
    private Entity player;

    @Override
    public void start(GameData gameData, World world) {
        player = createPlayer(gameData);
        world.addEntity(player);
        System.out.println("Started player");
    }

    /**
     * Creating player and setting all the parts of the player-object
     * @param gameData
     * @return
     */
    private Entity createPlayer(GameData gameData) {

        float[] shapex = new float[3];
        float[] shapey = new float[3];
        float maxSpeed = 5;
        float x = gameData.getDisplayWidth() / 2;
        float y = gameData.getDisplayHeight() / 2;
        float radians = 3.1415f / 2;



        Entity player = new Player();
        player.setRadius(8);
        player.add(new MovingPart(maxSpeed));
        player.add(new PositionPart(x, y, radians));
        player.add(new LifePart(1,1,1,1));
        player.add(new CollisionPart());
        player.add(new InventoryPart());
        player.setShapeY(shapey);
        player.setShapeX(shapex);
        player.setEntityType(EntityType.PLAYER);

        return player;
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(player);
        System.out.println("Removed player");
    }
}
