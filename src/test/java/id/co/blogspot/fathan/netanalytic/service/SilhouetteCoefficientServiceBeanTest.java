package id.co.blogspot.fathan.netanalytic.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import id.co.blogspot.fathan.netanalytic.entity.NetworkAccess;
import id.co.blogspot.fathan.netanalytic.entity.SystemParameter;
import id.co.blogspot.fathan.netanalytic.repository.NetworkAccessRepository;
import id.co.blogspot.fathan.netanalytic.repository.SystemParameterRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class SilhouetteCoefficientServiceBeanTest {

  private static final String DEFAULT_CLUSTER_CODE = "0";
  private static final String DEFAULT_SP_TOTAL_CENTROID = "3";
  private static final String DEFAULT_IP_ADDRESS = "127.0.0.1";
  private static final Long DEFAULT_URL_CLUSTER = 1L;
  private static final Long DEFAULT_ACCESS_DURATION = 100L;
  private static final Long DEFAULT_ACCESS_SIZE = 100L;

  @Mock
  private NetworkAccessRepository networkAccessRepository;

  @Mock
  private SystemParameterRepository systemParameterRepository;

  @InjectMocks
  private SilhouetteCoefficientServiceBean silhouetteCoefficientServiceBean;

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

  public SilhouetteCoefficientServiceBean getSilhouetteCoefficientServiceBean() {
    return silhouetteCoefficientServiceBean;
  }

  public void setSilhouetteCoefficientServiceBean(SilhouetteCoefficientServiceBean silhouetteCoefficientServiceBean) {
    this.silhouetteCoefficientServiceBean = silhouetteCoefficientServiceBean;
  }

  @Before
  public void initializeTest() throws Exception {
    MockitoAnnotations.initMocks(this);

    List<NetworkAccess> networkAccesses = new ArrayList<NetworkAccess>();
    for (int i = 0; i < 1000; i += 100) {
      networkAccesses.add(new NetworkAccess(UUID.randomUUID().toString(), new Date(), DEFAULT_IP_ADDRESS,
          DEFAULT_URL_CLUSTER + i, DEFAULT_ACCESS_DURATION + i, DEFAULT_ACCESS_SIZE + i, Integer
              .parseInt(DEFAULT_CLUSTER_CODE)));
    }
    networkAccesses.get(9).setAccessTime(Calendar.getInstance().getTime());
    networkAccesses.get(9).setIpAddress("0.0.0.0");
    networkAccesses.get(9).setClusterCode(Integer.parseInt(DEFAULT_CLUSTER_CODE) + 1);
    networkAccesses.get(8).setClusterCode(Integer.parseInt(DEFAULT_CLUSTER_CODE) + 2);
    networkAccesses.get(7).setClusterCode(Integer.parseInt(DEFAULT_CLUSTER_CODE) + 2);

    SystemParameter systemParameterTotalCentroid =
        new SystemParameter(null, NetworkAccessServiceBean.SP_TOTAL_CENTROID, DEFAULT_SP_TOTAL_CENTROID);

    Mockito.when(getNetworkAccessRepository().findAll()).thenReturn(networkAccesses);
    Mockito.when(getSystemParameterRepository().findByCode(Mockito.eq(NetworkAccessServiceBean.SP_TOTAL_CENTROID)))
        .thenReturn(systemParameterTotalCentroid);
  }

  @Test
  public void calculateSilhouetteCoefficientTest() throws Exception {
    getSilhouetteCoefficientServiceBean().calculateSilhouetteCoefficient();
    Mockito.verify(getNetworkAccessRepository()).findAll();
    Mockito.verify(getSystemParameterRepository()).findByCode(Mockito.eq(NetworkAccessServiceBean.SP_TOTAL_CENTROID));
  }

  @Test
  public void calculateSilhouetteCoefficientTestWithOneClusterAsResult() throws Exception {
    List<NetworkAccess> networkAccesses = new ArrayList<NetworkAccess>();
    for (int i = 0; i < 1000; i += 100) {
      networkAccesses.add(new NetworkAccess(UUID.randomUUID().toString(), new Date(), DEFAULT_IP_ADDRESS,
          DEFAULT_URL_CLUSTER + i, DEFAULT_ACCESS_DURATION + i, DEFAULT_ACCESS_SIZE + i, Integer
              .parseInt(DEFAULT_CLUSTER_CODE)));
    }
    Mockito.when(getNetworkAccessRepository().findAll()).thenReturn(networkAccesses);
    getSilhouetteCoefficientServiceBean().calculateSilhouetteCoefficient();
    Mockito.verify(getNetworkAccessRepository()).findAll();
    Mockito.verify(getSystemParameterRepository()).findByCode(Mockito.eq(NetworkAccessServiceBean.SP_TOTAL_CENTROID));
  }

}
