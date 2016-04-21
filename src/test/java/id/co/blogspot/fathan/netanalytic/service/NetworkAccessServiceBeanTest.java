package id.co.blogspot.fathan.netanalytic.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@SuppressWarnings("unchecked")
public class NetworkAccessServiceBeanTest {

  private static final String DEFAULT_CLUSTER_CODE = "2";
  private static final Pageable DEFAULT_PAGEABLE = new PageRequest(0, 10);
  private static final String DEFAULT_SP_TOTAL_CENTROID = "2";
  private static final String DEFAULT_IP_ADDRESS = "127.0.0.1";
  private static final Long DEFAULT_URL_CLUSTER = 1L;
  private static final Long DEFAULT_ACCESS_DURATION = 100L;
  private static final Long DEFAULT_ACCESS_SIZE = 100L;

  @Mock
  private NetworkAccessRepository networkAccessRepository;

  @Mock
  private SystemParameterRepository systemParameterRepository;

  @InjectMocks
  private NetworkAccessServiceBean networkAccessServiceBean;

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

  public NetworkAccessServiceBean getNetworkAccessServiceBean() {
    return networkAccessServiceBean;
  }

  public void setNetworkAccessServiceBean(NetworkAccessServiceBean networkAccessServiceBean) {
    this.networkAccessServiceBean = networkAccessServiceBean;
  }

  @Before
  public void initializeTest() throws Exception {
    MockitoAnnotations.initMocks(this);

    List<NetworkAccess> networkAccesses = new ArrayList<NetworkAccess>();
    for (int i = 0; i < 1000; i += 100) {
      networkAccesses.add(new NetworkAccess(null, new Date(), DEFAULT_IP_ADDRESS, DEFAULT_URL_CLUSTER + i,
          DEFAULT_ACCESS_DURATION + i, DEFAULT_ACCESS_SIZE + i, Integer.parseInt(DEFAULT_CLUSTER_CODE)));
    }
    Page<NetworkAccess> networkAccessPage = new PageImpl<NetworkAccess>(networkAccesses);

    SystemParameter systemParameterTotalCentroid =
        new SystemParameter(null, NetworkAccessServiceBean.SP_TOTAL_CENTROID, DEFAULT_SP_TOTAL_CENTROID);
    SystemParameter systemParameterTotalIndividual =
        new SystemParameter(null, NetworkAccessServiceBean.SP_TOTAL_INDIVIDUAL, String.valueOf(10));
    SystemParameter systemParameterTotalAttribute =
        new SystemParameter(null, NetworkAccessServiceBean.SP_TOTAL_ATTRIBUTE, String.valueOf(5));
    SystemParameter systemParameterDefaultIteration =
        new SystemParameter(null, NetworkAccessServiceBean.SP_DEFAULT_ITERATION, String.valueOf(50));
    Object[] totalPerCluster = new Object[] {new Object[] {Integer.parseInt(DEFAULT_CLUSTER_CODE), 10L}};
    Object[] maxAttributes =
        new Object[] {new Date(), DEFAULT_IP_ADDRESS, DEFAULT_URL_CLUSTER, DEFAULT_ACCESS_DURATION, DEFAULT_ACCESS_SIZE};

    Mockito.when(getNetworkAccessRepository().findByClusterCode(Mockito.anyString(), (Pageable) Mockito.anyObject()))
        .thenReturn(networkAccessPage);
    Mockito.when(getNetworkAccessRepository().findAll()).thenReturn(networkAccesses);
    Mockito.when(getSystemParameterRepository().findByCode(Mockito.eq(NetworkAccessServiceBean.SP_TOTAL_CENTROID)))
        .thenReturn(systemParameterTotalCentroid);
    Mockito.when(getNetworkAccessRepository().findTotalPerCluster((List<Integer>) Mockito.anyObject())).thenReturn(
        totalPerCluster);
    Mockito.when(getSystemParameterRepository().findByCode(Mockito.eq(NetworkAccessServiceBean.SP_TOTAL_INDIVIDUAL)))
        .thenReturn(systemParameterTotalIndividual);
    Mockito.when(getSystemParameterRepository().findByCode(Mockito.eq(NetworkAccessServiceBean.SP_TOTAL_ATTRIBUTE)))
        .thenReturn(systemParameterTotalAttribute);
    Mockito.when(getSystemParameterRepository().findByCode(Mockito.eq(NetworkAccessServiceBean.SP_DEFAULT_ITERATION)))
        .thenReturn(systemParameterDefaultIteration);
    Mockito.when(getNetworkAccessRepository().findMaxAttributes()).thenReturn(maxAttributes);
    Mockito.when(getNetworkAccessRepository().save((List<NetworkAccess>) Mockito.anyObject())).thenReturn(null);
  }

  @Test
  public void findNetworkAccessByClusterCodeTest() throws Exception {
    getNetworkAccessServiceBean().findNetworkAccessByClusterCode(DEFAULT_CLUSTER_CODE, DEFAULT_PAGEABLE);
    Mockito.verify(getNetworkAccessRepository()).findByClusterCode(Mockito.anyString(), (Pageable) Mockito.anyObject());
  }

  @Test
  public void findAllTest() throws Exception {
    getNetworkAccessServiceBean().findAll();
    Mockito.verify(getNetworkAccessRepository()).findAll();
  }

  @Test
  public void findTotalPerClusterTest() throws Exception {
    getNetworkAccessServiceBean().findTotalPerCluster();
    Mockito.verify(getSystemParameterRepository()).findByCode(Mockito.eq(NetworkAccessServiceBean.SP_TOTAL_CENTROID));
    Mockito.verify(getNetworkAccessRepository()).findTotalPerCluster((List<Integer>) Mockito.anyObject());
  }

  @Test
  public void clusterTest() throws Exception {
    getNetworkAccessServiceBean().cluster();
    Mockito.verify(getNetworkAccessRepository()).findAll();
    Mockito.verify(getSystemParameterRepository()).findByCode(Mockito.eq(NetworkAccessServiceBean.SP_TOTAL_CENTROID));
    Mockito.verify(getSystemParameterRepository()).findByCode(Mockito.eq(NetworkAccessServiceBean.SP_TOTAL_INDIVIDUAL));
    Mockito.verify(getSystemParameterRepository()).findByCode(Mockito.eq(NetworkAccessServiceBean.SP_TOTAL_ATTRIBUTE));
    Mockito.verify(getSystemParameterRepository())
        .findByCode(Mockito.eq(NetworkAccessServiceBean.SP_DEFAULT_ITERATION));
    Mockito.verify(getNetworkAccessRepository()).findMaxAttributes();
    Mockito.verify(getNetworkAccessRepository()).save((List<NetworkAccess>) Mockito.anyObject());
  }

}
