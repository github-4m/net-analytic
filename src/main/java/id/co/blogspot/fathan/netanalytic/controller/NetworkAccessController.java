package id.co.blogspot.fathan.netanalytic.controller;

import java.util.ArrayList;
import java.util.List;

import id.co.blogspot.fathan.netanalytic.dto.ListRestResponse;
import id.co.blogspot.fathan.netanalytic.dto.PageMetaData;
import id.co.blogspot.fathan.netanalytic.dto.SingleRestResponse;
import id.co.blogspot.fathan.netanalytic.entity.NetworkAccess;
import id.co.blogspot.fathan.netanalytic.service.NetworkAccessService;
import id.co.blogspot.fathan.netanalytic.util.ValueConverter;

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

  @RequestMapping(value = NetworkAccessController.CLUSTER, method = RequestMethod.GET,
      produces = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseBody
  public SingleRestResponse<List<List<Long>>> cluster(@RequestParam String requestId, @RequestParam(
      defaultValue = "true") Boolean useGeneticAlgorithm) throws Exception {
    try {
      LOGGER.info("invoking cluster network access at controller. Request Id : {}, Use Genetic Algorithm : {}",
          new Object[] {requestId, useGeneticAlgorithm});
      List<List<Long>> centroids = getNetworkAccessService().cluster(useGeneticAlgorithm);
      return new SingleRestResponse<List<List<Long>>>(null, null, true, requestId, centroids);
    } catch (Exception e) {
      LOGGER.error("error invoking cluster network access at controller.", e);
      return new SingleRestResponse<List<List<Long>>>(e.getMessage(), null, false, requestId, null);
    }
  }

  @RequestMapping(value = NetworkAccessController.FILTER_TOTAL_PER_CLUSTER, method = RequestMethod.GET,
      produces = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseBody
  public SingleRestResponse<List<List<Integer>>> filterTotalPerCluster(@RequestParam String requestId) throws Exception {
    try {
      LOGGER.info("invoking filter total per cluster network access at controller. Request Id : {}",
          new Object[] {requestId});
      List<List<Integer>> totalPerClusters = getNetworkAccessService().findTotalPerCluster();
      return new SingleRestResponse<List<List<Integer>>>(null, null, true, requestId, totalPerClusters);
    } catch (Exception e) {
      LOGGER.error("error invoking filter total per cluster network access at controller.", e);
      return new SingleRestResponse<List<List<Integer>>>(e.getMessage(), null, false, requestId, null);
    }
  }

  @RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseBody
  public ListRestResponse<List<Long>> filterAll(@RequestParam String requestId) throws Exception {
    try {
      LOGGER.info("invoking filter all network access at controller. Request Id : {}", new Object[] {requestId});
      List<NetworkAccess> networkAccesses = getNetworkAccessService().findAll();
      List<List<Long>> networkAccessResponses = new ArrayList<List<Long>>();
      for (NetworkAccess networkAccess : networkAccesses) {
        List<Long> networkAccessResponse = new ArrayList<Long>();
        networkAccessResponse.add(networkAccess.getAccessTime().getTime());
        networkAccessResponse.add(ValueConverter.stringToInteger(networkAccess.getIpAddress()).longValue());
        networkAccessResponse.add(networkAccess.getUrlCluster());
        networkAccessResponse.add(networkAccess.getAccessDuration());
        networkAccessResponse.add(networkAccess.getAccessSize());
        networkAccessResponse.add(networkAccess.getClusterCode().longValue());
        networkAccessResponses.add(networkAccessResponse);
      }
      return new ListRestResponse<List<Long>>(null, null, true, requestId, networkAccessResponses, new PageMetaData(0,
          networkAccesses.size(), networkAccesses.size()));
    } catch (Exception e) {
      LOGGER.error("error invoking filter all network access at controller.", e);
      return new ListRestResponse<List<Long>>(e.getMessage(), null, false, requestId, null, null);
    }
  }

}
