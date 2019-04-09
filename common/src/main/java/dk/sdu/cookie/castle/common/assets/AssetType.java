package dk.sdu.cookie.castle.common.assets;

public enum AssetType {
    TEXTURE("/images/");

    private String path;

    AssetType(String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }
}
