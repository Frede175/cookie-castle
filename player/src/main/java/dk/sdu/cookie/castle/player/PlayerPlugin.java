package dk.sdu.cookie.castle.player;

import dk.sdu.cookie.castle.common.assets.Asset;
import dk.sdu.cookie.castle.common.assets.AssetType;
import dk.sdu.cookie.castle.common.assets.FileType;
import dk.sdu.cookie.castle.common.data.Entity;
import dk.sdu.cookie.castle.common.data.EntityType;
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
        gameData.addAssets(assets);
        player = createPlayer(gameData);
        world.addEntity(player);
    }

    private void initializeAssets() {
        Asset sumo = new Asset("sumo", AssetType.TEXTURE, FileType.PNG);
        assets.put(sumo.getId(), sumo);
    }

    private Entity createPlayer(GameData gameData) {
        float[] shapeX = new float[3];
        float[] shapeY = new float[3];
        float maxSpeed = 150;
        float x = gameData.getDisplayWidth() / 2;
        float y = gameData.getDisplayHeight() / 2;
        float radians = 3.1415f / 2;

        Entity player = new Player();
        player.initializeAssets(assets);

        player.setRadius(8);
        player.add(new MovingPart(maxSpeed));
        player.add(new PositionPart(x, y, radians));
        player.add(new LifePart(1,1,1,1));
        player.add(new CollisionPart());
        player.add(new InventoryPart());

        // Starting weapon for the player
        WeaponPart weaponPart = new WeaponPart(400f,10f,5f);
        player.add(weaponPart);

        player.setShapeY(shapeY);
        player.setShapeX(shapeX);
        player.add(new ShootingPart(weaponPart.getAttackSpeed()));
        player.setEntityType(EntityType.PLAYER);

        player.setCurrentTexture("sumo");

        return player;
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(player);
        gameData.removeAssets(assets);
    }
}
