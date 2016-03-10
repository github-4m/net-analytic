package id.co.blogspot.fathan.netanalytic.controller;

import java.util.List;

import id.co.blogspot.fathan.netanalytic.dto.SingleRestResponse;
import id.co.blogspot.fathan.netanalytic.service.NetworkAccessService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = NetworkAccessController.BASE_PATH)
public class NetworkAccessController {

	private static final Logger LOGGER = LoggerFactory.getLogger(NetworkAccessController.class);
	public static final String BASE_PATH = "/network-access";
	public static final String CLUSTER = "/cluster";
	public static final String FILTER_TOTAL_PER_CLUSTER = "/filter/total-per-cluster";

	@Autowired
	private NetworkAccessService networkAccessService;

	public NetworkAccessService getNetworkAccessService() {
		return networkAccessService;
	}

	public void setNetworkAccessService(NetworkAccessService networkAccessService) {
		this.networkAccessService = networkAccessService;
	}

	@RequestMapping(value = NetworkAccessController.CLUSTER, method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public SingleRestResponse<List<List<Long>>> cluster(@RequestParam String requestId) throws Exception {
		try {
			LOGGER.info("invoking cluster network access at controller. Request Id : {}", new Object[] { requestId });
			List<List<Long>> centroids = getNetworkAccessService().cluster();
			return new SingleRestResponse<List<List<Long>>>(null, null, true, requestId, centroids);
		} catch (Exception e) {
			LOGGER.error("error invoking cluster network access at controller.", e);
			return new SingleRestResponse<List<List<Long>>>(e.getMessage(), null, false, requestId, null);
		}
	}

	@RequestMapping(value = NetworkAccessController.FILTER_TOTAL_PER_CLUSTER, method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public SingleRestResponse<List<List<Integer>>> filterTotalPerCluster(@RequestParam String requestId)
			throws Exception {
		try {
			LOGGER.info("invoking filter total per cluster network access at controller. Request Id : {}",
					new Object[] { requestId });
			List<List<Integer>> totalPerClusters = getNetworkAccessService().findTotalPerCluster();
			return new SingleRestResponse<List<List<Integer>>>(null, null, true, requestId, totalPerClusters);
		} catch (Exception e) {
			LOGGER.error("error invoking filter total per cluster network access at controller.", e);
			return new SingleRestResponse<List<List<Integer>>>(e.getMessage(), null, false, requestId, null);
		}
	}

}
