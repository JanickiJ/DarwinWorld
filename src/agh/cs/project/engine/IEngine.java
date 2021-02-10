package agh.cs.project.engine;


import agh.cs.project.map.IWorldMap;
import agh.cs.project.elements.Animal;
import agh.cs.project.elements.Grass;

import java.util.List;

public interface IEngine {
    void run();
    IWorldMap getIWorldMap();
    List<Animal> getAnimalsToDraw();
    List<Grass> getGrassToDraw();
    int getAge();
}