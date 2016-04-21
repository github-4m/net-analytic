package id.co.blogspot.fathan.netanalytic.core;

import id.co.blogspot.fathan.netanalytic.entity.NetworkAccess;

import java.util.ArrayList;

import org.junit.Test;

public class GeneticAlgorithmTest {

  @Test
  public void constructorTest() throws Exception {
    GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm();
    geneticAlgorithm.setNetworkAccesses(new ArrayList<NetworkAccess>());
    geneticAlgorithm.getNetworkAccesses();
    geneticAlgorithm.setMaxValueAttributes(new ArrayList<Long>());
    geneticAlgorithm.getMaxValueAttributes();
    geneticAlgorithm.setTotalAttribute(5);
    geneticAlgorithm.getTotalAttribute();
    geneticAlgorithm.setTotalCentroid(2);
    geneticAlgorithm.getTotalCentroid();
    geneticAlgorithm.setTotalIndividual(10);
    geneticAlgorithm.getTotalIndividual();
  }

}
