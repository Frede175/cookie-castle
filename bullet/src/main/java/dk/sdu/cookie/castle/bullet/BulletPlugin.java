package dk.sdu.cookie.castle.bullet;

import dk.sdu.cookie.castle.common.assets.Asset;
import dk.sdu.cookie.castle.common.assets.AssetLoader;
import dk.sdu.cookie.castle.common.assets.AssetType;
import dk.sdu.cookie.castle.common.assets.FileType;
import dk.sdu.cookie.castle.common.data.Entity;
import dk.sdu.cookie.castle.common.data.GameData;
import dk.sdu.cookie.castle.common.data.World;
import dk.sdu.cookie.castle.common.services.IGamePluginService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BulletPlugin implements IGamePluginService {

    public static Map<String, String> getAssets() {
        return assets;
    }

    private static Map<String, String> assets = new ConcurrentHashMap<>();

    @Override
    public void start(GameData gameData, World world) {
        initializeAssets(gameData);
    }

    private void initializeAssets(GameData gameData) {
        Collection<Asset> loadingAssets = new ArrayList<>();
        loadingAssets.add(new Asset("bullet", AssetType.TEXTURE, FileType.PNG));
        assets = AssetLoader.loadAssets(this.getClass(), loadingAssets);
        gameData.addAssets(loadingAssets);
    }

    /**
     * Removes the Entity from the world, if it is of the Bullet Class
     *
     * @param gameData The GameData
     * @param world The World
     */
    @Override
    public void stop(GameData gameData, World world) {
        for (Entity e : world.getEntities()) {
            if (e.getClass() == Bullet.class) {
                world.removeEntity(e);
            }
        }
    }
}
