package dk.sdu.cookie.castle.map;

import dk.sdu.cookie.castle.common.data.Entityparts.PositionPart;

import java.util.ArrayList;
import java.util.List;

// Class containing presets for rooms that can be generated "randomly" when game/level is initiated
public class RoomPreset {

    private List<PositionPart> enemyPositions;
    private List<PositionPart> itemPositions;
    private List<PositionPart> rockPositions;

    public RoomPreset() {
        enemyPositions = new ArrayList<>();
        itemPositions = new ArrayList<>();
        rockPositions = new ArrayList<>();
    }

    public void addEnemyPosition(PositionPart positionPart) {
        enemyPositions.add(positionPart);
    }

    public void addItemPosition(PositionPart positionPart) {
        itemPositions.add(positionPart);
    }

    public void addRockPosition(PositionPart positionPart) {
        rockPositions.add(positionPart);
    }

    public List<PositionPart> getEnemyPositions() {
        return enemyPositions;
    }

    public List<PositionPart> getItemPositions() {
        return itemPositions;
    }

    public List<PositionPart> getRockPositions() {
        return rockPositions;
    }
}
