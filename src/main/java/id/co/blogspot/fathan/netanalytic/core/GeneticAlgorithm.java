package id.co.blogspot.fathan.netanalytic.core;

import java.util.ArrayList;
import java.util.List;

import id.co.blogspot.fathan.netanalytic.entity.NetworkAccess;
import id.co.blogspot.fathan.netanalytic.util.ValueConverter;

public class GeneticAlgorithm {

  private List<NetworkAccess> networkAccesses;
  private List<Long> maxValueAttributes;
  private Integer totalAttribute;
  private Integer totalCentroid;
  private Integer totalIndividual;

  public GeneticAlgorithm() {
    // do nothing
  }

  public GeneticAlgorithm(List<NetworkAccess> networkAccesses, List<Long> maxValueAttributes, Integer totalAttribute,
      Integer totalCentroid, Integer totalIndividual) {
    super();
    this.networkAccesses = networkAccesses;
    this.maxValueAttributes = maxValueAttributes;
    this.totalAttribute = totalAttribute;
    this.totalCentroid = totalCentroid;
    this.totalIndividual = totalIndividual;
  }

  public List<NetworkAccess> getNetworkAccesses() {
    return networkAccesses;
  }

  public void setNetworkAccesses(List<NetworkAccess> networkAccesses) {
    this.networkAccesses = networkAccesses;
  }

  public List<Long> getMaxValueAttributes() {
    return maxValueAttributes;
  }

  public void setMaxValueAttributes(List<Long> maxValueAttributes) {
    this.maxValueAttributes = maxValueAttributes;
  }

  public Integer getTotalAttribute() {
    return totalAttribute;
  }

  public void setTotalAttribute(Integer totalAttribute) {
    this.totalAttribute = totalAttribute;
  }

  public Integer getTotalCentroid() {
    return totalCentroid;
  }

  public void setTotalCentroid(Integer totalCentroid) {
    this.totalCentroid = totalCentroid;
  }

  public Integer getTotalIndividual() {
    return totalIndividual;
  }

  public void setTotalIndividual(Integer totalIndividual) {
    this.totalIndividual = totalIndividual;
  }

  private List<List<Long>> generateIndividual() throws Exception {
    List<List<Long>> individuals = new ArrayList<List<Long>>();
    for (int i = 0; i < this.totalIndividual; i++) {
      List<Long> cromosoms = new ArrayList<Long>();
      for (int j = 0; j < (this.totalAttribute * this.totalCentroid); j++) {
        cromosoms.add(Long.valueOf(Double.valueOf(Math.random() * this.maxValueAttributes.get(j % this.totalAttribute))
            .longValue()));
      }
      individuals.add(cromosoms);
    }
    return individuals;
  }

  private List<Double> calculateFitness(List<List<Long>> individuals) throws Exception {
    List<Double> fitness = new ArrayList<Double>();
    for (List<Long> individual : individuals) {
      Double a = 0.0;
      for (NetworkAccess networkAccess : this.networkAccesses) {
        Double b = -1.0;
        for (int k = 0; k < this.totalCentroid; k++) {
          Double c = Math.pow(networkAccess.getAccessTime().getTime() - individual.get((k * totalAttribute) + 0), 2);
          c +=
              Math.pow(
                  ValueConverter.stringToInteger(networkAccess.getIpAddress())
                      - individual.get((k * totalAttribute) + 1), 2);
          c += Math.pow(networkAccess.getUrlCluster() - individual.get((k * totalAttribute) + 2), 2);
          c += Math.pow(networkAccess.getAccessDuration() - individual.get((k * totalAttribute) + 3), 2);
          c += Math.pow(networkAccess.getAccessSize() - individual.get((k * totalAttribute) + 4), 2);
          c = Math.sqrt(c / 5);
          if (k == 0) {
            b = c;
          } else if (b > c) {
            b = c;
          }
        }
        a += b;
      }
      fitness.add(1 / a);
    }
    return fitness;
  }

  private List<Integer> useMachineRoullete(List<Double> fitness) throws Exception {
    Double a = 0.0;
    List<Double> percentages = new ArrayList<Double>();
    List<Integer> flags = new ArrayList<Integer>();
    for (Double fitnessItem : fitness) {
      a += fitnessItem;
    }
    for (Double fitnessItem : fitness) {
      percentages.add(100 * fitnessItem / a);
    }
    for (int i = 0; i < this.totalIndividual; i++) {
      flags.add(i);
    }
    for (int i = 0; i < this.totalIndividual; i++) {
      Integer b = Double.valueOf(Math.random() * 100).intValue();
      Integer c = 0;

      for (int j = 0; j < this.totalIndividual; j++) {
        if (b >= c && b < (c + percentages.get(j))) {
          flags.set(i, j);
          break;
        }
        c += percentages.get(j).intValue();
      }
    }
    return flags;
  }

