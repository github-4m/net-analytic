package id.co.blogspot.fathan.netanalytic.dto;

import java.io.Serializable;

public class PageMetaData implements Serializable {

  private static final long serialVersionUID = -4627202765629899378L;
  private Integer page;
  private Integer size;
  private Integer totalElement;

  public PageMetaData() {
    // do nothing
  }

  public PageMetaData(Integer page, Integer size, Integer totalElement) {
    super();
    this.page = page;
    this.size = size;
    this.totalElement = totalElement;
  }

  public Integer getPage() {
    return page;
  }

  public void setPage(Integer page) {
    this.page = page;
  }

  public Integer getSize() {
    return size;
  }

  public void setSize(Integer size) {
    this.size = size;
  }

  public Integer getTotalElement() {
    return totalElement;
  }

  public void setTotalElement(Integer totalElement) {
    this.totalElement = totalElement;
  }

  @Override
  public String toString() {
    return String.format(
        "PageMetaData [page=%s, size=%s, totalElement=%s, getPage()=%s, getSize()=%s, getTotalElement()=%s]", page,
        size, totalElement, getPage(), getSize(), getTotalElement());
  }

}
