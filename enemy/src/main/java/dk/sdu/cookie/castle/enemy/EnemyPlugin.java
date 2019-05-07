package dk.sdu.cookie.castle.enemy;

import dk.sdu.cookie.castle.common.assets.Asset;
import dk.sdu.cookie.castle.common.assets.AssetLoader;
import dk.sdu.cookie.castle.common.assets.AssetType;
import dk.sdu.cookie.castle.common.assets.FileType;
import dk.sdu.cookie.castle.common.data.Entity;
import dk.sdu.cookie.castle.common.data.EntityType;
import dk.sdu.cookie.castle.common.data.Entityparts.*;
import dk.sdu.cookie.castle.common.data.GameData;
import dk.sdu.cookie.castle.common.data.World;
import dk.sdu.cookie.castle.common.enemy.Enemy;
import dk.sdu.cookie.castle.common.services.IGamePluginService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EnemyPlugin implements IGamePluginService {
    private static Map<String, String> assets = new ConcurrentHashMap<>();

    @Override
    public void start(GameData gameData, World world) {
        initializeAssets(gameData);
    }

    public static String getAssetId(String name) {
        return assets.get(name);
    }

    private void initializeAssets(GameData gameData) {
        Collection<Asset> loadingAssets = new ArrayList<>();
        loadingAssets.add(new Asset("cookie", AssetType.TEXTURE, FileType.PNG));
        assets = AssetLoader.loadAssets(this.getClass(), loadingAssets);
        gameData.addAssets(loadingAssets);
    }

    @Override
    public void stop(GameData gameData, World world) {
        for (Entity entity : world.getEntities(Enemy.class)) {
            world.removeEntity(entity);
        }
    }
}
