package id.co.blogspot.fathan.netanalytic.core;

import id.co.blogspot.fathan.netanalytic.entity.NetworkAccess;
import id.co.blogspot.fathan.netanalytic.util.ValueConverter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class SilhouetteCoefficient {

  private List<NetworkAccess> networkAccesses;
  private Integer totalCentroid;
  private Map<Integer, List<NetworkAccess>> datasPerCluster = new HashMap<Integer, List<NetworkAccess>>();

  public SilhouetteCoefficient(List<NetworkAccess> networkAccesses, Integer totalCentroid) throws Exception {
    super();
    this.networkAccesses = networkAccesses;
    this.totalCentroid = totalCentroid;
    generateDatasPerCluster();
  }

  public List<NetworkAccess> getNetworkAccesses() {
    return networkAccesses;
  }

  public void setNetworkAccesses(List<NetworkAccess> networkAccesses) {
    this.networkAccesses = networkAccesses;
  }

  public Integer getTotalCentroid() {
    return totalCentroid;
  }

  public void setTotalCentroid(Integer totalCentroid) {
    this.totalCentroid = totalCentroid;
  }

  public Map<Integer, List<NetworkAccess>> getDatasPerCluster() {
    return datasPerCluster;
  }

  public void setDatasPerCluster(Map<Integer, List<NetworkAccess>> datasPerCluster) {
    this.datasPerCluster = datasPerCluster;
  }

  private void generateDatasPerCluster() throws Exception {
    for (NetworkAccess networkAccess : networkAccesses) {
      if (datasPerCluster.get(networkAccess.getClusterCode()) == null) {
        datasPerCluster.put(networkAccess.getClusterCode(), new ArrayList<NetworkAccess>());
      }
      datasPerCluster.get(networkAccess.getClusterCode()).add(networkAccess);
    }
  }

  private Double calculateEcludianDistance(NetworkAccess arg0, NetworkAccess arg1) throws Exception {
    Double a = Math.pow(arg0.getAccessTime().getTime() - arg1.getAccessTime().getTime(), 2);
    a +=
        Math.pow(
            ValueConverter.stringToInteger(arg0.getIpAddress()) - ValueConverter.stringToInteger(arg1.getIpAddress()),
            2);
    a += Math.pow(arg0.getUrlCluster() - arg1.getUrlCluster(), 2);
    a += Math.pow(arg0.getAccessDuration() - arg1.getAccessDuration(), 2);
    a += Math.pow(arg0.getAccessSize() - arg1.getAccessSize(), 2);
    a = Math.sqrt(a);
    return a;
  }

  private Double calculateValueB(Integer entryKey, NetworkAccess source) throws Exception {
    Double b = 0.0;
    int i = 0;
    for (Entry<Integer, List<NetworkAccess>> entry : datasPerCluster.entrySet()) {
      if (!entry.getKey().equals(entryKey)) {
        List<NetworkAccess> values = entry.getValue();
        Double a = 0.0;
        for (NetworkAccess value : values) {
          a += calculateEcludianDistance(source, value);
        }
        a /= values.size();
        if (i == 0) {
          b = a;
        } else if (a < b) {
          b = a;
        }
        i++;
      }
    }
    return b;
  }

  public void calculateSilhouetteCoefficientPerData() throws Exception {
    for (Entry<Integer, List<NetworkAccess>> entry : datasPerCluster.entrySet()) {
      List<NetworkAccess> values = entry.getValue();
      for (NetworkAccess value : values) {
        Double a = 0.0;
        for (NetworkAccess valueA : values) {
          if (!value.getId().equals(valueA.getId())) {
            a += calculateEcludianDistance(value, valueA);
          }
        }
        if (values.size() > 1) {
          a /= (values.size() - 1);
        }
        Double b = calculateValueB(entry.getKey(), value);
        Double c = b;
        if (a > b) {
          c = a;
        }
        Double sc = 0.0;
        if (c != 0) {
          sc = (b - a) / c;
        }
        value.setSilhouetteValue(sc);
      }
    }
  }
}
