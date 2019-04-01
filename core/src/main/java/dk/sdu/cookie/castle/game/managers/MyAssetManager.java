package dk.sdu.cookie.castle.game.managers;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import dk.sdu.cookie.castle.common.assets.Asset;
import dk.sdu.cookie.castle.common.assets.AssetLoader;
import dk.sdu.cookie.castle.common.assets.AssetType;
import dk.sdu.cookie.castle.common.assets.FileType;
import dk.sdu.cookie.castle.common.data.Entity;
import dk.sdu.cookie.castle.common.data.World;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MyAssetManager extends AssetManager {
    private Map<Class, ArrayList<String>> classAssets;
    private String backgroundId;

    public MyAssetManager() {
        classAssets = new ConcurrentHashMap<>();
        initializeLocalAssets();
    }

    /**
     * Manage assets by loading or unloading them depending on the active entities
     *
     * @param world Current game world with entities
     * @return boolean
     */
    public boolean update(World world) {
        ArrayList<Class> activeClasses = new ArrayList<>();

        for (Map.Entry<String, Entity> entity : world.getEntityMap().entrySet()) {
            if (!activeClasses.contains(entity.getValue().getClass())) {
                activeClasses.add(entity.getValue().getClass());
            }

            if (!classAssets.containsKey(entity.getValue().getClass())) {
                loadAssets(entity.getValue().getAssets(), entity.getValue().getClass());
            }
        }

        boolean isUpdated = super.update();

        if (isUpdated) {
            checkForUnusedAssets(activeClasses);
        } else {
            System.out.println("Loading assets: " + super.getProgress());
        }

        return isUpdated;
    }

    /**
     * Check for unused assets and unload them if needed
     *
     * @param activeEntityClasses Currently active entity classes
     */
    private void checkForUnusedAssets(ArrayList<Class> activeEntityClasses) {
        for (Map.Entry<Class, ArrayList<String>> classAsset : classAssets.entrySet()) {
            if (!activeEntityClasses.contains(classAsset.getKey())) {
                unloadClassAssets(classAsset.getKey());
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
     * Load assets without linking them to a class
     *
     * @param assets Assets mapped by their id
     */
    private void loadAssets(Map<String, Asset> assets) {
        for (Map.Entry<String, Asset> asset : assets.entrySet()) {
            loadAsset(asset.getValue());
        }
    }

    /**
     * Load assets and link them to the corresponding class
     *
     * @param assets Assets mapped by their id
     * @param c      Class for mapping all assets to the corresponding classes
     */
    private void loadAssets(Map<String, Asset> assets, Class c) {
        System.out.println("Loading assets for class: " + c);

        // Collection of all asset ids belonging to the entity
        ArrayList<String> assetIds = new ArrayList<>();

        for (Map.Entry<String, Asset> asset : assets.entrySet()) {
            loadAsset(asset.getValue());
            assetIds.add(asset.getValue().getId());
        }

        // Link asset to owner class
        classAssets.put(c, assetIds);
        System.out.println("classAssets size: " + classAssets.size());
    }

    /**
     * Load asset data and add to managed assets
     *
     * @param asset Asset to be loaded
     */
    private void loadAsset(Asset asset) {
        FileHandle file = new FileHandle(asset.getId());
        file.write(asset.getData(), false);
        asset.closeInputStream();

        Class assetClass = getAssetClass(asset.getAssetType());
        AssetDescriptor assetDescriptor = getAssetDescriptor(file, assetClass);

        super.load(assetDescriptor);
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

    /**
     * Unload all assets for an entity
     *
     * @param c Entity id
     */
    private void unloadClassAssets(Class c) {
        for (String assetId : classAssets.get(c)) {
            if (super.isLoaded(assetId)) {
                System.out.println("Unloading asset: " + assetId);
                super.unload(assetId);
            }
        }

        classAssets.remove(c);
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
    private <T> AssetDescriptor getAssetDescriptor(FileHandle file, Class type) {
        return new AssetDescriptor<T>(file, type);
    }
}
