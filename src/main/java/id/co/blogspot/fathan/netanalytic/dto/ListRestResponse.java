package id.co.blogspot.fathan.netanalytic.dto;

import java.util.List;

public class ListRestResponse<T> extends BaseRestResponse {

  private static final long serialVersionUID = 1953837080502370400L;
  private List<T> content;
  private PageMetaData pageMetaData;
  
  public ListRestResponse() {
    // do nothing
  }

  public ListRestResponse(String errorMessage, String errorCode, boolean success, String requestId, List<T> content,
      PageMetaData pageMetaData) {
    super(errorMessage, errorCode, success, requestId);
    this.content = content;
    this.pageMetaData = pageMetaData;
  }

  public List<T> getContent() {
    return content;
  }

  public void setContent(List<T> content) {
    this.content = content;
  }

  public PageMetaData getPageMetaData() {
    return pageMetaData;
  }

  public void setPageMetaData(PageMetaData pageMetaData) {
    this.pageMetaData = pageMetaData;
  }

  @Override
  public String toString() {
    return String.format("ListRestResponse [content=%s, pageMetaData=%s, getContent()=%s, getPageMetaData()=%s]",
        content, pageMetaData, getContent(), getPageMetaData());
  }

}
