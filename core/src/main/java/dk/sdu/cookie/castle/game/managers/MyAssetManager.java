package dk.sdu.cookie.castle.game.managers;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import dk.sdu.cookie.castle.common.assets.Asset;
import dk.sdu.cookie.castle.common.assets.AssetLoader;
import dk.sdu.cookie.castle.common.assets.AssetType;
import dk.sdu.cookie.castle.common.assets.FileType;
import dk.sdu.cookie.castle.common.data.GameData;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MyAssetManager extends AssetManager {
    // Internal registry of loaded and managed assets
    private Map<String, Asset> loadedAssets = new ConcurrentHashMap<>();
    private String backgroundId;

    public MyAssetManager() {
        initializeLocalAssets();
    }

    /**
     * Manage assets by loading or unloading them depending on activeAssets
     *
     * @param gameData Current gameData containing active assets
     * @return boolean Whether the AssetManager has finished loading
     */
    public boolean update(GameData gameData) {
        Map<String, Asset> activeAssets = gameData.getActiveAssets();
        if (loadedAssets.size() == activeAssets.size()) {
            return super.update();
        }

        System.out.println("Active assets: " + gameData.getActiveAssets().size());

        for (Map.Entry<String, Asset> asset : gameData.getActiveAssets().entrySet()) {
            if (!loadedAssets.containsKey(asset.getKey())) {
                loadAsset(asset.getValue(), true);
            }
        }

        boolean isUpdated = super.update();

        if (isUpdated) {
            checkForUnusedAssets(activeAssets);
        } else {
            System.out.println("Loading assets: " + super.getProgress());
        }

        return isUpdated;
    }

    /**
     * Check for unused assets and unload them if needed
     *
     * @param activeAssets Currently active assets
     */
    private void checkForUnusedAssets(Map<String, Asset> activeAssets) {
        for (Map.Entry<String, Asset> asset : loadedAssets.entrySet()) {
            if (!activeAssets.containsKey(asset.getKey())) {
                System.out.println("Unloading asset: " + asset.getKey());
                super.unload(asset.getKey());
                loadedAssets.remove(asset.getKey());
            }
        }
    }

    private void initializeLocalAssets() {
        // Initialize local assets
        Map<String, Asset> assets = new ConcurrentHashMap<>();
        Asset background = new Asset("background", AssetType.TEXTURE, FileType.PNG);
        assets.put(background.getId(), background);
        backgroundId = background.getId();

        AssetLoader.loadAssets(this.getClass(), assets);
        loadAssets(assets);
    }

    /**
     * Load assets without registering them in loadedAssets
     *
     * @param assets Assets mapped by their id
     */
    private void loadAssets(Map<String, Asset> assets) {
        for (Map.Entry<String, Asset> asset : assets.entrySet()) {
            loadAsset(asset.getValue(), false);
        }
    }

    /**
     * Load asset data
     *
     * @param asset Asset to be loaded
     */
    private void loadAsset(Asset asset) {
        System.out.println("Loading asset: " + asset.getName() + " - " + asset.getId());
        FileHandle file = new FileHandle(asset.getId());
        file.write(asset.getData(), false);
        asset.closeInputStream();

        Class assetClass = getAssetClass(asset.getAssetType());
        AssetDescriptor assetDescriptor = getAssetDescriptor(file, assetClass);

        super.load(assetDescriptor);
    }

    /**
     * Handle asset loading and optionally register in loadedAssets
     *
     * @param asset          Asset to be loaded
     * @param isFromGameData Whether to register or not
     */
    private void loadAsset(Asset asset, boolean isFromGameData) {
        loadAsset(asset);

        if (isFromGameData) {
            loadedAssets.put(asset.getId(), asset);
        }
    }

    private Class getAssetClass(AssetType type) {
        Class returnClass;

        switch (type) {
            case TEXTURE:
                returnClass = Texture.class;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }

        return returnClass;
    }

    public Texture getBackground() {
        return super.get(backgroundId, Texture.class);
    }

    /**
     * Get AssetDescriptor from FileHandle and Class
     *
     * @param file Newly created FileHandle for the asset
     * @param type Type of asset
     * @param <T>  Type of asset
     * @return AssetDescriptor
     */
    private <T> AssetDescriptor getAssetDescriptor(FileHandle file, Class<T> type) {
        return new AssetDescriptor<>(file, type);
    }
}
