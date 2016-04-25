package id.co.blogspot.fathan.netanalytic.service;

import id.co.blogspot.fathan.netanalytic.core.SilhouetteCoefficient;
import id.co.blogspot.fathan.netanalytic.entity.NetworkAccess;
import id.co.blogspot.fathan.netanalytic.repository.NetworkAccessRepository;
import id.co.blogspot.fathan.netanalytic.repository.SystemParameterRepository;
import id.co.blogspot.fathan.netanalytic.util.ValueConverter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class SilhouetteCoefficientServiceBean implements SilhouetteCoefficientService {

  public static final String SP_TOTAL_CENTROID = "TOTAL_CENTROID";

  @Autowired
  private NetworkAccessRepository networkAccessRepository;

  @Autowired
  private SystemParameterRepository systemParameterRepository;

  public NetworkAccessRepository getNetworkAccessRepository() {
    return networkAccessRepository;
  }

  public void setNetworkAccessRepository(NetworkAccessRepository networkAccessRepository) {
    this.networkAccessRepository = networkAccessRepository;
  }

  public SystemParameterRepository getSystemParameterRepository() {
    return systemParameterRepository;
  }

  public void setSystemParameterRepository(SystemParameterRepository systemParameterRepository) {
    this.systemParameterRepository = systemParameterRepository;
  }

  @SuppressWarnings("unchecked")
  @Override
  @Transactional(readOnly = false, rollbackFor = Exception.class)
  public List<Map<Integer, Object>> calculateSilhouetteCoefficient() throws Exception {
    List<NetworkAccess> networkAccesses = getNetworkAccessRepository().findAll();
    Integer totalCentroid =
        Integer.parseInt(getSystemParameterRepository().findByCode(SilhouetteCoefficientServiceBean.SP_TOTAL_CENTROID)
            .getValue());
    SilhouetteCoefficient silhouetteCoefficient = new SilhouetteCoefficient(networkAccesses, totalCentroid);
    silhouetteCoefficient.calculateSilhouetteCoefficientPerData();
    List<Map<Integer, Object>> silhouetteValuePerDataAndAveragePerCluster = new ArrayList<Map<Integer, Object>>();
    Map<Integer, List<NetworkAccess>> datasPerCluster = silhouetteCoefficient.getDatasPerCluster();
    Map<Integer, Object> silhouetteValuePerData = new HashMap<Integer, Object>();
    Map<Integer, Object> silhouetteValueAveragePerCluster = new HashMap<Integer, Object>();
    for (Entry<Integer, List<NetworkAccess>> entry : datasPerCluster.entrySet()) {
      silhouetteValuePerData.put(entry.getKey(), new ArrayList<List<Object>>());
      List<NetworkAccess> values = entry.getValue();
      Double scPerCluster = 0.0;
      for (NetworkAccess value : values) {
        List<Object> clearData = new ArrayList<Object>();
        clearData.add(value.getAccessTime().getTime());
        clearData.add(ValueConverter.stringToInteger(value.getIpAddress()).longValue());
        clearData.add(value.getUrlCluster());
        clearData.add(value.getAccessDuration());
        clearData.add(value.getAccessSize());
        clearData.add(value.getClusterCode().longValue());
        clearData.add(value.getSilhouetteValue());
        scPerCluster += value.getSilhouetteValue();
        ((List<List<Object>>) silhouetteValuePerData.get(entry.getKey())).add(clearData);
      }
      if (values.size() > 0) {
        scPerCluster /= values.size();
      }
      silhouetteValueAveragePerCluster.put(entry.getKey(), scPerCluster);
    }
    silhouetteValuePerDataAndAveragePerCluster.add(silhouetteValuePerData);
    silhouetteValuePerDataAndAveragePerCluster.add(silhouetteValueAveragePerCluster);
    return silhouetteValuePerDataAndAveragePerCluster;
  }

}
