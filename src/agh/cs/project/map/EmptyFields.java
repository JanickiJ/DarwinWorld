package agh.cs.project.map;

import agh.cs.project.elements.Vector2d;

import java.util.LinkedList;
import java.util.Random;

public class EmptyFields {
    private final LinkedList<Vector2d> emptyFields;
    private final LinkedList<Vector2d> emptyFieldsInJungle;
    private final LinkedList<Vector2d> emptyFieldsInStep;
    private final Vector2d lowerLeftJungle;
    private final Vector2d upperRightJungle;
    private final IWorldMap map;


    public EmptyFields(Vector2d lowerLeft, Vector2d upperRight, Vector2d lowerLeftJungle, Vector2d upperRightJungle,IWorldMap map) {
        this.lowerLeftJungle = lowerLeftJungle;
        this.upperRightJungle = upperRightJungle;
        this.emptyFields = new LinkedList<>();
        this.emptyFieldsInJungle = new LinkedList<>();
        this.emptyFieldsInStep = new LinkedList<>();

        this.map = map;
        for(int x= lowerLeft.x;x<= upperRight.x;x++ ){
            for(int y = lowerLeft.y;y<= upperRight.y;y++ ){
                addEmptyField(new Vector2d(x,y));
            }
        }
    }



    public boolean inJungle(Vector2d position){
        return (position.precedes(upperRightJungle) && position.follows(lowerLeftJungle));
    }
    public boolean inStep(Vector2d position){
        return (map.inBorders(position) && !inJungle(position));
    }


    public void addEmptyField(Vector2d emptyField){
        emptyFields.add(emptyField);
        if(inJungle(emptyField)){
            emptyFieldsInJungle.add(emptyField);
        }
        if(inStep(emptyField)){
            emptyFieldsInStep.add(emptyField);
        }
    }

    public void removeEmptyField(Vector2d emptyField){
        emptyFields.remove(emptyField);
        emptyFieldsInJungle.remove(emptyField);
        emptyFieldsInStep.remove(emptyField);
    }

    public Vector2d getRandomEmptyField(){
        return emptyFields.get(new Random().nextInt(emptyFields.size()));
    }

    public Vector2d getRandomEmptyJungleField(){
        if(emptyFieldsInJungle.size() != 0){
            return emptyFieldsInJungle.get(new Random().nextInt(emptyFieldsInJungle.size()));
        }
        return null;
    }

    public Vector2d getRandomEmptyStepField(){
        if(emptyFieldsInStep.size() != 0){
            return emptyFieldsInStep.get(new Random().nextInt(emptyFieldsInStep.size()));
        }
        return null;
    }


    public Vector2d generateChildrenPosition(Vector2d parentsPosition) {
        LinkedList<Vector2d> emptyNeighbourFields = new LinkedList<>();
        for(int i=-1;i<2;i++){
            for(int j=-1;j<2;j++){
                int x = parentsPosition.x + i;
                int y = parentsPosition.y + j;
                Vector2d neighbourField = new Vector2d(x,y);
                if(map.inBorders(neighbourField) && !map.isOccupied(neighbourField)){
                    emptyNeighbourFields.add(neighbourField);
                }
            }
        }
        if(!emptyNeighbourFields.isEmpty()){
            return emptyNeighbourFields.get(new Random().nextInt(emptyNeighbourFields.size()));
        }
        else{
            return emptyFields.get(new Random().nextInt(emptyFields.size()));
        }
    }
}
