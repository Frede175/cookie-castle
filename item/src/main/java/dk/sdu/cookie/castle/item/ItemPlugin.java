package dk.sdu.cookie.castle.item;

import dk.sdu.cookie.castle.common.assets.Asset;
import dk.sdu.cookie.castle.common.assets.AssetLoader;
import dk.sdu.cookie.castle.common.assets.AssetType;
import dk.sdu.cookie.castle.common.assets.FileType;
import dk.sdu.cookie.castle.common.data.Entity;
import dk.sdu.cookie.castle.common.data.EntityType;
import dk.sdu.cookie.castle.common.data.Entityparts.*;
import dk.sdu.cookie.castle.common.data.GameData;
import dk.sdu.cookie.castle.common.data.World;
import dk.sdu.cookie.castle.common.item.Item;
import dk.sdu.cookie.castle.common.services.IGamePluginService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ItemPlugin implements IGamePluginService {

    private static Map<String, String> assets = new ConcurrentHashMap<>();

    @Override
    public void start(GameData gameData, World world) {
        initializeAssets(gameData);
    }

    private void initializeAssets(GameData gameData) {
        Collection<Asset> loadingAssets = new ArrayList<>();
        loadingAssets.add(new Asset("sugar", AssetType.TEXTURE, FileType.PNG));
        loadingAssets.add(new Asset("shake", AssetType.TEXTURE, FileType.PNG));
        loadingAssets.add(new Asset("energyDrink", AssetType.TEXTURE, FileType.PNG));
        assets = AssetLoader.loadAssets(this.getClass(), loadingAssets);
        gameData.addAssets(loadingAssets);
    }

    public static Map<String, String> getAssets() {
        return assets;
    }

    @Override
    public void stop(GameData gameData, World world) {
        for (Entity entity : world.getEntities(Item.class)) {
            world.removeEntity(entity);
        }
        gameData.removeAssets(assets.values());
    }
}
