package dk.sdu.cookie.castle.common.assets;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AssetLoader {
    /**
     * Load data for specified assets and return Map of references
     *
     * @param c      Caller class for the getting resources from its classloader
     * @param assets Map of assets to have their data loaded
     */
    public static Map<String, String> loadAssets(Class c, Map<String, Asset> assets) {
        Map<String, String> returnMap = new ConcurrentHashMap<>();
        for (Map.Entry<String, Asset> asset : assets.entrySet()) {
            loadAssetData(c, asset.getValue());
            returnMap.put(asset.getValue().getName(), asset.getValue().getId());
        }

        return returnMap;
    }

    private static void loadAssetData(Class c, Asset asset) {
        System.out.println("Getting asset: " + asset);
        asset.setData(c.getResourceAsStream(asset.getPath()));

        try {
            assert (asset.getData().available() != 0);
        } catch (IOException e) {
            System.out.println("File not found - try reimporting Maven and rebuild");
            e.printStackTrace();
        }
    }
}
