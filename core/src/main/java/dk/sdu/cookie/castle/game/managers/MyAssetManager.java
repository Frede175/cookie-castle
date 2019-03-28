package dk.sdu.cookie.castle.game.managers;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import dk.sdu.cookie.castle.common.assets.Asset;
import dk.sdu.cookie.castle.common.assets.AssetHelper;
import dk.sdu.cookie.castle.common.assets.AssetType;
import dk.sdu.cookie.castle.common.assets.FileType;
import dk.sdu.cookie.castle.common.data.Entity;
import dk.sdu.cookie.castle.common.data.World;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MyAssetManager {
    //    public final AssetManager manager = new AssetManager();
    private Map<String, Texture> textures;
    private String backgroundId;

    public MyAssetManager() {
        textures = new ConcurrentHashMap<>();
        setLocalAssets();
        System.out.println("Asset manager created");
    }

    private void setLocalAssets() {
        // Create local assets
        Map<String, Asset> assets = new ConcurrentHashMap<>();
        Asset background = new Asset("background", AssetType.TEXTURE, FileType.PNG);
        assets.put(background.getId(), background);
        backgroundId = background.getId();

        AssetHelper.loadAssetData(this.getClass(), assets);
        loadAssets(assets);
    }

    public Texture getTexture(String id) {
        return textures.get(id);
    }

    public void loadEntities(World world) {
        System.out.println("Loading entities: " + world.getEntities().size());
        for (Entity entity : world.getEntities()) {
            loadAssets(entity.getAssets());
        }
    }

    private void loadAssets(Map<String, Asset> assets) {
        for (Map.Entry<String, Asset> asset : assets.entrySet()) {
            if (asset.getValue().getAssetType() == AssetType.TEXTURE) {
                loadTexture(asset.getValue());
            }
        }
    }

    /*
    Load texture from asset and add to texture collection
     */
    private void loadTexture(Asset asset) {
        System.out.println("Loading texture: " + asset.getName());
        FileHandle file = new FileHandle(asset.getName());

        System.out.println("Texture exists: " + file.exists());
        file.write(asset.getData(), false);
        textures.put(asset.getId(), new Texture(file));
    }

    public Texture getBackground() {
        return textures.get(backgroundId);
    }
}
