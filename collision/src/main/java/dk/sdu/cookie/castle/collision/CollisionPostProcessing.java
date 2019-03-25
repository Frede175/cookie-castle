package dk.sdu.cookie.castle.collision;

import dk.sdu.cookie.castle.common.data.Entity;
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
                    CollisionPart collisionPart1 = aabbArray[j].getEntity().getPart(CollisionPart.class);
                    CollisionPart collisionPart2 = aabbArray[i].getEntity().getPart(CollisionPart.class);

                    collisionPart1.setHit(true);
                    collisionPart2.setHit(true);
                    collisionPart1.setCollidingEntity(aabbArray[i].getEntity());
                    collisionPart2.setCollidingEntity(aabbArray[j].getEntity());
                }
            }
        }

        for (int c = 0; c < 2; c++) {
            v[c] = s2[c] - s[c] * s[c] / aabbArray.length;
        }

        sortAxis = 0;
        if (v[1] > v[0]) sortAxis = 1;
    }

    private int compareAB(AABB AABB_1, AABB AABB_2) {
        float minA = AABB_1.getMinPoint()[sortAxis];
        float minB = AABB_2.getMinPoint()[sortAxis];
        return Float.compare(minA, minB);
    }

    private boolean overlap(AABB AABB_1, AABB AABB_2) {
        //Checking sorting axis. (x or y axis)
        if (sortAxis == 0) { //Sorting on the x axis, checking overlap on the y axis
            return (AABB_1.getMinPoint()[1] < AABB_2.getMinPoint()[1] && AABB_1.getMaxPoint()[1] > AABB_2.getMinPoint()[1])
                    || (AABB_2.getMinPoint()[1] < AABB_1.getMinPoint()[1] && AABB_2.getMaxPoint()[1] > AABB_1.getMinPoint()[1]);
        } else { //Sorting on the y axis, checking overlap on the x axis
            return (AABB_1.getMinPoint()[0] < AABB_2.getMinPoint()[0] && AABB_1.getMaxPoint()[0] > AABB_2.getMinPoint()[0])
                    || (AABB_2.getMinPoint()[0] < AABB_1.getMinPoint()[0] && AABB_2.getMaxPoint()[0] > AABB_1.getMinPoint()[0]);
        }
    }
}
