package id.co.blogspot.fathan.netanalytic.core;

import id.co.blogspot.fathan.netanalytic.entity.NetworkAccess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

public class SilhouetteCoefficientTest {

  @Test
  public void constructorTest() throws Exception {
    SilhouetteCoefficient silhouetteCoefficient = new SilhouetteCoefficient(new ArrayList<NetworkAccess>(), 2);
    silhouetteCoefficient.setNetworkAccesses(new ArrayList<NetworkAccess>());
    silhouetteCoefficient.getNetworkAccesses();
    silhouetteCoefficient.setDatasPerCluster(new HashMap<Integer, List<NetworkAccess>>());
    silhouetteCoefficient.setTotalCentroid(2);
    silhouetteCoefficient.getTotalCentroid();
  }

}
