package dk.sdu.cookie.castle.player;

import dk.sdu.cookie.castle.common.assets.Asset;
import dk.sdu.cookie.castle.common.assets.AssetType;
import dk.sdu.cookie.castle.common.assets.FileType;
import dk.sdu.cookie.castle.common.data.Entity;
import dk.sdu.cookie.castle.common.data.Entityparts.*;
import dk.sdu.cookie.castle.common.data.GameData;
import dk.sdu.cookie.castle.common.data.World;
import dk.sdu.cookie.castle.common.services.IGamePluginService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerPlugin implements IGamePluginService {
    private Entity player;
    private Map<String, Asset> assets = new ConcurrentHashMap<>();

    @Override
    public void start(GameData gameData, World world) {
        initializeAssets();
        player = createPlayer(gameData);
        world.addEntity(player);
        System.out.println("Started player");
    }

    private void initializeAssets() {
        Asset sumo = new Asset("sumo", AssetType.TEXTURE, FileType.PNG);
        assets.put(sumo.getId(), sumo);
//        assets.add(new Asset("heart", AssetType.TEXTURE, FileType.JPG));
    }

    private Entity createPlayer(GameData gameData) {
        float deacceleration = 10;
        float acceleration = 200;
        float maxSpeed = 5;
        float rotationSpeed = 5;
        float x = gameData.getDisplayWidth() / 2;
        float y = gameData.getDisplayHeight() / 2;
        float radians = 3.1415f / 2;

        float[] colour = new float[4];
        colour[0] = 1.0f;
        colour[1] = 1.0f;
        colour[2] = 1.0f;
        colour[3] = 1.0f;

        Entity player = new Player(this.getClass(), assets);
        player.setRadius(8);
        player.add(new MovingPart(maxSpeed));
        player.add(new PositionPart(x, y));
        player.add(new LifePart(1, 1, 1, 1));
        player.add(new CollisionPart());
        player.add(new InventoryPart());

        player.setCurrentTextureId("sumo");

        return player;
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(player);
        System.out.println("Removed player");
    }
}
