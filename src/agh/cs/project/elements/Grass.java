package agh.cs.project.elements;

public class Grass implements IMapElement {
    private final Vector2d position;

    public Grass(Vector2d position){
        this.position = position;
    }

    public Vector2d getPosition(){
        return this.position;
    }

    @Override
    public void addObserver(IPositionChangeObserver observer){
    }

    @Override
    public void removeObserver(IPositionChangeObserver observer) {
    }

    @Override
    public void setID(int animalID) {
    }

    public String toString(){
        return "*";
    }

}
