package agh.cs.project.engine;


import agh.cs.project.map.IWorldMap;
import agh.cs.project.elements.Animal;
import agh.cs.project.elements.Grass;
import agh.cs.project.map.GrassField;

import java.util.LinkedList;
import java.util.List;

public class SimulationEngine implements IEngine {

    private final GrassField map;
    private final OptionsParser parameters;
    private int age;

    public SimulationEngine(OptionsParser parameters) {
        this.parameters = parameters;
        map = new GrassField(parameters.getWidth()-1, parameters.getHeight()-1,parameters.getJungleRatio());
        placeRandomAnimals(parameters.getStartNumOfAnimals());
        age = 0;
    }

    @Override
    public void run() {
        removeDeadAnimals();
        moving();
        eating();
        copulation();
        losingEnergy();
        placeRandomGrass();
        age += 1;
        map.setAge(age);
    }

    public void removeDeadAnimals() {
        LinkedList<Animal> animalsToRemove = new LinkedList<>();
        for (Animal animal : map.getAnimalsList()) {
            if (animal.isDead()) {
                animalsToRemove.add(animal);
            }
        }

        for (Animal animal : animalsToRemove) {
            map.removeIMapElement(animal);
        }
    }

    public void eating() {
        LinkedList<Grass> grassToRemove = new LinkedList<>();
        for (Grass grass : map.getGrassList()) {
            if (map.getAnimals().containsKey(grass.getPosition())) {
                LinkedList<Animal> animalsToFeed = map.getAnimals().get(grass.getPosition()).getAnimalsToFeed();
                int energy = parameters.getPlantEnergy() / animalsToFeed.size();
                for (Animal animal : animalsToFeed) {
                    animal.changeEnergy(energy);
                }
                grassToRemove.add(grass);
            }
        }

        for (Grass grass : grassToRemove) {
            map.removeIMapElement(grass);
        }
    }

    public void moving() {
        LinkedList<Animal> animalsToMove = (LinkedList<Animal>) map.getAnimalsList().clone();
        for (Animal animal : animalsToMove) {
            animal.move();
        }
    }

    public void copulation() {
        LinkedList<Animal> children = new LinkedList<>();

        map.getAnimals().forEach((key, value) -> {
            LinkedList<Animal> animalsToCopulate = value.getAnimalsToCopulate();
            if (animalsToCopulate.size() > 1) {
                Animal child = animalsToCopulate.getFirst().copulate(animalsToCopulate.get(1));
                if (child != null && child.getPosition() != null) {
                    children.add(child);
                }
            }
        });

        for (Animal child : children) {
            map.place(child);
        }

    }

    public void losingEnergy() {
        for (Animal animal : map.getAnimalsList()) {
            animal.changeEnergy(-parameters.getMoveEnergy());
        }
        removeDeadAnimals();
    }

    public void placeRandomAnimals(int howMany) {
        for (int i = 0; i < howMany; i++) {
            if (map.getEmptyFields().getRandomEmptyField() != null) {
                Animal animalToPlace = new Animal(map, map.getEmptyFields().getRandomEmptyField(), parameters.getStartEnergy(), map.getAge());
                map.place(animalToPlace);
            }
        }
    }

    public void placeRandomGrass() {
        Grass grassInJungle = new Grass(map.getEmptyFields().getRandomEmptyJungleField());
        Grass grassInStep = new Grass(map.getEmptyFields().getRandomEmptyStepField());

        if (grassInJungle.getPosition() != null) {
            map.place(grassInJungle);
        }
        if (grassInStep.getPosition() != null) {
            map.place(grassInStep);
        }
    }

    @Override
    public IWorldMap getIWorldMap() {
        return map;
    }

    public List<Animal> getAnimalsToDraw(){
        return map.getAnimalsToDraw();
    }

    public List<Grass> getGrassToDraw(){
        return map.getGrassList();
    }
    public int getAge(){
        return age;
    }

}
