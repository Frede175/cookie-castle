package dk.sdu.cookie.castle.player;

import dk.sdu.cookie.castle.common.assets.Asset;
import dk.sdu.cookie.castle.common.assets.AssetLoader;
import dk.sdu.cookie.castle.common.assets.AssetType;
import dk.sdu.cookie.castle.common.assets.FileType;
import dk.sdu.cookie.castle.common.data.Entity;
import dk.sdu.cookie.castle.common.data.EntityType;
import dk.sdu.cookie.castle.common.data.Entityparts.*;
import dk.sdu.cookie.castle.common.data.GameData;
import dk.sdu.cookie.castle.common.data.World;
import dk.sdu.cookie.castle.common.services.IGamePluginService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerPlugin implements IGamePluginService {
    private Entity player;
    private Map<String, String> assets;

    @Override
    public void start(GameData gameData, World world) {
        initializeAssets(gameData);
        player = createPlayer(gameData);
        player.setIsActive(true);
        world.addEntity(player);
    }

    private void initializeAssets(GameData gameData) {
        Collection<Asset> loadingAssets = new ArrayList<>();
        loadingAssets.add(new Asset("player", AssetType.TEXTURE, FileType.PNG));
        assets = AssetLoader.loadAssets(this.getClass(), loadingAssets);
        gameData.addAssets(loadingAssets);
    }

    private Entity createPlayer(GameData gameData) {
        float[] shapeX = new float[3];
        float[] shapeY = new float[3];
        float maxSpeed = 200;
        float x = gameData.getDisplayWidth() / 2f;
        float y = gameData.getDisplayHeight() / 2f;
        float radians = 3.1415f / 2;

        Entity player = new Player();

        player.setRadius(8);
        player.add(new MovingPart(maxSpeed));
        player.add(new PositionPart(x, y, radians));
        player.add(new LifePart(10, 1, 10, 1));
        player.add(new CollisionPart());
        player.add(new InventoryPart());

        // Starting weapon for the player
        WeaponPart weaponPart = new WeaponPart(300f, 10f, 5f);
        player.add(weaponPart);

        player.setShapeY(shapeY);
        player.setShapeX(shapeX);
        player.add(new ShootingPart(weaponPart.getAttackSpeed()));
        player.setEntityType(EntityType.PLAYER);

        player.setCurrentTexture(assets.get("player"));

        return player;
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(player);
        gameData.removeAssets(assets.keySet());
    }
}