  private List<List<Long>> selectionProcess(List<List<Long>> individuals, List<Integer> flags) throws Exception {
    List<List<Long>> selections = new ArrayList<List<Long>>();
    for (int i = 0; i < this.totalIndividual; i++) {
      List<Long> cromosoms = new ArrayList<Long>();
      for (int j = 0; j < (this.totalAttribute * this.totalCentroid); j++) {
        cromosoms.add(individuals.get(flags.get(i)).get(j));
      }
      selections.add(cromosoms);
    }
    return selections;
  }

  private void useCrossOver(List<List<Long>> selections) throws Exception {
    Double a = Math.random() * 1;
    for (int i = 0; i < this.totalIndividual; i += 2) {
      Double b = Math.random() * 1;
      if (b < a) {
        Integer c = Double.valueOf(Math.random() * (this.totalAttribute * this.totalCentroid)).intValue();
        Integer d = -1;
        if (c > (this.totalAttribute * this.totalCentroid) - 1) {
          c--;
        }
        for (;;) {
          d = Double.valueOf(Math.random() * (this.totalAttribute * this.totalCentroid)).intValue();
          if (d >= c) {
            break;
          }
        }
        for (int j = c; j <= d; j++) {
          Long e = selections.get(i).get(j);
          selections.get(i).set(j, selections.get(i + 1).get(j));
          selections.get(i + 1).set(j, e);
        }
      }
    }
  }

  private void useMutation(List<List<Long>> selections) throws Exception {
    Integer a = Double.valueOf(Math.random() * (this.totalAttribute * this.totalCentroid)).intValue();
    Integer b = Double.valueOf(Math.random() * (this.totalAttribute * this.totalCentroid)).intValue();
    Integer c = 0;
    Double d = Math.random() * 1;
    if (a > (this.totalAttribute * this.totalCentroid) - 1) {
      a--;
    }
    if (d > 0.5) {
      c = 1;
    } else {
      c = -1;
    }
    for (List<Long> selection : selections) {
      selection.set(a, selection.get(a) + (c * b));
    }
  }

  private List<List<Long>> useElitism(List<List<Long>> individuals, List<List<Long>> selections) throws Exception {
    List<List<Long>> elitismIndividuals = new ArrayList<List<Long>>();
    List<List<Long>> elitismSelections = new ArrayList<List<Long>>();
    for (List<Long> individual : individuals) {
      List<Long> elitismCromosoms = new ArrayList<Long>();
      for (Long cromosom : individual) {
        elitismCromosoms.add(cromosom);
      }
      elitismIndividuals.add(elitismCromosoms);
    }
    for (List<Long> selection : selections) {
      List<Long> elitismCromosoms = new ArrayList<Long>();
      for (Long cromosom : selection) {
        elitismCromosoms.add(cromosom);
      }
      elitismIndividuals.add(elitismCromosoms);
    }
    List<Double> fitness = calculateFitness(elitismIndividuals);
    List<Integer> indexes = new ArrayList<Integer>();
    for (int i = 0; i < elitismIndividuals.size(); i++) {
      indexes.add(i);
    }
    for (int i = 0; i < elitismIndividuals.size() - 1; i++) {
      for (int j = (i + 1); j < elitismIndividuals.size(); j++) {
        if (fitness.get(i) < fitness.get(j)) {
          Double a = fitness.get(i);
          fitness.set(i, fitness.get(j));
          fitness.set(j, a);
          Integer b = indexes.get(i);
          indexes.set(i, indexes.get(j));
          indexes.set(j, b);
        }
      }
    }
    for (int i = 0; i < this.totalIndividual; i++) {
      List<Long> elitismCromosoms = new ArrayList<Long>();
      for (Long cromosom : elitismIndividuals.get(indexes.get(i))) {
        elitismCromosoms.add(cromosom);
      }
      elitismSelections.add(elitismCromosoms);
    }
    return elitismSelections;
  }

  public List<List<Long>> generateCentroid(Integer iteration) throws Exception {
    List<List<Long>> centroids = new ArrayList<List<Long>>();
    List<List<Long>> individuals = generateIndividual();
    for (int i = 0; i < iteration; i++) {
      List<Double> fitness = calculateFitness(individuals);
      List<Integer> flags = useMachineRoullete(fitness);
      List<List<Long>> selections = selectionProcess(individuals, flags);
      useCrossOver(selections);
      useMutation(selections);
      individuals = useElitism(individuals, selections);
    }
    List<Double> fitness = calculateFitness(individuals);
    Integer correctIndex = 0;
    Double temporaryFitness = 0.0;
    for (int i = 0; i < this.totalIndividual; i++) {
      if (i == 0) {
        correctIndex = i;
        temporaryFitness = fitness.get(i);
      } else if (temporaryFitness < fitness.get(i)) {
        correctIndex = i;
        temporaryFitness = fitness.get(i);
      }
    }
    for (int i = 0; i < this.totalCentroid; i++) {
      List<Long> gens = new ArrayList<Long>();
      for (int j = 0; j < this.totalAttribute; j++) {
        gens.add(individuals.get(correctIndex).get((i * this.totalAttribute) + j));
      }
      centroids.add(gens);
    }
    return centroids;
  }

}
