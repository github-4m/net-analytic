package id.co.blogspot.fathan.netanalytic.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import id.co.blogspot.fathan.netanalytic.dto.SystemParameterBulkRequest;
import id.co.blogspot.fathan.netanalytic.dto.SystemParameterRequest;
import id.co.blogspot.fathan.netanalytic.entity.SystemParameter;
import id.co.blogspot.fathan.netanalytic.service.SystemParameterService;

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

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SystemParameterControllerTest {

  private static final String DEFAULT_REQUEST_ID = UUID.randomUUID().toString();
  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper(new JsonFactory());

  @Mock
  private SystemParameterService systemParameterService;

  @InjectMocks
  private SystemParameterController systemParameterController;

  private MockMvc mockMvc;

  public SystemParameterService getSystemParameterService() {
    return systemParameterService;
  }

  public void setSystemParameterService(SystemParameterService systemParameterService) {
    this.systemParameterService = systemParameterService;
  }

  public SystemParameterController getSystemParameterController() {
    return systemParameterController;
  }

  public void setSystemParameterController(SystemParameterController systemParameterController) {
    this.systemParameterController = systemParameterController;
  }

  public MockMvc getMockMvc() {
    return mockMvc;
  }

  public void setMockMvc(MockMvc mockMvc) {
    this.mockMvc = mockMvc;
  }

  @SuppressWarnings("unchecked")
  @Before
  public void initializeTest() throws Exception {
    MockitoAnnotations.initMocks(this);
    setMockMvc(MockMvcBuilders
        .standaloneSetup(getSystemParameterController())
        .setMessageConverters(new ByteArrayHttpMessageConverter(), new StringHttpMessageConverter(),
            new ResourceHttpMessageConverter(), new FormHttpMessageConverter(),
            new MappingJackson2HttpMessageConverter()).build());

    List<SystemParameter> systemParameters = new ArrayList<SystemParameter>();
    systemParameters.add(new SystemParameter());

    Mockito.when(getSystemParameterService().findAllEditable()).thenReturn(systemParameters);
    Mockito.doNothing().when(getSystemParameterService()).save((List<SystemParameter>) Mockito.anyObject());
  }

  @Test
  public void filterAllEditableTest() throws Exception {
    URI uri =
        new URI(SystemParameterController.BASE_PATH + SystemParameterController.FILTER_ALL_EDITABLE + "?requestId="
            + DEFAULT_REQUEST_ID);
    getMockMvc().perform(
        MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk());
    Mockito.verify(getSystemParameterService()).findAllEditable();
  }

  @Test
  public void filterAllEditableTestWithException() throws Exception {
    Mockito.doThrow(Exception.class).when(getSystemParameterService()).findAllEditable();
    URI uri =
        new URI(SystemParameterController.BASE_PATH + SystemParameterController.FILTER_ALL_EDITABLE + "?requestId="
            + DEFAULT_REQUEST_ID);
    getMockMvc().perform(
        MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk());
    Mockito.verify(getSystemParameterService()).findAllEditable();
  }

  @SuppressWarnings("unchecked")
  @Test
  public void bulkSaveTest() throws Exception {
    SystemParameterBulkRequest request = new SystemParameterBulkRequest();
    request.setSystemParameters(new ArrayList<SystemParameterRequest>());
    request.getSystemParameters().add(new SystemParameterRequest());
    URI uri =
        new URI(SystemParameterController.BASE_PATH + SystemParameterController.BULK_SAVE + "?requestId="
            + DEFAULT_REQUEST_ID);
    getMockMvc().perform(
        MockMvcRequestBuilders.post(uri).content(OBJECT_MAPPER.writeValueAsString(request))
            .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk());
    Mockito.verify(getSystemParameterService()).save((List<SystemParameter>) Mockito.anyObject());
  }

  @SuppressWarnings("unchecked")
  @Test
  public void bulkSaveTestWithException() throws Exception {
    Mockito.doThrow(Exception.class).when(getSystemParameterService())
        .save((List<SystemParameter>) Mockito.anyObject());
    SystemParameterBulkRequest request = new SystemParameterBulkRequest();
    request.setSystemParameters(new ArrayList<SystemParameterRequest>());
    request.getSystemParameters().add(new SystemParameterRequest());
    URI uri =
        new URI(SystemParameterController.BASE_PATH + SystemParameterController.BULK_SAVE + "?requestId="
            + DEFAULT_REQUEST_ID);
    getMockMvc().perform(
        MockMvcRequestBuilders.post(uri).content(OBJECT_MAPPER.writeValueAsString(request))
            .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk());
    Mockito.verify(getSystemParameterService()).save((List<SystemParameter>) Mockito.anyObject());
  }

}
