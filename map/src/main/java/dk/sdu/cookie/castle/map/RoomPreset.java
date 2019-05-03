package dk.sdu.cookie.castle.map;

import dk.sdu.cookie.castle.common.data.Entityparts.PositionPart;

import java.util.ArrayList;
import java.util.List;

// Class containing presets for rooms that can be generated "randomly" when game/level is initiated
public class RoomPreset {

    List<PositionPart> enemyPositions;
    List<PositionPart> itemPositions;
    List<PositionPart> rocks;

    public RoomPreset() {
        enemyPositions = new ArrayList<>();
        itemPositions = new ArrayList<>();
        rocks = new ArrayList<>();
    }
}
