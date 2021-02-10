package agh.cs.project.elements;

public interface IMapElement {
    Vector2d getPosition();
    void addObserver(IPositionChangeObserver observer);
    void removeObserver(IPositionChangeObserver observer);
    void setID(int animalID);
}

