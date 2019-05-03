package dk.sdu.cookie.castle.map;

import dk.sdu.cookie.castle.common.data.Entityparts.PositionPart;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RoomPresetGenerator {
    private List<RoomPreset> roomPresets;
    private Random random;

    public RoomPresetGenerator() {
        roomPresets = new ArrayList<>();
        random = new Random();
        getFileFromResources();
    }

    private void getFileFromResources() {
        try (InputStream is = this.getClass().getResourceAsStream("/preset1.txt")) {
            System.out.println("Does exist: " + is.available());
            InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);
            List<String> str = new ArrayList<>();
            br.lines().forEach(line -> str.add(line));
            for (String temp : str) {
                String[] strings = temp.split(" ");
                switch (Integer.valueOf(strings[0])) {
                    case 1:
                        System.out.println("rock");
                        break;
                    case 2:
                        System.out.println("enemy");
                        break;
                    case 3:
                        System.out.println("item");
                }
            }
        } catch (IOException e) {
            System.out.println("Doesn't exist");
            e.printStackTrace();
        }
    }

    public RoomPreset getRandomRoomPreset() {
        return roomPresets.get(random.nextInt((roomPresets.size()) + 1));
    }
}
