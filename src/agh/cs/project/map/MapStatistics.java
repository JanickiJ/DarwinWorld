package agh.cs.project.map;

import agh.cs.project.elements.Animal;
import agh.cs.project.elements.Genes;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class MapStatistics {

    private boolean makeStatistics;
    private int endStatisticDate;
    private int sumOfLifeTime;
    private int numOfDeadAnimals;
    private final GrassField map;
    private Animal trackedAnimal;
    private final LinkedList<Double> averageEnergyEpoch = new LinkedList<>();
    private final LinkedList<Double> averageNumChildrenEpoch = new LinkedList<>();
    private final LinkedList<Integer> averageNumAnimalsEpoch = new LinkedList<>();
    private final LinkedList<Integer> averageNumGrassingEpoch = new LinkedList<>();
    private final LinkedList<Double> averageLifeTimeEpoch = new LinkedList<>();
    private final Map<Genes, Set<Animal>> dominantGenotypeEpoch = new HashMap<>();
    private int startNumOfDescendants;
    private int startNumOfChildren;
    private int endNumOfDescendants;
    private int endNumOfChildren;

    public MapStatistics(GrassField map){
        sumOfLifeTime = 0;
        numOfDeadAnimals = 0;
        this.map = map;
        makeStatistics = false;
        trackedAnimal = null;
    }

    public void removeAnimal(Animal animal){
        sumOfLifeTime += animal.getAge();
        numOfDeadAnimals += 1;
    }

    public int getMaxEnergy(){
        int max =0;
        for(Animal a: map.getAnimalsList()){
            if(a.getEnergy() > max){
                max = a.getEnergy();
            }
        }
        return max;
    }

    public int getMinEnergy(){
        int min =  Integer.MAX_VALUE;
        for(Animal a: map.getAnimalsList()){
            if(a.getEnergy() < min){
                min = a.getEnergy();
            }
        }
        return min;
    }

    public double getAverageNumOfChildren(){
        LinkedList<Animal> animalsList = map.getAnimalsList();
        double sum = 0;
        for(Animal a: animalsList){
            sum += a.getNumOfChildren();
        }
        if(animalsList.size() == 0) return 0;
        double result = round(sum/(double) animalsList.size());

        if(makeStatistics) averageNumChildrenEpoch.add(result);
        return result;
    }

    public double getAverageEnergy(){
        LinkedList<Animal> animalsList = map.getAnimalsList();
        int sum = 0;
        for(Animal a: animalsList){
            sum += a.getEnergy();
        }
        if(animalsList.size() ==0) return 0;
        double result = round(sum/ (double) animalsList.size());
        if (makeStatistics) averageEnergyEpoch.add(result);
        return result;
    }

    public double getAverageLifeTime(){
        if(numOfDeadAnimals == 0) return 0;
        double result = round(sumOfLifeTime /(double) numOfDeadAnimals);
        if(makeStatistics) averageLifeTimeEpoch.add(result);
        return result;
    }


    public int getNumOfAliveAnimals(){
        int result = map.getAnimalsList().size();
        if(makeStatistics) averageNumAnimalsEpoch.add(result);
        return result;
    }

    public int getNumOfGrass(){
        int result = map.getGrassList().size();
        if(makeStatistics) averageNumGrassingEpoch.add(result);
        return result;
    }

    public Map.Entry<Genes, Set<Animal>> getDominantGenotype(){
        LinkedList<Animal> animals = map.getAnimalsList();
        Map<Genes, Set<Animal>> genotypes = new HashMap<>();

        for(Animal a : animals){
            Genes key = a.getGenes();
            if(!genotypes.containsKey(key)){
                genotypes.put(key, new HashSet<>(Set.of(a)));
            }
            else if (genotypes.containsKey(key)){
                genotypes.get(key).add(a);
            }

            if(makeStatistics) {
                if (!dominantGenotypeEpoch.containsKey(a.getGenes())) {
                    dominantGenotypeEpoch.put(a.getGenes(), new HashSet<>(Set.of(a)));
                } else if (dominantGenotypeEpoch.containsKey(a.getGenes())) {
                    dominantGenotypeEpoch.get(a.getGenes()).add(a);
                }
            }

        }

        int max = 0;
        Map.Entry<Genes, Set<Animal>> result = null;
        for(Map.Entry<Genes, Set<Animal>> pair : genotypes.entrySet()){
            if(max < pair.getValue().size()){
                max = pair.getValue().size();
                result = pair;
            }
        }
        return result;
    }


    public double averageOfLifeTimeStatistics(){
        double sum = 0;
        for(double num : averageLifeTimeEpoch){
            sum += num;
        }
        if(averageLifeTimeEpoch.size() == 0) return 0;
        return round(sum/ (double) averageLifeTimeEpoch.size());
    }

    public double averageOfLiveAnimalsStatistics(){
        double sum = 0.0;
        for(Integer num : averageNumAnimalsEpoch){
            sum += num;
        }
        if(averageNumAnimalsEpoch.size() == 0) return 0;
        return round(sum/(double) averageNumAnimalsEpoch.size());
    }

    public double averageOfGrassStatistics(){
        double sum = 0.0;
        for(int num : averageNumGrassingEpoch){
            sum += num;
        }
        if(averageNumGrassingEpoch.size() == 0) return 0;
        return round(sum/(double) averageNumGrassingEpoch.size());
    }

    public double averageOfEnergyStatistics(){
        double sum = 0.0;
        for(double num : averageEnergyEpoch){
            sum += num;
        }
        if(averageEnergyEpoch.size() == 0) return 0;
        return round(sum/ averageEnergyEpoch.size());
    }

    public double averageOfBabiesStatistics(){
        double sum = 0.0;
        for(double num : averageNumChildrenEpoch){
            sum += num;
        }
        if(averageNumChildrenEpoch.size() == 0) return 0;
        return round(sum/ (double) averageNumChildrenEpoch.size());
    }

    public List<Integer> averageOfGenotypeStatistics(){
        int max = 0;
        Map.Entry<Genes, Set<Animal>> result = null;
        for(Map.Entry<Genes, Set<Animal>> pair : dominantGenotypeEpoch.entrySet()){
            if(pair.getValue().size() > max){
                max = pair.getValue().size();
                result = pair;
            }
        }
        if (result != null) {
            return result.getKey().getGenom();
        }
        return null;
    }

    public void writeTxt(String fileName){
        try(PrintWriter out = new PrintWriter(fileName)) {
            out.println("Average statistics of n ages after inserting n");
            out.println("Average Lifetime: " + averageOfLifeTimeStatistics());
            out.println("Average energy: " + averageOfEnergyStatistics());
            out.println("Average num of children: " + averageOfBabiesStatistics());
            out.println("Average num of grass: " + averageOfGrassStatistics());
            out.println("Average num of alive animals: " + averageOfLiveAnimalsStatistics());
            out.println("Dominating genome: " + averageOfGenotypeStatistics());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void startAnimalStatistics(){
        startNumOfChildren = trackedAnimal.getNumOfChildren();
        startNumOfDescendants = trackedAnimal.getNumOfDescendants();
    }

    public void endAnimalStatistics(){
        endNumOfDescendants = trackedAnimal.getNumOfDescendants() - startNumOfDescendants;
        endNumOfChildren = trackedAnimal.getNumOfChildren() - startNumOfChildren;

    }

    public int getEndNumOfDescendants(){
        return endNumOfDescendants;
    }

    public int getEndNumOfChildren(){
        return endNumOfChildren;
    }

    public int getDeadTime(){
        if(trackedAnimal.getAge()+trackedAnimal.getBirthday() < getEndStatisticDate()){
            return trackedAnimal.getAge()+trackedAnimal.getBirthday();
        }
        else{
            return -1;
        }
    }



    public void setMakeStatistics(boolean makeStatistics){
        this.makeStatistics = makeStatistics;
        if (trackedAnimal != null){
            if(makeStatistics){
                startAnimalStatistics();
            }
            else{
                endAnimalStatistics();
            }
        }
    }

    public void setEndStatisticDate(int date) {
        endStatisticDate = date;
    }
    public int getEndStatisticDate(){
        return endStatisticDate;
    }

    public void setTrackedAnimal(Animal a){
        startNumOfChildren = a.getNumOfChildren();
        startNumOfDescendants = a.getNumOfDescendants();
        trackedAnimal =a;
    }

    public Animal getTrackedAnimal(){
        return trackedAnimal;
    }

    public double round(double x){
        return (double)Math.round(x * 100d) / 100d;
    }
}
