package id.co.blogspot.fathan.netanalytic.core;

import id.co.blogspot.fathan.netanalytic.entity.NetworkAccess;
import id.co.blogspot.fathan.netanalytic.util.ValueConverter;

import java.util.ArrayList;
import java.util.List;

public class KMeans {

  private List<NetworkAccess> networkAccesses;
  private List<List<Long>> centroids;
  private Integer totalAttribute;
  private Integer totalCentroid;

  public KMeans() {
    // do nothing
  }

  public KMeans(List<NetworkAccess> networkAccesses, List<List<Long>> centroids, Integer totalAttribute,
      Integer totalCentroid) {
    super();
    this.networkAccesses = networkAccesses;
    this.centroids = centroids;
    this.totalAttribute = totalAttribute;
    this.totalCentroid = totalCentroid;
  }

  public List<NetworkAccess> getNetworkAccesses() {
    return networkAccesses;
  }

  public void setNetworkAccesses(List<NetworkAccess> networkAccesses) {
    this.networkAccesses = networkAccesses;
  }

  public List<List<Long>> getCentroids() {
    return centroids;
  }

  public void setCentroids(List<List<Long>> centroids) {
    this.centroids = centroids;
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

  private List<List<Long>> copyCentroids() throws Exception {
    List<List<Long>> newCentroids = new ArrayList<List<Long>>();
    for (List<Long> centroid : this.centroids) {
      List<Long> newCentroid = new ArrayList<Long>();
      for (Long attribute : centroid) {
        newCentroid.add(attribute);
      }
      newCentroids.add(newCentroid);
    }
    return newCentroids;
  }

  private void assigneeCluster() throws Exception {
    for (NetworkAccess networkAccess : this.networkAccesses) {
      Double a = 0.0;
      for (int i = 0; i < this.totalCentroid; i++) {
        Double b = Math.pow(networkAccess.getAccessTime().getTime() - this.centroids.get(i).get(0), 2);
        b += Math.pow(ValueConverter.stringToInteger(networkAccess.getIpAddress()) - this.centroids.get(i).get(1), 2);
        b += Math.pow(networkAccess.getUrlCluster() - this.centroids.get(i).get(2), 2);
        b += Math.pow(networkAccess.getAccessDuration() - this.centroids.get(i).get(3), 2);
        b += Math.pow(networkAccess.getAccessSize() - this.centroids.get(i).get(4), 2);
        b = Math.sqrt(b);
        if (i == 0) {
          a = b;
          networkAccess.setClusterCode(i);
        } else if (a > b) {
          a = b;
          networkAccess.setClusterCode(i);
        }
      }
    }
  }

  private void calculateCentroid() throws Exception {
    List<Integer> totalPerClusterCodes = new ArrayList<Integer>();
    for (List<Long> centroid : this.centroids) {
      totalPerClusterCodes.add(0);
      for (int i = 0; i < this.totalAttribute; i++) {
        centroid.set(i, 0L);
      }
    }
    for (NetworkAccess networkAccess : this.networkAccesses) {
      for (int i = 0; i < this.totalCentroid; i++) {
        if (networkAccess.getClusterCode() == i) {
          totalPerClusterCodes.set(i, totalPerClusterCodes.get(i) + 1);
          this.centroids.get(i).set(0, this.centroids.get(i).get(0) + networkAccess.getAccessTime().getTime());
          this.centroids.get(i).set(1,
              this.centroids.get(i).get(1) + ValueConverter.stringToInteger(networkAccess.getIpAddress()));
          this.centroids.get(i).set(2, this.centroids.get(i).get(2) + networkAccess.getUrlCluster());
          this.centroids.get(i).set(3, this.centroids.get(i).get(3) + networkAccess.getAccessDuration());
          this.centroids.get(i).set(4, this.centroids.get(i).get(4) + networkAccess.getAccessSize());
          break;
        }
      }
    }
    for (int i = 0; i < this.totalCentroid; i++) {
      if (totalPerClusterCodes.get(i) > 0) {
        this.centroids.get(i).set(0, this.centroids.get(i).get(0) / totalPerClusterCodes.get(i));
        this.centroids.get(i).set(1, this.centroids.get(i).get(1) / totalPerClusterCodes.get(i));
        this.centroids.get(i).set(2, this.centroids.get(i).get(2) / totalPerClusterCodes.get(i));
        this.centroids.get(i).set(3, this.centroids.get(i).get(3) / totalPerClusterCodes.get(i));
        this.centroids.get(i).set(4, this.centroids.get(i).get(4) / totalPerClusterCodes.get(i));
      }
    }
  }

  private boolean compareCentroids(List<List<Long>> previousCentroids) throws Exception {
    Double a = 0.0;
    for (int i = 0; i < this.totalCentroid; i++) {
      Double b = 0.0;
      for (int j = 0; j < this.totalAttribute; j++) {
        b += Math.pow(previousCentroids.get(i).get(j) - this.centroids.get(i).get(j), 2);
      }
      if (b != 0) {
        b = Math.sqrt(b);
      }
      a += b;
    }
    a /= this.totalCentroid;
    return a == 0.0;
  }

  public void cluster(Integer iteration, List<List<Long>> centroids) throws Exception {
    boolean same = false;
    int i = 0;
    while (i < iteration || !same) {
      List<List<Long>> previousCentroids = copyCentroids();
      assigneeCluster();
      calculateCentroid();
      same = compareCentroids(previousCentroids);
      i++;
    }
  }

}
