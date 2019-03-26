package dk.sdu.cookie.castle.collision;

import dk.sdu.cookie.castle.collision.util.MTV;
import dk.sdu.cookie.castle.collision.util.Projection;
import dk.sdu.cookie.castle.collision.util.Vector2;
import dk.sdu.cookie.castle.common.data.Entity;
import dk.sdu.cookie.castle.common.data.EntityType;
import dk.sdu.cookie.castle.common.data.Entityparts.CollisionPart;
import dk.sdu.cookie.castle.common.data.GameData;
import dk.sdu.cookie.castle.common.data.World;
import dk.sdu.cookie.castle.common.services.IPostEntityProcessingService;

import java.util.Arrays;
import java.util.Comparator;

public class CollisionPostProcessing implements IPostEntityProcessingService {
    private int sortAxis = 0;
    private Comparator<AABB> comparator = this::compareAB;


    @Override
    public void process(GameData gameData, World world) {
        AABB[] aabbArray = new AABB[world.getEntities().size()];
        {
            int i = 0;
            for (Entity entity : world.getEntities()) {
                aabbArray[i++] = new AABB(entity);
            }
        }


        Arrays.sort(aabbArray, comparator);


        float[] s = {0.0f, 0.0f};
        float[] s2 = {0.0f, 0.0f};
        float[] v = new float[2];

        for (int i = 0; i < aabbArray.length; i++) {
            float[] p = new float[2];
            p[0] = 0.5f * (aabbArray[i].getMinPoint()[0] + aabbArray[i].getMaxPoint()[0]);
            p[1] = 0.5f * (aabbArray[i].getMinPoint()[1] + aabbArray[i].getMaxPoint()[1]);


            for (int c = 0; c < 2; c++) {
                s[c] += p[c];
                s2[c] += p[c] * p[c];

            }

            for (int j = i + 1; j < aabbArray.length; j++) {

                if (aabbArray[j].getMinPoint()[sortAxis] > aabbArray[i].getMaxPoint()[sortAxis]) break;
                if (overlap(aabbArray[j], aabbArray[i])) {
                    MTV mtv = preciseCollision(aabbArray[j], aabbArray[i]);
                    if (mtv != null) {
                        CollisionPart collisionPart1 = aabbArray[j].getEntity().getPart(CollisionPart.class);
                        CollisionPart collisionPart2 = aabbArray[i].getEntity().getPart(CollisionPart.class);

                        //Calc which object need to move
                        boolean entity1CanMove = canMove(aabbArray[i].getEntity());
                        boolean entity2CanMove = canMove(aabbArray[j].getEntity());

                        //TODO Move objects!

                        collisionPart1.setHit(true);
                        collisionPart2.setHit(true);
                        collisionPart1.setCollidingEntity(aabbArray[i].getEntity());
                        collisionPart2.setCollidingEntity(aabbArray[j].getEntity());
                    }
                }
            }
        }

        for (int c = 0; c < 2; c++) {
            v[c] = s2[c] - s[c] * s[c] / aabbArray.length;
        }

        sortAxis = 0;
        if (v[1] > v[0]) sortAxis = 1;
    }

    private boolean canMove(Entity entity) {
        return entity.getEntityType() == EntityType.DOOR ||
                entity.getEntityType() == EntityType.REMOVABLE_OBSTACLE ||
                entity.getEntityType() == EntityType.STATIC_OBSTACLE ||
                entity.getEntityType() == EntityType.WALL;
    }

    private int compareAB(AABB aabb1, AABB aabb2) {
        float minA = aabb1.getMinPoint()[sortAxis];
        float minB = aabb2.getMinPoint()[sortAxis];
        return Float.compare(minA, minB);
    }

    private boolean overlap(AABB aabb1, AABB aabb2) {
        //Checking sorting axis. (x or y axis)
        if (sortAxis == 0) { //Sorting on the x axis, checking overlap on the y axis
            return (aabb1.getMinPoint()[1] < aabb2.getMinPoint()[1] && aabb1.getMaxPoint()[1] > aabb2.getMinPoint()[1])
                    || (aabb2.getMinPoint()[1] < aabb1.getMinPoint()[1] && aabb2.getMaxPoint()[1] > aabb1.getMinPoint()[1]);
        } else { //Sorting on the y axis, checking overlap on the x axis
            return (aabb1.getMinPoint()[0] < aabb2.getMinPoint()[0] && aabb1.getMaxPoint()[0] > aabb2.getMinPoint()[0])
                    || (aabb2.getMinPoint()[0] < aabb1.getMinPoint()[0] && aabb2.getMaxPoint()[0] > aabb1.getMinPoint()[0]);
        }
    }

    private Vector2 smallestAxis;
    private float overlap;

    private MTV preciseCollision(AABB aabb1, AABB aabb2) {
        smallestAxis = null;
        overlap = Float.MAX_VALUE;
        Vector2[] points1 = getPoints(aabb1.getEntity().getShapeX(), aabb1.getEntity().getShapeY());
        Vector2[] points2 = getPoints(aabb2.getEntity().getShapeX(), aabb2.getEntity().getShapeY());

        Vector2[] axis1 = getAxes(points1);
        Vector2[] axis2 = getAxes(points2);

        if (checkProjectionOverlap(points1, points2, axis1) && checkProjectionOverlap(points1, points2, axis2)) {
            return new MTV(smallestAxis, overlap);
        }
        return null;
    }

    private boolean checkProjectionOverlap(Vector2[] points1, Vector2[] points2, Vector2[] axis1) {
        for (Vector2 axis : axis1) {
            Projection p1 = project(axis, points1);
            Projection p2 = project(axis, points2);
            if (!p1.overlap(p2)) return false;
            else {
                //Getting overlap
                float o = p1.getOverlap(p2);
                if (o < overlap) {
                    overlap = o;
                    smallestAxis = axis;
                }
            }
        }
        return true;
    }

    private Vector2[] getPoints(float[] shapeX, float[] shapeY) {
        Vector2[] points = new Vector2[shapeX.length];
        for (int i = 0; i < shapeX.length; i++) {
            points[i] = new Vector2(shapeX[i], shapeY[i]);
        }
        return points;
    }

    private Vector2[] getAxes(Vector2[] edges) {
        Vector2[] axis = new Vector2[edges.length];
        for (int i = 0; i < axis.length; i++) {
            Vector2 p1 = edges[i];
            Vector2 p2 = edges[(i + 1) % axis.length];

            axis[i] = p1.subtract(p2).perp().normalize();
        }
        return axis;
    }

    private Projection project(Vector2 axis, Vector2[] shape) {
        float min = axis.dot(shape[0]);
        float max = min;

        for (int i = 1; i < shape.length; i++) {
            float value = axis.dot(shape[i]);

            if (value < min) min = value;
            else if (value > max) max = value;
        }
        return new Projection(min, max);
    }
}
