package dk.sdu.cookie.castle.common.assets;

import java.io.InputStream;
import java.util.UUID;

public class Asset {
    private final UUID ID = UUID.randomUUID();
    private String name;
    private AssetType assetType;
    private FileType fileType;
    private InputStream data;

    public Asset(String name, AssetType assetType, FileType type) {
        this.assetType = assetType;
        this.name = name;
        this.fileType = type;
    }

    public String getId() {
        return ID.toString();
    }

    public String getName() {
        return name;
    }

    public AssetType getAssetType() {
        return assetType;
    }

    void setData(InputStream inputStream) {
        this.data = inputStream;
    }

    public InputStream getData() {
        return data;
    }

    String getPath() {
        return assetType.getPath() + name + fileType.getExtension();
    }
}
