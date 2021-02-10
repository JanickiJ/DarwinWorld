package agh.cs.project.elements;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Genes {
    private final int size = 32;
    private final int geneTypes = 8;
    private final Integer[] genom = new Integer[size];


    public Genes(){
        generateRandomGenes();
    }

    public Genes(Genes Gene1, Genes Gene2){

    Random r = new Random();
    int division1 = r.nextInt(size-1);
    int division2 = division1 + r.nextInt(size-division1);

    System.arraycopy(Gene1.genom, 0, genom, 0, division1);

    if (division2 - division1 >= 0) {
        System.arraycopy(Gene2.genom, division1, genom, division1, division2 - division1);
    }
    if (size - division2 >= 0) {
        System.arraycopy(Gene1.genom, division2, genom, division2, size - division2);
    }

    repairGenes();
    }

    private void generateRandomGenes(){
        for(int i =0; i< size; i++){
            genom[i] = new Random().nextInt(geneTypes);
        }
        repairGenes();
    }

    private void repairGenes(){
        boolean[] isInGenes = new boolean[geneTypes];
        boolean needRepair = true;

        while(needRepair){
            needRepair = false;
            Arrays.fill(isInGenes, false);

            for(int gene: genom){
                isInGenes[gene] = true;
            }

            for(int i = 0; i< geneTypes; i++){
                if(!isInGenes[i]){
                    genom[new Random().nextInt(geneTypes)] = i;
                    needRepair = true;
                }
            }
        }
        Arrays.sort(genom);
    }

    public int generateDirection(){
        return genom[new Random().nextInt(size)];
    }

    public List<Integer> getGenom(){
        return Arrays.asList(genom);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Genes)) return false;
        Genes genes = (Genes) o;
        return Arrays.equals(genom, genes.genom);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(genom);
    }
}
