package id.co.blogspot.fathan.netanalytic.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import id.co.blogspot.fathan.netanalytic.entity.NetworkAccess;
import id.co.blogspot.fathan.netanalytic.service.NetworkAccessService;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class NetworkAccessControllerTest {

  private static final String DEFAULT_IP_ADDRESS = "127.0.0.1";
  private static final Long DEFAULT_URL_CLUSTER = 1L;
  private static final Long DEFAULT_ACCESS_DURATION = 100L;
  private static final Long DEFAULT_ACCESS_SIZE = 100L;
  private static final Integer DEFAULT_CLUSTER_CODE = 1;
  private static final String DEFAULT_REQUEST_ID = UUID.randomUUID().toString();

  @Mock
  private NetworkAccessService networkAccessService;

  @InjectMocks
  private NetworkAccessController networkAccessController;

  private MockMvc mockMvc;

  public NetworkAccessService getNetworkAccessService() {
    return networkAccessService;
  }

  public void setNetworkAccessService(NetworkAccessService networkAccessService) {
    this.networkAccessService = networkAccessService;
  }

  public NetworkAccessController getNetworkAccessController() {
    return networkAccessController;
  }

  public void setNetworkAccessController(NetworkAccessController networkAccessController) {
    this.networkAccessController = networkAccessController;
  }

  public MockMvc getMockMvc() {
    return mockMvc;
  }

  public void setMockMvc(MockMvc mockMvc) {
    this.mockMvc = mockMvc;
  }

  @Before
  public void initializeTest() throws Exception {
    MockitoAnnotations.initMocks(this);
    setMockMvc(MockMvcBuilders
        .standaloneSetup(getNetworkAccessController())
        .setMessageConverters(new ByteArrayHttpMessageConverter(), new StringHttpMessageConverter(),
            new ResourceHttpMessageConverter(), new FormHttpMessageConverter(),
            new MappingJackson2HttpMessageConverter()).build());

    List<NetworkAccess> networkAccesses = new ArrayList<NetworkAccess>();
    networkAccesses.add(new NetworkAccess(null, new Date(), DEFAULT_IP_ADDRESS, DEFAULT_URL_CLUSTER,
        DEFAULT_ACCESS_DURATION, DEFAULT_ACCESS_SIZE, DEFAULT_CLUSTER_CODE));

    Mockito.when(getNetworkAccessService().cluster(Mockito.anyBoolean())).thenReturn(new ArrayList<List<Long>>());
    Mockito.when(getNetworkAccessService().findTotalPerCluster()).thenReturn(new ArrayList<List<Integer>>());
    Mockito.when(getNetworkAccessService().findAll()).thenReturn(networkAccesses);
  }

  @Test
  public void clusterTest() throws Exception {
    URI uri =
        new URI(NetworkAccessController.BASE_PATH + NetworkAccessController.CLUSTER + "?requestId="
            + DEFAULT_REQUEST_ID);
    getMockMvc().perform(
        MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk());
    Mockito.verify(getNetworkAccessService()).cluster(Mockito.anyBoolean());
  }

  @Test
  public void clusterTestWithException() throws Exception {
    Mockito.doThrow(Exception.class).when(getNetworkAccessService()).cluster(Mockito.anyBoolean());
    URI uri =
        new URI(NetworkAccessController.BASE_PATH + NetworkAccessController.CLUSTER + "?requestId="
            + DEFAULT_REQUEST_ID);
    getMockMvc().perform(
        MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk());
    Mockito.verify(getNetworkAccessService()).cluster(Mockito.anyBoolean());
  }

  @Test
  public void filterTotalPerClusterTest() throws Exception {
    URI uri =
        new URI(NetworkAccessController.BASE_PATH + NetworkAccessController.FILTER_TOTAL_PER_CLUSTER + "?requestId="
            + DEFAULT_REQUEST_ID);
    getMockMvc().perform(
        MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk());
    Mockito.verify(getNetworkAccessService()).findTotalPerCluster();
  }

  @Test
  public void filterTotalPerClusterTestWithException() throws Exception {
    Mockito.doThrow(Exception.class).when(getNetworkAccessService()).findTotalPerCluster();
    URI uri =
        new URI(NetworkAccessController.BASE_PATH + NetworkAccessController.FILTER_TOTAL_PER_CLUSTER + "?requestId="
            + DEFAULT_REQUEST_ID);
    getMockMvc().perform(
        MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk());
    Mockito.verify(getNetworkAccessService()).findTotalPerCluster();
  }

  @Test
  public void filterAllTest() throws Exception {
    URI uri = new URI(NetworkAccessController.BASE_PATH + "?requestId=" + DEFAULT_REQUEST_ID);
    getMockMvc().perform(
        MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk());
    Mockito.verify(getNetworkAccessService()).findAll();
  }

  @Test
  public void filterAllTestWithException() throws Exception {
    Mockito.doThrow(Exception.class).when(getNetworkAccessService()).findAll();
    URI uri = new URI(NetworkAccessController.BASE_PATH + "?requestId=" + DEFAULT_REQUEST_ID);
    getMockMvc().perform(
        MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk());
    Mockito.verify(getNetworkAccessService()).findAll();
  }

}
