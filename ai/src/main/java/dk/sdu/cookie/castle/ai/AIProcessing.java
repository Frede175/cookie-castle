package dk.sdu.cookie.castle.ai;

import dk.sdu.cookie.castle.common.data.*;
import dk.sdu.cookie.castle.common.data.Entityparts.AIMovingPart;
import dk.sdu.cookie.castle.common.data.Entityparts.PositionPart;
import dk.sdu.cookie.castle.common.services.IEntityProcessingService;
import dk.sdu.cookie.castle.common.util.Vector2f;

import java.util.LinkedList;

public class AIProcessing implements IEntityProcessingService {
    private AStar aStar;

    public AIProcessing() {
        aStar = Activator.getaStar();
    }

    /**
     * Check for every enemy, calculate the path, and give the all the enemies' movingparts the next move direction
     * @param gameData
     * @param world
     */
    @Override
    public void process(GameData gameData, World world) {
        aStar.updateGrid(world, gameData);
        Entity player = null;
        PositionPart playerPos = null;
        // Finds the player, and the position of the player on the map
        for (Entity p : world.getEntities()) {
            if (p.getEntityType() == EntityType.PLAYER) {
                player = p;
                playerPos = player.getPart(PositionPart.class);
                break;
            }
        }
        
        if (player == null) return;

        // Finds all enemies, and get a hold of their movingpart
        for (Entity enemy : world.getEntities()) {
            if (enemy.getEntityType() == EntityType.ENEMY) {
                AIMovingPart AIMovingPart = enemy.getPart(AIMovingPart.class);

                // Checks if the timelimit for updating the path has been exceeded
                // Updates the list of points to walk to get to the player
                if (AIMovingPart.needUpdate()) {
                    PositionPart positionPart = enemy.getPart(PositionPart.class);
                    LinkedList<Vector2f> route = aStar.calculateRoute(new Vector2f(positionPart.getX(), positionPart.getY()), new Vector2f(playerPos.getX(), playerPos.getY()));
                    AIMovingPart.setRoute(route);
                }
            }

        }
    }
}
