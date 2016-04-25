package id.co.blogspot.fathan.netanalytic.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import id.co.blogspot.fathan.netanalytic.service.SilhouetteCoefficientService;

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

public class SilhouetteCoefficientControllerTest {

  private static final String DEFAULT_REQUEST_ID = UUID.randomUUID().toString();

  @Mock
  private SilhouetteCoefficientService silhouetteCoefficientService;

  @InjectMocks
  private SilhouetteCoefficientController silhouetteCoefficientController;

  MockMvc mockMvc;

  public SilhouetteCoefficientService getSilhouetteCoefficientService() {
    return silhouetteCoefficientService;
  }

  public void setSilhouetteCoefficientService(SilhouetteCoefficientService silhouetteCoefficientService) {
    this.silhouetteCoefficientService = silhouetteCoefficientService;
  }

  public SilhouetteCoefficientController getSilhouetteCoefficientController() {
    return silhouetteCoefficientController;
  }

  public void setSilhouetteCoefficientController(SilhouetteCoefficientController silhouetteCoefficientController) {
    this.silhouetteCoefficientController = silhouetteCoefficientController;
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
        .standaloneSetup(getSilhouetteCoefficientController())
        .setMessageConverters(new ByteArrayHttpMessageConverter(), new StringHttpMessageConverter(),
            new ResourceHttpMessageConverter(), new FormHttpMessageConverter(),
            new MappingJackson2HttpMessageConverter()).build());

    List<Map<Integer, Object>> silhouetteValuePerDataAndAveragePerCluster = new ArrayList<Map<Integer, Object>>();

    Mockito.when(getSilhouetteCoefficientService().calculateSilhouetteCoefficient()).thenReturn(
        silhouetteValuePerDataAndAveragePerCluster);
  }

  @Test
  public void calculateTest() throws Exception {
    URI uri =
        new URI(SilhouetteCoefficientController.BASE_PATH + SilhouetteCoefficientController.CALCULATE + "?requestId="
            + DEFAULT_REQUEST_ID);
    getMockMvc().perform(
        MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk());
    Mockito.verify(getSilhouetteCoefficientService()).calculateSilhouetteCoefficient();
  }

  @Test
  public void calculateTestWithException() throws Exception {
    Mockito.doThrow(Exception.class).when(getSilhouetteCoefficientService()).calculateSilhouetteCoefficient();
    URI uri =
        new URI(SilhouetteCoefficientController.BASE_PATH + SilhouetteCoefficientController.CALCULATE + "?requestId="
            + DEFAULT_REQUEST_ID);
    getMockMvc().perform(
        MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk());
    Mockito.verify(getSilhouetteCoefficientService()).calculateSilhouetteCoefficient();
  }

}
