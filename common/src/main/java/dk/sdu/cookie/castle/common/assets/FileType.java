package dk.sdu.cookie.castle.common.assets;

public enum FileType {
    PNG(".png"),
    JPG(".jpg");

    private String extension;

    FileType(String extension) {
        this.extension = extension;
    }

    public String getExtension() {
        return this.extension;
    }
}
