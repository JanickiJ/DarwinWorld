package agh.cs.project.elements;

public interface IPositionChangeObserver {
    void positionChanged(Vector2d oldPosition, Vector2d newPosition, Animal animal);
    void positionWillChange(Vector2d oldPosition, Vector2d newPosition, Animal animal);
}


