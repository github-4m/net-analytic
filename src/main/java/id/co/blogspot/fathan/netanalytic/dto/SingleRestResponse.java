package id.co.blogspot.fathan.netanalytic.dto;

public class SingleRestResponse<T> extends BaseRestResponse {

  private static final long serialVersionUID = -92081143757364032L;
  private T value;

  public SingleRestResponse() {
    // do nothing
  }

  public SingleRestResponse(String errorMessage, String errorCode, boolean success, String requestId, T value) {
    super(errorMessage, errorCode, success, requestId);
    this.value = value;
  }

  public T getValue() {
    return value;
  }

  public void setValue(T value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return String.format("SingleRestResponse [value=%s, getValue()=%s]", value, getValue());
  }

}
