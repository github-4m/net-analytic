package id.co.blogspot.fathan.netanalytic.core;

import id.co.blogspot.fathan.netanalytic.entity.NetworkAccess;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class KMeansTest {

  @Test
  public void constructorTest() throws Exception {
    KMeans kMeans = new KMeans();
    kMeans.setNetworkAccesses(new ArrayList<NetworkAccess>());
    kMeans.getNetworkAccesses();
    kMeans.setCentroids(new ArrayList<List<Long>>());
    kMeans.getCentroids();
    kMeans.setTotalAttribute(5);
    kMeans.getTotalAttribute();
    kMeans.setTotalCentroid(2);
    kMeans.getTotalCentroid();
  }

}
