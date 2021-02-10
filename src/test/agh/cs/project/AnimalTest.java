package test.agh.cs.project;
import agh.cs.project.elements.Animal;
import agh.cs.project.elements.Genes;
import agh.cs.project.map.GrassField;
import agh.cs.project.elements.Vector2d;
import agh.cs.project.map.IWorldMap;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AnimalTest {
    @Test
    public void moveTest() {

        IWorldMap map = new GrassField(5,5,3);
        Animal a = new Animal(map, new Vector2d(0, 0), 0, new Genes(),0);
        a.move();
        assertNotEquals(new Vector2d(0, 0), a.getPosition());

        Animal a2 = new Animal(map, new Vector2d(1, 1), 0, new Genes(),0);
        a2.move();
        assertNotEquals(new Vector2d(1, 1), a.getPosition());

        IWorldMap map2 = new GrassField(1,1,1);
        Animal a3 = new Animal(map2, new Vector2d(0,0),10, new Genes(),0);
        a3.move();
        assertNotEquals(new Vector2d(0, 0), a3.getPosition());
    }

    @Test
    public void copulateTest(){
        IWorldMap map2 = new GrassField(1,1,1);
        Animal a1 = new Animal(map2, new Vector2d(0, 0), 10, new Genes(),0);
        Animal a2 = new Animal(map2, new Vector2d(0, 0), 20, new Genes(),0);

        Animal a3 = a1.copulate(a2);

        assertEquals(a3.getEnergy(), 7);
        assertEquals(a1.getEnergy(), 8);
        assertEquals(a2.getEnergy(), 15);
    }
    @Test
    public void isDeadTest(){
        IWorldMap map2 = new GrassField(1,1,1);
        Animal a1 = new Animal(map2, new Vector2d(0, 0), 1, new Genes(),0);
        Animal a2 = new Animal(map2, new Vector2d(0, 0), 0, new Genes(),0);
        Animal a3 = new Animal(map2, new Vector2d(0, 0), -1, new Genes(),0);

        assertFalse(a1.isDead());
        assertTrue(a2.isDead());
        assertTrue(a3.isDead());
    }




}
