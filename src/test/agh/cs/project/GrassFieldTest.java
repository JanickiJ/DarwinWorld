package test.agh.cs.project;

import agh.cs.project.elements.Animal;
import agh.cs.project.elements.Grass;
import agh.cs.project.map.GrassField;
import agh.cs.project.elements.Vector2d;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GrassFieldTest {

    @Test
    public void constructorTest() {
        GrassField map = new GrassField(3,3,3);
        assertEquals(new Vector2d(0,0), map.getLowerLeft());
        assertEquals(new Vector2d(3,3),map.getUpperRight());
        assertEquals(new Vector2d(1,1),map.getLowerLeftJungle());
        assertEquals(new Vector2d(2,2),map.getUpperRightJungle());

        map = new GrassField(4, 4, 2);
        assertEquals(new Vector2d(0,0), map.getLowerLeft());
        assertEquals(new Vector2d(4,4),map.getUpperRight());
        assertEquals(new Vector2d(1,1),map.getLowerLeftJungle());
        assertEquals(new Vector2d(3,3),map.getUpperRightJungle());

        map = new GrassField(4, 4,  4);
        assertEquals(new Vector2d(0,0), map.getLowerLeft());
        assertEquals(new Vector2d(4,4),map.getUpperRight());
        assertEquals(new Vector2d(2,2),map.getLowerLeftJungle());
        assertEquals(new Vector2d(3,3),map.getUpperRightJungle());

        map = new GrassField(4, 4,  3);
        assertEquals(new Vector2d(0,0), map.getLowerLeft());
        assertEquals(new Vector2d(4,4),map.getUpperRight());
        assertEquals(new Vector2d(2,2),map.getLowerLeftJungle());
        assertEquals(new Vector2d(3,3),map.getUpperRightJungle());

    }

    @Test
    public void inBordersTest() {
        GrassField map = new GrassField(4,4,3);
        assertFalse(map.inBorders(new Vector2d(-1, 0)));
        assertFalse(map.inBorders(new Vector2d(2, 5)));
        assertTrue(map.inBorders(new Vector2d(4, 4)));
    }

    @Test
    public void placeTest(){
        GrassField map = new GrassField(3,3,3);
        Animal a1 = new Animal(map,new Vector2d(1,1),2,0);
        Animal a2 = new Animal(map,new Vector2d(1,1),2,0);
        Animal a3 = new Animal(map,new Vector2d(4,1),2,0);

        assertTrue(map.place(a1));
        assertFalse(map.place(a2));
        assertFalse(map.place(a3));
    }

    @Test
    public void objectAtTest(){
        GrassField map = new GrassField(1,1,3);
        Animal a1 = new Animal(map,new Vector2d(1,1),2,0);
        Animal a2 = new Animal(map,new Vector2d(1,1),2,0);

        map.place(a1);
        map.place(a2);

        assertEquals(a1,map.objectAt(new Vector2d(1,1)));
        map.place(new Grass(new Vector2d(0,0)));
        assertTrue(map.objectAt(new Vector2d(0,0)) instanceof Grass);
    }

    @Test
    public void isOccupiedTest(){
        GrassField map = new GrassField(1,1,3);
        Animal a1 = new Animal(map,new Vector2d(1,1),2,0);
        Animal a2 = new Animal(map,new Vector2d(1,1),2,0);

        map.place(a1);
        map.place(a2);

        map.place(new Grass(new Vector2d(0,0)));
        assertTrue(map.isOccupied(new Vector2d(0,0)));
        assertTrue(map.isOccupied(new Vector2d(1,1)));
    }

    @Test
    public void generateChildrenPositionTest(){
        GrassField map = new GrassField(1,1,3);
        Animal a1 = new Animal(map,new Vector2d(1,1),2,0);
        Animal a2 = new Animal(map,new Vector2d(0,0),2,0);
        Animal a3 = new Animal(map,new Vector2d(1,0),2,0);
        map.place(a1);
        map.place(a2);
        map.place(a3);
        assertEquals(new Vector2d(0, 1), map.generateChildrenPosition(a1.getPosition()));
    }


}
