package id.co.blogspot.fathan.netanalytic.dto;

import java.io.Serializable;

public class BaseRestResponse implements Serializable {

  private static final long serialVersionUID = -6686454059205454089L;
  private String errorMessage;
  private String errorCode;
  private boolean success;
  private String requestId;

  public BaseRestResponse() {
    // do nothing
  }

  public BaseRestResponse(String errorMessage, String errorCode, boolean success, String requestId) {
    super();
    this.errorMessage = errorMessage;
    this.errorCode = errorCode;
    this.success = success;
    this.requestId = requestId;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public String getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(String errorCode) {
    this.errorCode = errorCode;
  }

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public String getRequestId() {
    return requestId;
  }

  public void setRequestId(String requestId) {
    this.requestId = requestId;
  }

  @Override
  public String toString() {
    return String
        .format(
            "BaseRestResponse [errorMessage=%s, errorCode=%s, success=%s, requestId=%s, getErrorMessage()=%s, getErrorCode()=%s, isSuccess()=%s, getRequestId()=%s]",
            errorMessage, errorCode, success, requestId, getErrorMessage(), getErrorCode(), isSuccess(), getRequestId());
  }

}
