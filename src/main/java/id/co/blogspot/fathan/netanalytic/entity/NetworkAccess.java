package id.co.blogspot.fathan.netanalytic.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = NetworkAccess.TABLE_NAME)
public class NetworkAccess implements Serializable {

  private static final long serialVersionUID = -5192514785118576917L;
  public static final String TABLE_NAME = "NTA_NETWORK_ACCESS";
  public static final String COLUMN_ID = "ID";
  public static final String COLUMN_ACCESS_TIME = "ACCESS_TIME";
  public static final String COLUMN_IP_ADDRESS = "IP_ADDRESS";
  public static final String COLUMN_URL_CLUSTER = "URL_CLUSTER";
  public static final String COLUMN_ACCESS_DURATION = "ACCESS_DURATION";
  public static final String COLUMN_ACCESS_SIZE = "ACCESS_SIZE";
  public static final String COLUMN_CLUSTER_CODE = "CLUSTER_CODE";

  @Id
  @Column(name = COLUMN_ID)
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "uuid2")
  @org.springframework.data.annotation.Id
  private String id;

  @Column(name = COLUMN_ACCESS_TIME)
  private Date accessTime;

  @Column(name = COLUMN_IP_ADDRESS)
  private String ipAddress;

  @Column(name = COLUMN_URL_CLUSTER)
  private Long urlCluster;

  @Column(name = COLUMN_ACCESS_DURATION)
  private Long accessDuration;

  @Column(name = COLUMN_ACCESS_SIZE)
  private Long accessSize;

  @Column(name = COLUMN_CLUSTER_CODE)
  private Integer clusterCode;

  public NetworkAccess() {
    // do nothing
  }

  public NetworkAccess(String id, Date accessTime, String ipAddress, Long urlCluster, Long accessDuration,
      Long accessSize, Integer clusterCode) {
    super();
    this.id = id;
    this.accessTime = accessTime;
    this.ipAddress = ipAddress;
    this.urlCluster = urlCluster;
    this.accessDuration = accessDuration;
    this.accessSize = accessSize;
    this.clusterCode = clusterCode;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Date getAccessTime() {
    return accessTime;
  }

  public void setAccessTime(Date accessTime) {
    this.accessTime = accessTime;
  }

  public String getIpAddress() {
    return ipAddress;
  }

  public void setIpAddress(String ipAddress) {
    this.ipAddress = ipAddress;
  }

  public Long getUrlCluster() {
    return urlCluster;
  }

  public void setUrlCluster(Long urlCluster) {
    this.urlCluster = urlCluster;
  }

  public Long getAccessDuration() {
    return accessDuration;
  }

  public void setAccessDuration(Long accessDuration) {
    this.accessDuration = accessDuration;
  }

  public Long getAccessSize() {
    return accessSize;
  }

  public void setAccessSize(Long accessSize) {
    this.accessSize = accessSize;
  }

  public Integer getClusterCode() {
    return clusterCode;
  }

  public void setClusterCode(Integer clusterCode) {
    this.clusterCode = clusterCode;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("NetworkAccess [id=").append(id).append(", accessTime=").append(accessTime).append(", ipAddress=")
        .append(ipAddress).append(", urlCluster=").append(urlCluster).append(", accessDuration=")
        .append(accessDuration).append(", accessSize=").append(accessSize).append(", clusterCode=").append(clusterCode)
        .append(", getId()=").append(getId()).append(", getAccessTime()=").append(getAccessTime())
        .append(", getIpAddress()=").append(getIpAddress()).append(", getUrlCluster()=").append(getUrlCluster())
        .append(", getAccessDuration()=").append(getAccessDuration()).append(", getAccessSize()=")
        .append(getAccessSize()).append(", getClusterCode()=").append(getClusterCode()).append("]");
    return builder.toString();
  }

}
