package id.co.blogspot.fathan.netanalytic.service;

import java.util.ArrayList;
import java.util.List;

import id.co.blogspot.fathan.netanalytic.entity.SystemParameter;
import id.co.blogspot.fathan.netanalytic.repository.SystemParameterRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class SystemParameterServiceBeanTest {

  public static final String DEFAULT_SYSTEM_PARAMETER_CODE = "CODE-1";
  public static final String DEFAULT_SYSTEM_PARAMETER_CODE_2 = "CODE-2";

  @Mock
  private SystemParameterRepository systemParameterRepository;

  @InjectMocks
  private SystemParameterServiceBean systemParameterServiceBean;

  public SystemParameterRepository getSystemParameterRepository() {
    return systemParameterRepository;
  }

  public void setSystemParameterRepository(SystemParameterRepository systemParameterRepository) {
    this.systemParameterRepository = systemParameterRepository;
  }

  public SystemParameterServiceBean getSystemParameterServiceBean() {
    return systemParameterServiceBean;
  }

  public void setSystemParameterServiceBean(SystemParameterServiceBean systemParameterServiceBean) {
    this.systemParameterServiceBean = systemParameterServiceBean;
  }

  @Before
  public void initializeTest() throws Exception {
    MockitoAnnotations.initMocks(this);

    SystemParameter systemParameter = new SystemParameter();
    List<SystemParameter> systemParameters = new ArrayList<SystemParameter>();
    systemParameters.add(new SystemParameter(null, DEFAULT_SYSTEM_PARAMETER_CODE, null));
    systemParameters.add(new SystemParameter(null, NetworkAccessServiceBean.SP_TOTAL_ATTRIBUTE, null));

    Mockito.when(getSystemParameterRepository().findByCode(Mockito.eq(DEFAULT_SYSTEM_PARAMETER_CODE))).thenReturn(
        systemParameter);
    Mockito.when(getSystemParameterRepository().findByCode(Mockito.eq(DEFAULT_SYSTEM_PARAMETER_CODE_2))).thenReturn(
        null);
    Mockito.when(getSystemParameterRepository().findAll()).thenReturn(systemParameters);
  }

  @Test
  public void saveTest() throws Exception {
    List<SystemParameter> request = new ArrayList<SystemParameter>();
    request.add(new SystemParameter(null, DEFAULT_SYSTEM_PARAMETER_CODE, null));
    request.add(new SystemParameter(null, DEFAULT_SYSTEM_PARAMETER_CODE_2, null));
    getSystemParameterServiceBean().save(request);
    Mockito.verify(getSystemParameterRepository()).findByCode(Mockito.eq(DEFAULT_SYSTEM_PARAMETER_CODE));
    Mockito.verify(getSystemParameterRepository()).findByCode(Mockito.eq(DEFAULT_SYSTEM_PARAMETER_CODE_2));
  }

  @Test
  public void findAllEditableTest() throws Exception {
    getSystemParameterServiceBean().findAllEditable();
    Mockito.verify(getSystemParameterRepository()).findAll();
  }

}
