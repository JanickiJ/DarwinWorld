package agh.cs.project.engine;


import com.google.gson.Gson;


import java.io.FileReader;
import java.io.FileNotFoundException;

public class OptionsParser {

    private int width;
    private int height;
    private double jungleRatio;
    private int startEnergy;
    private int moveEnergy;
    private int plantEnergy;
    private int startNumOfAnimals;


    public static OptionsParser loadPropFromFile(String dataPath) throws FileNotFoundException,IllegalArgumentException {
        Gson gson = new Gson();
        OptionsParser parameters = gson.fromJson(new FileReader(dataPath), OptionsParser.class);
        parameters.testData();
        return parameters;
    }

    public void testData() throws IllegalArgumentException{
        String incorrect = "Incorrect variable: ";
        if(this.width <= 0){ throw new IllegalArgumentException(incorrect + "map width");}
        if(this.height <= 0){ throw new IllegalArgumentException(incorrect + "map height");}
        if(this.startEnergy <= 0){ throw new IllegalArgumentException(incorrect + "start Energy");}
        if(this.moveEnergy <= 0){ throw new IllegalArgumentException(incorrect + "move energy");}
        if(this.plantEnergy <= 0){ throw new IllegalArgumentException(incorrect + "plant energy");}
        if(this.jungleRatio <= 0){ throw new IllegalArgumentException(incorrect + "plant energy");}
    }

    public double getJungleRatio() {
        return jungleRatio;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getMoveEnergy() {
        return moveEnergy;
    }

    public int getStartEnergy(){
        return startEnergy;
    }

    public int getPlantEnergy() {
        return plantEnergy;
    }
    public int getStartNumOfAnimals(){
        return startNumOfAnimals;
    }

}
