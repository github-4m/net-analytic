package id.co.blogspot.fathan.netanalytic.repository;

import id.co.blogspot.fathan.netanalytic.entity.NetworkAccess;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NetworkAccessRepository extends JpaRepository<NetworkAccess, String> {

	String QUERY_FIND_MAX_ATTRIBUTES = "SELECT MAX(na.accessTime), MAX(na.ipAddress), MAX(na.urlCluster), MAX(na.accessDuration), MAX(na.accessSize)"
			+ " FROM NetworkAccess na";
	String QUERY_FIND_TOTAL_PER_CLUSTER = "SELECT na.clusterCode, COUNT(na.clusterCode)"
			+ " FROM NetworkAccess na WHERE na.clusterCode IN (?1) GROUP BY na.clusterCode";

	Page<NetworkAccess> findByClusterCode(String clusterCode, Pageable pageable) throws Exception;

	@Query(value = QUERY_FIND_MAX_ATTRIBUTES)
	Object findMaxAttributes() throws Exception;

	@Query(value = QUERY_FIND_TOTAL_PER_CLUSTER)
	Object[] findTotalPerCluster(List<Integer> clusterCodeList) throws Exception;

}
