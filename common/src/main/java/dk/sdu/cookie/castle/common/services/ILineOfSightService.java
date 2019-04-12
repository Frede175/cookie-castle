package dk.sdu.cookie.castle.common.services;

import dk.sdu.cookie.castle.common.data.World;
import dk.sdu.cookie.castle.common.util.Vector2f;


public interface ILineOfSightService {
    /**
     * @param world The Data of the world
     * @param start The starting point of the line to be checked
     * @param end   The ending point of the line to be checked
     * @return Returns true if the two points are in line of sight of eachother. False if not.
     */
    boolean isInlineOfSight(World world, Vector2f start, Vector2f end);
}
