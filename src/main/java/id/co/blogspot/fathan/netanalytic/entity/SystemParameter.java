package id.co.blogspot.fathan.netanalytic.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = SystemParameter.TABLE_NAME, uniqueConstraints = {@UniqueConstraint(
    columnNames = {SystemParameter.COLUMN_CODE})})
public class SystemParameter implements Serializable {

  private static final long serialVersionUID = -5065321259541045239L;
  public static final String TABLE_NAME = "NTA_SYSTEM_PARAMETER";
  public static final String COLUMN_ID = "ID";
  public static final String COLUMN_CODE = "CODE";
  public static final String COLUMN_VALUE = "VALUE";
  
  @Id
  @Column(name = COLUMN_ID)
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "uuid2")
  @org.springframework.data.annotation.Id
  private String id;
  
  @Column(name = COLUMN_CODE, nullable = false)
  private String code;
  
  @Column(name= COLUMN_VALUE, nullable=false)
  private String value;
  
  public SystemParameter(){
    // do nothing
  }

  public SystemParameter(String id, String code, String value) {
    super();
    this.id = id;
    this.code = code;
    this.value = value;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
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
    StringBuilder builder = new StringBuilder();
    builder.append("SystemParameter [id=").append(id).append(", code=").append(code).append(", value=").append(value)
        .append(", getId()=").append(getId()).append(", getCode()=").append(getCode()).append(", getValue()=")
        .append(getValue()).append("]");
    return builder.toString();
  }

}
