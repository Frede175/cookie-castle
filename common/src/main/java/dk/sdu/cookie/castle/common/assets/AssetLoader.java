package dk.sdu.cookie.castle.common.assets;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AssetLoader {
    /**
     * Load data for specified assets and return Map of references
     *
     * @param c      Caller class for the getting resources from its classloader
     * @param assets Map of assets to have their data loaded
     */
    public static Map<String, String> loadAssets(Class c, Collection<Asset> assets) {
        Map<String, String> returnMap = new ConcurrentHashMap<>();
        for (Asset asset : assets) {
            loadAssetData(c, asset);
            returnMap.put(asset.getName(), asset.getId());
        }

        return returnMap;
    }

    private static void loadAssetData(Class c, Asset asset) {
        asset.setData(c.getResourceAsStream(asset.getPath()));
        try {
            assert (asset.getData().available() != 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
