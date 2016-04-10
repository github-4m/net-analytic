package id.co.blogspot.fathan.netanalytic.service;

import java.util.List;

import id.co.blogspot.fathan.netanalytic.entity.NetworkAccess;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NetworkAccessService {

  Page<NetworkAccess> findNetworkAccessByClusterCode(String clusterCode, Pageable pageable) throws Exception;

  List<List<Integer>> findTotalPerCluster() throws Exception;

  List<List<Long>> cluster() throws Exception;

  List<NetworkAccess> findAll() throws Exception;

}
