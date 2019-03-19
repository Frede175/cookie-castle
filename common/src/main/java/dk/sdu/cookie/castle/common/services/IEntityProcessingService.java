package dk.sdu.cookie.castle.common.services;

import dk.sdu.cookie.castle.common.data.GameData;
import dk.sdu.cookie.castle.common.data.World;

public interface IEntityProcessingService {

    void process(GameData gameData, World world);
}
