package agh.cs.project.elements;

import agh.cs.project.map.IWorldMap;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


public class Animal implements IMapElement {

    private int energy;
    private final Genes genes;
    private MapDirection direction = MapDirection.NORTH;
    private Vector2d position;
    private Vector2d oldPosition;
    private final IWorldMap map;
    private int animalID;
    private final List<Animal> children;
    private int age;
    private final int birthday;

    private final List<IPositionChangeObserver> observers = new LinkedList<>();


    public Animal(IWorldMap map, Vector2d initialPosition, int initialEnergy,int birthday){
        this.position = initialPosition;
        this.map = map;
        this.energy = initialEnergy;
        this.genes = new Genes();
        this.children = new LinkedList<>();
        this.age =0;
        this.birthday = birthday;

    }

    public Animal(IWorldMap map, Vector2d initialPosition, int initialEnergy, Genes genes, int birthday){
        this.position = initialPosition;
        this.map = map;
        this.energy = initialEnergy;
        this.genes = genes;
        this.children = new LinkedList<>();
        this.age =0;
        this.birthday = birthday;
    }


    public void changeDirection(){
        int numOfTurns = genes.generateDirection();
        for (int i = 0; i<numOfTurns; i++){
            this.direction = this.direction.next();
        }
    }

    public void move(){
        age+=1;
        oldPosition = position;
        positionWillChange();
        changeDirection();
        this.position = this.position.add(this.direction.toUnitVector());
        this.position = position.repair(map.getLowerLeft(), map.getUpperRight());
        positionChanged();
    }


    public Animal copulate(Animal parent){

        int childEnergy = (int) ((0.25 * this.energy) + (0.25* parent.energy));
        this.changeEnergy((int) (-0.25 * this.energy));
        parent.changeEnergy((int) (-0.25 * parent.energy));

        if(childEnergy >0) {
            Animal child = new Animal(map, map.generateChildrenPosition(parent.getPosition()), childEnergy, new Genes(this.genes, parent.genes),map.getAge());
            this.addChild(child);
            parent.addChild(child);
            return child;
        }
        return null;
    }


    public boolean isDead(){
        return this.energy <=0;
    }

    public void changeEnergy(int value) {
        this.energy = this.energy + value;
    }



    @Override
    public void addObserver(IPositionChangeObserver observer){
        if (!observers.contains(observer)){
            observers.add(observer);
        }
    }
    @Override
    public void removeObserver(IPositionChangeObserver observer){
        observers.remove(observer);
    }
    @Override
    public void setID(int animalID) {
        this.animalID = animalID;
    }



    public void positionChanged(){
        for(IPositionChangeObserver o : observers){
            o.positionChanged(oldPosition,position,this);
        }
    }

    public void positionWillChange(){
        for(IPositionChangeObserver o : observers){
            o.positionWillChange(oldPosition,position,this);
        }
    }


    public void addChild(Animal animal){
        children.add(animal);
    }


    public int getNumOfChildren() {
        return children.size();
    }

    public int getBirthday(){
        return this.birthday;
    }


    public List<Animal> getChildren(){
        return children;
    }

    private Set<Animal> getDescendants(List <Animal> children){
        Set<Animal> descendants = new HashSet<>(children);
        for(Animal child : children){
            descendants.addAll(getDescendants(child.getChildren()));
        }
        return descendants;
    }


    public int getNumOfDescendants(){
        return getDescendants(children).size();
    }
    public int getAge(){
        return age;
    }
    public Vector2d getPosition(){
        return position;
    }
    public Genes getGenes(){
        return genes;
    }
    public int getEnergy() {
        return energy;
    }

    public int getID() {
        return animalID;
    }


    public String toString(){
        return switch (this.direction) {
            case NORTH -> "N";
            case SOUTH -> "S";
            case EAST -> "E";
            case WEST -> "W";
            case NORTHEAST -> "NE";
            case SOUTHEAST -> "SE";
            case SOUTHWEST -> "SW";
            case NORTHWEST -> "NW";

        };
    }
}
