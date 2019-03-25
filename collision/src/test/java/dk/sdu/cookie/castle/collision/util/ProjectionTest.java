package dk.sdu.cookie.castle.collision.util;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ProjectionTest {

    @Test
    public void shouldOverlap() {
        Projection p1 = new Projection(0,10);
        Projection p2 = new Projection(5, 15);

        assertTrue(p1.overlap(p2));
        assertTrue(p2.overlap(p1));
    }

    @Test
    public void shouldNotOverlap() {
        Projection p1 = new Projection(0,10);
        Projection p2 = new Projection(11, 15);

        assertFalse(p1.overlap(p2));
        assertFalse(p2.overlap(p1));
    }

    @Test
    public void getOverlapShouldEquals5() {
        Projection p1 = new Projection(0,10);
        Projection p2 = new Projection(5, 15);

        assertEquals(5f, p1.getOverlap(p2));
        assertEquals(5f, p2.getOverlap(p1));
    }

    @Test
    public void getOverlapShouldEquals0() {
        Projection p1 = new Projection(0,10);
        Projection p2 = new Projection(11, 15);

        assertEquals(0f, p1.getOverlap(p2));
        assertEquals(0f, p2.getOverlap(p1));
    }


}
