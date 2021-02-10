package test.agh.cs.project;

import agh.cs.project.elements.Vector2d;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Vector2dTest {
    private final Vector2d lowerLeft = new Vector2d(0,0);
    private final Vector2d upperRight = new Vector2d(5,5);

    @Test
    public void repairTest(){
        Vector2d v1 = new Vector2d(4,4);
        Vector2d u1 = new Vector2d(4,4);
        assertEquals(v1.repair(lowerLeft, upperRight), u1);

        Vector2d v2 = new Vector2d(5,6);
        Vector2d u2 = new Vector2d(5,0);
        assertEquals(v2.repair(lowerLeft, upperRight), u2);

        Vector2d v3 = new Vector2d(6,6);
        Vector2d u3 = new Vector2d(0,0);
        assertEquals(v3.repair(lowerLeft, upperRight), u3);

        Vector2d v4 = new Vector2d(-1,6);
        Vector2d u4 = new Vector2d(5,0);
        assertEquals(v4.repair(lowerLeft, upperRight), u4);

        Vector2d v5 = new Vector2d(-1,-1);
        Vector2d u5 = new Vector2d(5,5);
        assertEquals(v5.repair(lowerLeft, upperRight), u5);


        Vector2d v6 = new Vector2d(6,5);
        Vector2d u6 = new Vector2d(0,5);
        assertEquals(v6.repair(lowerLeft, upperRight), u6);


        Vector2d v7 = new Vector2d(-1,5);
        Vector2d u7 = new Vector2d(5,5);
        assertEquals(v7.repair(lowerLeft, upperRight), u7);
    }

}
