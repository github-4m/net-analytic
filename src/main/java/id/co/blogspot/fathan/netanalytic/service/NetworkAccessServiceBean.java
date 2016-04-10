package id.co.blogspot.fathan.netanalytic.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import id.co.blogspot.fathan.netanalytic.core.GeneticAlgorithm;
import id.co.blogspot.fathan.netanalytic.core.KMeans;
import id.co.blogspot.fathan.netanalytic.entity.NetworkAccess;
import id.co.blogspot.fathan.netanalytic.repository.NetworkAccessRepository;
import id.co.blogspot.fathan.netanalytic.repository.SystemParameterRepository;
import id.co.blogspot.fathan.netanalytic.util.ValueConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class NetworkAccessServiceBean implements NetworkAccessService {

  public static final String SP_TOTAL_INDIVIDUAL = "TOTAL_INDIVIDUAL";
  public static final String SP_TOTAL_CENTROID = "TOTAL_CENTROID";
  public static final String SP_TOTAL_ATTRIBUTE = "TOTAL_ATTRIBUTE";
  public static final String SP_DEFAULT_ITERATION = "DEFAULT_ITERATION";

  @Autowired
  private NetworkAccessRepository networkAccessRepository;

  @Autowired
  private SystemParameterRepository systemParameterRepository;

  public NetworkAccessRepository getNetworkAccessRepository() {
    return networkAccessRepository;
  }

  public void setNetworkAccessRepository(NetworkAccessRepository networkAccessRepository) {
    this.networkAccessRepository = networkAccessRepository;
  }

  public SystemParameterRepository getSystemParameterRepository() {
    return systemParameterRepository;
  }

  public void setSystemParameterRepository(SystemParameterRepository systemParameterRepository) {
    this.systemParameterRepository = systemParameterRepository;
  }

  @Override
  public Page<NetworkAccess> findNetworkAccessByClusterCode(String clusterCode, Pageable pageable) throws Exception {
    return getNetworkAccessRepository().findByClusterCode(clusterCode, pageable);
  }

  @Override
  public List<List<Integer>> findTotalPerCluster() throws Exception {
    Integer totalCentroid =
        Integer.parseInt(getSystemParameterRepository().findByCode(NetworkAccessServiceBean.SP_TOTAL_CENTROID)
            .getValue());
    List<Integer> clusterCodeList = new ArrayList<Integer>();
    for (int i = 0; i < totalCentroid; i++) {
      clusterCodeList.add(i);
    }
    Object[] totalPerClusterRaws = getNetworkAccessRepository().findTotalPerCluster(clusterCodeList);
    List<List<Integer>> totalPerClusters = new ArrayList<List<Integer>>();
    for (Object totalPerClusterRaw : totalPerClusterRaws) {
      Object[] columns = (Object[]) totalPerClusterRaw;
      List<Integer> totalPerCluster = new ArrayList<Integer>();
      totalPerCluster.add((Integer) columns[0]);
      totalPerCluster.add(((Long) columns[1]).intValue());
      totalPerClusters.add(totalPerCluster);
    }
    return totalPerClusters;
  }

  @Override
  @Transactional(readOnly = false, rollbackFor = Exception.class)
  public List<List<Long>> cluster() throws Exception {
    List<NetworkAccess> networkAccesses = getNetworkAccessRepository().findAll();
    Integer totalIndividual =
        Integer.parseInt(getSystemParameterRepository().findByCode(NetworkAccessServiceBean.SP_TOTAL_INDIVIDUAL)
            .getValue());
    Integer totalCentroid =
        Integer.parseInt(getSystemParameterRepository().findByCode(NetworkAccessServiceBean.SP_TOTAL_CENTROID)
            .getValue());
    Integer totalAttribute =
        Integer.parseInt(getSystemParameterRepository().findByCode(NetworkAccessServiceBean.SP_TOTAL_ATTRIBUTE)
            .getValue());
    Integer defaultIteration =
        Integer.parseInt(getSystemParameterRepository().findByCode(NetworkAccessServiceBean.SP_DEFAULT_ITERATION)
            .getValue());
    Object[] maxAttributes = (Object[]) getNetworkAccessRepository().findMaxAttributes();
    List<Long> maxValueAttributes = new ArrayList<Long>();
    maxValueAttributes.add(((Date) maxAttributes[0]).getTime());
    maxValueAttributes.add(ValueConverter.stringToInteger(String.valueOf(maxAttributes[1])).longValue());
    maxValueAttributes.add((Long) maxAttributes[2]);
    maxValueAttributes.add((Long) maxAttributes[3]);
    maxValueAttributes.add((Long) maxAttributes[4]);
    GeneticAlgorithm geneticAlgorithm =
        new GeneticAlgorithm(networkAccesses, maxValueAttributes, totalAttribute, totalCentroid, totalIndividual);
    List<List<Long>> centroids = geneticAlgorithm.generateCentroid(defaultIteration);
    KMeans kMeans = new KMeans(networkAccesses, centroids, totalAttribute, totalCentroid);
    kMeans.cluster(defaultIteration, centroids);
    getNetworkAccessRepository().save(networkAccesses);
    return centroids;
  }

  @Override
  public List<NetworkAccess> findAll() throws Exception {
    return getNetworkAccessRepository().findAll();
  }

}
