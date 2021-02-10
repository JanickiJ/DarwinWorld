package agh.cs.project.map;

import agh.cs.project.elements.Animal;

import java.util.LinkedList;
import java.util.SortedSet;
import java.util.TreeSet;

public class MapCell{
    private final SortedSet<Animal> animals;

    public MapCell() {
        animals = new TreeSet<>((elm1, elm2) -> {
            if (elm1.equals(elm2)) return 0;
            int x1 = elm1.getEnergy();
            int x2 = elm2.getEnergy();
            if (x1 > x2)
                return -1;
            if (x1 < x2)
                return 1;
            int y1 = elm1.getID();
            int y2 = elm2.getID();
            if (y1 < y2)
                return 1;
            if (y1 > y2)
                return -1;
            return -1;
        });
    }

    public LinkedList<Animal> getAnimalsToFeed(){
        LinkedList<Animal> animalsToFeed = new LinkedList<>();
        animalsToFeed.add(animals.last());
        for(Animal animal : animals){
            if(!animalsToFeed.contains(animal) && animalsToFeed.getLast().getEnergy() == animal.getEnergy()){
                animalsToFeed.add(animal);
            }
        }
        return animalsToFeed;
    }

    public LinkedList<Animal> getAnimalsToCopulate(){
        LinkedList<Animal> animalsToCopulate = new LinkedList<>();
        if(animals.size() >1) {
            Animal parent1 = animals.last();
            animals.remove(parent1);
            Animal parent2 = animals.last();


            animals.add(parent1);
            animalsToCopulate.add(parent1);
            animalsToCopulate.add(parent2);
        }
        return animalsToCopulate;
    }

    public Animal getStrongestAnimal(){
        return animals.first();
    }
    public int getSize(){
        return animals.size();
    }

    public void addAnimal(Animal animal){
        animals.add(animal);
    }
    public void removeAnimal(Animal animal){
        animals.remove(animal);
    }
}
