package dk.sdu.cookie.castle.player;

import dk.sdu.cookie.castle.common.assets.Asset;
import dk.sdu.cookie.castle.common.data.Entity;

import java.util.Map;

class Player extends Entity {
    Player(Class c, Map<String, Asset> assets) {
        super(c, assets);
    }
}
