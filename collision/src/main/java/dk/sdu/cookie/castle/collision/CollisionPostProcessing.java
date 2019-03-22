package dk.sdu.cookie.castle.collision;

import dk.sdu.cookie.castle.common.data.Entity;
import dk.sdu.cookie.castle.common.data.Entityparts.PositionPart;
import dk.sdu.cookie.castle.common.data.GameData;
import dk.sdu.cookie.castle.common.data.World;
import dk.sdu.cookie.castle.common.services.IPostEntityProcessingService;

import java.util.Arrays;
import java.util.Comparator;

public class CollisionPostProcessing implements IPostEntityProcessingService {
    private int sortAxis = 0;
    private Comparator<AB> comparator = this::compareAB;


    @Override
    public void process(GameData gameData, World world) {
        AB[] abarray = new AB[world.getEntities().size()];
        {
            int i = 0;
            for (Entity entity: world.getEntities()) {
                abarray[i++] = new AB(entity);
            }
        }


        Arrays.sort(abarray, comparator);

        float[] s = {0,0f, 0,0f};
        float[] s2 ={0,0f, 0,0f};
        float[] v = new float[2];

        for (int i = 0; i < abarray.length; i ++) {
            float[] p = new float[2];
            p[0] = 0.5f * (abarray[i].getMinPoint()[0] + abarray[i].getMaxPoint()[0]);
            p[1] = 0.5f * (abarray[i].getMinPoint()[1] + abarray[i].getMaxPoint()[1]);


            for(int c = 0; c < 2; c++) {
                s[c] += p[c];
                s2[c] += p[c] * p[c];

            }

            for(int j = i + 1; j < abarray.length; j++) {

                if(abarray[j].getMinPoint()[sortAxis] > abarray[i].getMaxPoint()[sortAxis]) break;
                if(overlap(abarray[j], abarray[i])) {
                    // TODO Collision
                }
            }
        }

        for(int c = 0; c < 2; c++) {
            v[c] = s2[c] - s[c] * s[c] / abarray.length;
        }

        sortAxis = 0;
        if(v[1] > v[0]) sortAxis = 1;
    }

    private int compareAB(AB ab_1, AB ab_2) {
        float minA = ab_1.getMinPoint()[sortAxis];
        float minB = ab_2.getMinPoint()[sortAxis];
        return Float.compare(minA, minB);
    }

    private boolean overlap(AB ab_1, AB ab_2) {
        //Checking sorting axis. (x or y axis)
        if (sortAxis == 0) { //Sorting on the x axis, checking overlap on the y axis
            return (ab_1.getMinPoint()[1] < ab_2.getMinPoint()[1] && ab_1.getMaxPoint()[1] > ab_2.getMinPoint()[0])
                    || (ab_2.getMinPoint()[1] < ab_1.getMinPoint()[1] && ab_2.getMaxPoint()[1] > ab_1.getMinPoint()[0]);
        } else { //Sorting on the y axis, checking overlap on the x axis
            return (ab_1.getMinPoint()[0] < ab_2.getMinPoint()[0] && ab_1.getMaxPoint()[0] > ab_2.getMinPoint()[0])
                    || (ab_2.getMinPoint()[0] < ab_1.getMinPoint()[0] && ab_2.getMaxPoint()[0] > ab_1.getMinPoint()[0]);
        }
    }
}
