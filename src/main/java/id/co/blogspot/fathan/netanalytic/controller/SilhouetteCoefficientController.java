package id.co.blogspot.fathan.netanalytic.controller;

import java.util.List;
import java.util.Map;

import id.co.blogspot.fathan.netanalytic.dto.SingleRestResponse;
import id.co.blogspot.fathan.netanalytic.service.SilhouetteCoefficientService;

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
@RequestMapping(value = SilhouetteCoefficientController.BASE_PATH)
public class SilhouetteCoefficientController {

  private static final Logger LOGGER = LoggerFactory.getLogger(SilhouetteCoefficientController.class);
  public static final String BASE_PATH = "/silhouette-coefficient";
  public static final String CALCULATE = "/calculate";

  @Autowired
  private SilhouetteCoefficientService silhouetteCoefficientService;

  public SilhouetteCoefficientService getSilhouetteCoefficientService() {
    return silhouetteCoefficientService;
  }

  public void setSilhouetteCoefficientService(SilhouetteCoefficientService silhouetteCoefficientService) {
    this.silhouetteCoefficientService = silhouetteCoefficientService;
  }

  @RequestMapping(value = SilhouetteCoefficientController.CALCULATE, method = RequestMethod.GET,
      produces = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseBody
  public SingleRestResponse<List<Map<Integer, Object>>> calculate(@RequestParam String requestId) throws Exception {
    try {
      LOGGER.info("invoking calculate silhouette coefficient at controller. Request Id : {}", new Object[] {requestId});
      List<Map<Integer, Object>> silhouetteValuePerDataAndAveragePerCluster =
          getSilhouetteCoefficientService().calculateSilhouetteCoefficient();
      return new SingleRestResponse<List<Map<Integer, Object>>>(null, null, true, requestId,
          silhouetteValuePerDataAndAveragePerCluster);
    } catch (Exception e) {
      LOGGER.error("error invoking calculate silhouette coefficient at controller.", e);
      return new SingleRestResponse<List<Map<Integer, Object>>>(e.getMessage(), null, false, requestId, null);
    }
  }

}
