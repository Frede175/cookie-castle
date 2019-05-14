package dk.sdu.cookie.castle.map.entities;

public enum EntityPreset {
    ENEMY(1),
    ITEM(2),
    ROCK(3);

    /**
     * Represents the key from the preset text file
     */
    private int key;

    EntityPreset(int key) {
        this.key = key;
    }

    public static EntityPreset getPreset(int key) {
        EntityPreset returnPreset = null;

        for (EntityPreset preset : EntityPreset.values()) {
            if (preset.key == key) returnPreset = preset;
        }

        return returnPreset;
    }

    public int getKey() {
        return key;
    }
}
