package test.agh.cs.project;

import agh.cs.project.elements.Genes;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GenesTest {

    public boolean isRepair(Genes g) {

        boolean[] isInGenes = new boolean[8];

        for(int gene: g.getGenom()){
            isInGenes[gene] = true;
        }

        for(boolean b : isInGenes){
            if(!b){
                return b;
            }
        }
        return true;
    }
    @Test
    public void repairTest(){
        Genes g1 = new Genes();
        Genes g2 = new Genes();
        Genes g3 = new Genes();
        Genes g4 = new Genes();
        assertTrue(isRepair(g1));
        assertTrue(isRepair(g2));
        assertTrue(isRepair(g3));
        assertTrue(isRepair(g4));
    }
}
