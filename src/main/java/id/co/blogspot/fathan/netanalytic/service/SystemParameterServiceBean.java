package id.co.blogspot.fathan.netanalytic.service;

import id.co.blogspot.fathan.netanalytic.entity.SystemParameter;
import id.co.blogspot.fathan.netanalytic.repository.SystemParameterRepository;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class SystemParameterServiceBean implements SystemParameterService {

  @Autowired
  private SystemParameterRepository systemParameterRepository;

  public SystemParameterRepository getSystemParameterRepository() {
    return systemParameterRepository;
  }

  public void setSystemParameterRepository(SystemParameterRepository systemParameterRepository) {
    this.systemParameterRepository = systemParameterRepository;
  }

  @Override
  public List<SystemParameter> findAllEditable() throws Exception {
    List<SystemParameter> systemParameters = getSystemParameterRepository().findAll();
    Iterator<SystemParameter> iterator = systemParameters.iterator();
    while (iterator.hasNext()) {
      SystemParameter systemParameter = iterator.next();
      if (NetworkAccessServiceBean.SP_TOTAL_ATTRIBUTE.equals(systemParameter.getCode())) {
        iterator.remove();
      }
    }
    return systemParameters;
  }

  @Override
  @Transactional(readOnly = false, rollbackFor = Exception.class)
  public void save(List<SystemParameter> systemParameters) throws Exception {
    for (SystemParameter systemParameter : systemParameters) {
      SystemParameter savedSystemParameter = getSystemParameterRepository().findByCode(systemParameter.getCode());
      if (savedSystemParameter != null) {
        BeanUtils.copyProperties(systemParameter, savedSystemParameter, new String[] {"id", "code"});
      }
    }
  }

}
