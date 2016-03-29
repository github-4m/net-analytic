package id.co.blogspot.fathan.netanalytic.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SystemParameterBulkRequest implements Serializable {

  private static final long serialVersionUID = -7141094876822241954L;
  private List<SystemParameterRequest> systemParameters = new ArrayList<SystemParameterRequest>();
  
  public SystemParameterBulkRequest(){
    // do nothing
  }

  public SystemParameterBulkRequest(List<SystemParameterRequest> systemParameters) {
    super();
    this.systemParameters = systemParameters;
  }

  public List<SystemParameterRequest> getSystemParameters() {
    return systemParameters;
  }

  public void setSystemParameters(List<SystemParameterRequest> systemParameters) {
    this.systemParameters = systemParameters;
  }

  @Override
  public String toString() {
    return String.format("SystemParameterBulkRequest [systemParameters=%s, getSystemParameters()=%s]",
        systemParameters, getSystemParameters());
  }

}
