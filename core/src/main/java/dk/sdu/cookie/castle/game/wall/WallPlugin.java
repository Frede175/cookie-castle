package dk.sdu.cookie.castle.game.wall;

import dk.sdu.cookie.castle.common.data.Entity;
import dk.sdu.cookie.castle.common.data.Entityparts.CollisionPart;
import dk.sdu.cookie.castle.common.data.Entityparts.PositionPart;
import dk.sdu.cookie.castle.common.data.GameData;
import dk.sdu.cookie.castle.common.data.World;
import dk.sdu.cookie.castle.common.services.IGamePluginService;
import dk.sdu.cookie.castle.common.util.Vector2f;
import dk.sdu.cookie.castle.game.entities.OuterWall;

import static dk.sdu.cookie.castle.common.data.EntityType.WALL;

public class WallPlugin implements IGamePluginService {

    private OuterWall[] walls = new OuterWall[4];


    @Override
    public void start(GameData gameData, World world) {
        float marginTopBottom = 100;
        float marginLeftRight = 130;
        walls[0] = createWall(new Vector2f(0,720), new Vector2f(1280,720-marginTopBottom));
        walls[1] = createWall(new Vector2f(1280-marginLeftRight,720), new Vector2f(1280,0));
        walls[2] = createWall(new Vector2f(0,marginTopBottom), new Vector2f(1280, 0));
        walls[3] = createWall(new Vector2f(0,720), new Vector2f(marginLeftRight,0));

        for (OuterWall wall : walls) {
            world.addEntity(wall);
        }
    }

    private OuterWall createWall(Vector2f start, Vector2f end) {
        OuterWall wall = new OuterWall();
        wall.setIsActive(true);
        wall.setEntityType(WALL);
        wall.add(new PositionPart(start.getX(), start.getY(), 0));
        wall.add(new CollisionPart());
        float[] shapeX = new float[4];
        float[] shapeY = new float[4];

        shapeX[0] = start.getX();
        shapeX[1] = end.getX();
        shapeX[2] = end.getX();
        shapeX[3] = start.getX();
        shapeY[0] = start.getY();
        shapeY[1] = start.getY();
        shapeY[2] = end.getY();
        shapeY[3] = end.getY();
        wall.setShapeX(shapeX);
        wall.setShapeY(shapeY);
        wall.updateMinMax();

        return wall;

    }

    @Override
    public void stop(GameData gameData, World world) {
        for (Entity wall : walls) {
            world.removeEntity(wall);
        }
    }
}
