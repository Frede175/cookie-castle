package dk.sdu.cookie.castle.map;

import dk.sdu.cookie.castle.common.data.Entityparts.PositionPart;
import dk.sdu.cookie.castle.map.entities.EntityPreset;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// Class containing presets for rooms that can be generated "randomly" when game/level is initiated
class RoomPreset {
    private Map<EntityPreset, List<PositionPart>> entityPresetPositions;

    RoomPreset() {
        entityPresetPositions = new ConcurrentHashMap<>();

        for (EntityPreset entityPreset : EntityPreset.values()) {
            entityPresetPositions.put(entityPreset, new ArrayList<>());
        }
    }

    void addEntityPosition(EntityPreset entityPreset, PositionPart position) {
        entityPresetPositions.get(entityPreset).add(position);
    }

    Map<EntityPreset, List<PositionPart>> getEntityPositions() {
        return entityPresetPositions;
    }

    List<PositionPart> getEntityPositions(EntityPreset entityPreset) {
        return entityPresetPositions.get(entityPreset);
    }
}
