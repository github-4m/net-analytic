package id.co.blogspot.fathan.netanalytic.dto;

import java.io.Serializable;

public class SystemParameterResponse implements Serializable {

  private static final long serialVersionUID = 3606208952926093137L;
  private String code;
  private String value;
  
  public SystemParameterResponse() {
    // do nothing
  }

  public SystemParameterResponse(String code, String value) {
    super();
    this.code = code;
    this.value = value;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return String.format("SystemParameterResponse [code=%s, value=%s, getCode()=%s, getValue()=%s]", code, value,
        getCode(), getValue());
  }

}
