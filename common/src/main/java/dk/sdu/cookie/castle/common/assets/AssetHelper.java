package dk.sdu.cookie.castle.common.assets;

import java.io.IOException;
import java.util.Map;

public class AssetHelper {
    public static void loadAssetData(Class c, Map<String, Asset> assets) {
        for (Map.Entry<String, Asset> asset : assets.entrySet()) {
            System.out.println("Getting asset: " + asset.getKey());
            asset.getValue().setData(c.getResourceAsStream(asset.getValue().getPath()));

            try {
                System.out.println("File size: " + asset.getValue().getData().available() + " bytes");
            } catch (IOException e) {
                System.out.println("File not found - try reimporting Maven and rebuild");
                e.printStackTrace();
            }
        }
    }
}
