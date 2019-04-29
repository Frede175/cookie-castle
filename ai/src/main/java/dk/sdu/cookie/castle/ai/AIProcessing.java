package dk.sdu.cookie.castle.ai;

import dk.sdu.cookie.castle.common.data.*;
import dk.sdu.cookie.castle.common.data.Entityparts.AIMovingPart;
import dk.sdu.cookie.castle.common.data.Entityparts.PositionPart;
import dk.sdu.cookie.castle.common.services.IEntityProcessingService;

import java.util.LinkedList;

public class AIProcessing implements IEntityProcessingService {
    private AStar aStar;

    public AIProcessing() {
        aStar = Activator.getaStar();
    }

    // Check for every enemy, calculate the path, and give the enemys movingpart the next move direction
    @Override
    public void process(GameData gameData, World world) {
        aStar.updateGrid(world, gameData);
        Entity player = null;
        PositionPart playerPos = null;
        // Change to find direct player class
        for (Entity p : world.getEntities()) {
            if (p.getEntityType() == EntityType.PLAYER) {
                player = p;
                playerPos = player.getPart(PositionPart.class);
                break;
            }
        }

        if (player == null) return;

        // Change to find direct enemy class
        for (Entity enemy : world.getEntities()) {
            if (enemy.getEntityType() == EntityType.ENEMY) {
                AIMovingPart AIMovingPart = enemy.getPart(AIMovingPart.class);

                if (AIMovingPart.needUpdate()) {
                    PositionPart positionPart = enemy.getPart(PositionPart.class);
                    LinkedList<Point> route = aStar.calculateRoute(new Point(positionPart.getX(), positionPart.getY()), new Point(playerPos.getX(), playerPos.getY()));
                    AIMovingPart.setRoute(route);
                }
            }

        }
    }
}
