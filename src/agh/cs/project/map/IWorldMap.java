package agh.cs.project.map;


import agh.cs.project.elements.Animal;
import agh.cs.project.elements.Genes;
import agh.cs.project.elements.Grass;
import agh.cs.project.elements.Vector2d;

import java.util.LinkedList;
import java.util.List;

public interface IWorldMap {

    Vector2d generateChildrenPosition(Vector2d parentsPosition);
    boolean inBorders(Vector2d position);
    boolean inJungle(Vector2d position);
    boolean isOccupied(Vector2d position);

    LinkedList<Animal> getAnimalsList();
    MapStatistics getMapStatistics();
    Vector2d getUpperRight();
    Vector2d getLowerLeft();
    List<Animal> getAnimalsToDraw();
    List<Grass> getGrassList();
    String toString();
    int getAge();
}