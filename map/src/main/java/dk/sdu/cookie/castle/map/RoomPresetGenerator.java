package dk.sdu.cookie.castle.map;

import dk.sdu.cookie.castle.common.data.Entityparts.PositionPart;
import dk.sdu.cookie.castle.map.entities.EntityPreset;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class RoomPresetGenerator {
    private List<RoomPreset> roomPresets;
    private Random random;
    private int numberOfPresets = 2;

    RoomPresetGenerator() {
        roomPresets = new ArrayList<>();
        random = new Random();
        loadPresets();
    }

    private void loadPresets() {
        for (int i = 1; i <= numberOfPresets; i++) {
            try (InputStream is = this.getClass().getResourceAsStream("/preset" + i + ".txt")) {
                InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
                BufferedReader br = new BufferedReader(isr);
                List<String> entities = new ArrayList<>();
                br.lines().forEach(entities::add);
                roomPresets.add(createRoomPreset(entities));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Create a room preset from provided strings
     *
     * @param entities Specifies EntityPreset key and coordinates of an entity
     * @return RoomPreset populated with entity positions
     */
    private RoomPreset createRoomPreset(List<String> entities) {
        RoomPreset roomPreset = new RoomPreset();

        for (String entity : entities) {
            String[] entityData = entity.split(" ");
            PositionPart positionPart = new PositionPart(Integer.valueOf(entityData[1]), Integer.valueOf(entityData[2]), 0);

            try {
                roomPreset.addEntityPosition(EntityPreset.getPreset(Integer.valueOf(entityData[0])), positionPart);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return roomPreset;
    }

    RoomPreset getRandomRoomPreset() {
        return roomPresets.get(random.nextInt((roomPresets.size())));
    }
}
