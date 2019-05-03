package dk.sdu.cookie.castle.map;

import dk.sdu.cookie.castle.common.data.Entityparts.PositionPart;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RoomPresetGenerator {
    private List<RoomPreset> roomPresets;
    private Random random;
    private int numberOfPresets = 2;

    public RoomPresetGenerator() {
        roomPresets = new ArrayList<>();
        random = new Random();
        loadPresets();
    }

    private void loadPresets() {
        for (int i = 1; i <= numberOfPresets; i++) {
            try (InputStream is = this.getClass().getResourceAsStream("/preset" + i + ".txt")) {
                System.out.println("Does exist: " + is.available());
                InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
                BufferedReader br = new BufferedReader(isr);
                List<String> entities = new ArrayList<>();
                br.lines().forEach(line -> entities.add(line));
                RoomPreset roomPreset = new RoomPreset();
                for (String entity : entities) {
                    String[] entityData = entity.split(" ");
                    PositionPart positionPart = new PositionPart(Integer.valueOf(entityData[1]), Integer.valueOf(entityData[2]), 0);
                    switch (Integer.valueOf(entityData[0])) {
                        case 1:
                            roomPreset.addEnemyPosition(positionPart);
                            break;
                        case 2:
                            roomPreset.addItemPosition(positionPart);
                            break;
                        case 3:
                            roomPreset.addRockPosition(positionPart);
                            break;
                    }
                }
                roomPresets.add(roomPreset);
            } catch (IOException e) {
                System.out.println("Doesn't exist");
                e.printStackTrace();
            }
        }
    }

    public RoomPreset getRandomRoomPreset() {
        return roomPresets.get(random.nextInt((roomPresets.size())));
    }
}
