package agh.cs.project.map;

import agh.cs.project.elements.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class GrassField implements IWorldMap, IPositionChangeObserver {

    private final Vector2d lowerLeft;
    private final Vector2d upperRight;
    private final Vector2d lowerLeftJungle;
    private final Vector2d upperRightJungle;

    private final Map<Vector2d, Grass> grass = new HashMap<>();
    private final Map<Vector2d, MapCell> animals = new HashMap<>();
    private final LinkedList<Animal> animalsList = new LinkedList<>();
    private final LinkedList<Grass> grassList = new LinkedList<>();
    private final EmptyFields emptyFields;
    private final MapStatistics mapStatistics;
    private int animalID=0;
    private int age;

    public GrassField(int width, int height, double jungleRatio) {

        this.lowerLeft = new Vector2d(0,0);
        this.upperRight = new Vector2d(width, height);
        int jungleWidth= (int)(width/jungleRatio);
        int jungleHeight =(int)(height/jungleRatio);

        this.lowerLeftJungle = new Vector2d(0,0);
        this.upperRightJungle = new Vector2d(width, height);

        for (int i = 0; i < (width - jungleWidth); i++) {
            if (i % 2 == 0) {
                lowerLeftJungle.x++;
            } else {
                upperRightJungle.x--;
            }
        }

        for (int i = 0; i < (height - jungleHeight); i++) {
            if (i % 2 == 0) {
                lowerLeftJungle.y++;
            } else {
                upperRightJungle.y--;
            }
        }


        this.emptyFields = new EmptyFields(lowerLeft,upperRight, lowerLeftJungle, upperRightJungle, this);
        this.mapStatistics = new MapStatistics(this);
        this.age =0;
    }




    public boolean place(IMapElement element) {
        if (!isOccupied(element.getPosition()) && inBorders(element.getPosition())){
            element.setID(this.animalID);
            this.animalID+=1;
            addIMapElement(element);
            return true;
        }
        return false;
    }

    public boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }

    public Object objectAt(Vector2d position) {
        if (!animals.containsKey(position)) return grass.get(position);
        else return animals.get(position).getStrongestAnimal();
    }


    public void addIMapElement(IMapElement element){
        Vector2d position = element.getPosition();

        if (element instanceof Animal) {
            if (!animals.containsKey(position)) {
                animals.put(position, new MapCell());
            }
            animals.get(position).addAnimal((Animal) element);
            animalsList.add((Animal) element);
            element.addObserver(this);
        }
        else{
            grass.put(position,(Grass) element);
            grassList.add((Grass) element);
        }
        emptyFields.removeEmptyField(position);
    }

    public void removeIMapElement(IMapElement element){
        Vector2d position = element.getPosition();
        if (element instanceof Animal) {
            animals.get(position).removeAnimal((Animal) element);
            animalsList.remove(element);

            if(animals.get(position).getSize() == 0){
                animals.remove(position);
            }
            mapStatistics.removeAnimal((Animal) element);
        }
        else{
            grass.remove(position);
            grassList.remove(element);
        }
        if(!isOccupied(position)){
            emptyFields.addEmptyField(position);
        }
    }



    public boolean inBorders(Vector2d position) {
        return (position.precedes(upperRight) && position.follows(lowerLeft));
    }
    public boolean inJungle(Vector2d position) {
        return (position.precedes(upperRightJungle) && position.follows(lowerLeftJungle));
    }

    @Override
    public Vector2d generateChildrenPosition(Vector2d parentsPosition) {
        return emptyFields.generateChildrenPosition(parentsPosition);
   }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition, Animal animal) {
        addIMapElement(animal);
    }

    @Override
    public void positionWillChange(Vector2d oldPosition, Vector2d newPosition, Animal animal) {
        removeIMapElement(animal);
    }


    public List<Animal> getAnimalsToDraw(){
        List<Animal> animalsToDraw = new LinkedList<>();
        animals.forEach((key, value) -> {
            if (value.getStrongestAnimal() != null && value.getStrongestAnimal().getEnergy()>0) {
                animalsToDraw.add(value.getStrongestAnimal());
            }
        });
        return animalsToDraw;
    }


    public Vector2d getLowerLeft(){
        return this.lowerLeft;
    }

    public Vector2d getUpperRight(){
        return this.upperRight;
    }

    public Vector2d getLowerLeftJungle(){
        return this.lowerLeftJungle;
    }

    public Vector2d getUpperRightJungle(){
        return this.upperRightJungle;
    }

    public LinkedList<Animal> getAnimalsList(){
        return animalsList;
    }

    public LinkedList<Grass> getGrassList() {
        return grassList;
    }

    public MapStatistics getMapStatistics(){
        return mapStatistics;
    }

    public EmptyFields getEmptyFields() {
        return emptyFields;
    }

    public void setAge(int age){
        this.age = age;
    }

    public Map<Vector2d, MapCell> getAnimals() {
        return animals;
    }

    public int getAge() {
        return age;
    }
}


