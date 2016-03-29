package id.co.blogspot.fathan.netanalytic.controller;

import java.util.ArrayList;
import java.util.List;

import id.co.blogspot.fathan.netanalytic.dto.BaseRestResponse;
import id.co.blogspot.fathan.netanalytic.dto.ListRestResponse;
import id.co.blogspot.fathan.netanalytic.dto.PageMetaData;
import id.co.blogspot.fathan.netanalytic.dto.SystemParameterBulkRequest;
import id.co.blogspot.fathan.netanalytic.dto.SystemParameterRequest;
import id.co.blogspot.fathan.netanalytic.dto.SystemParameterResponse;
import id.co.blogspot.fathan.netanalytic.entity.SystemParameter;
import id.co.blogspot.fathan.netanalytic.service.SystemParameterService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = SystemParameterController.BASE_PATH)
public class SystemParameterController {

  private static final Logger LOGGER = LoggerFactory.getLogger(SystemParameterController.class);
  public static final String BASE_PATH = "/system-parameter";
  public static final String FILTER_ALL_EDITABLE = "/filter/all-editable";
  public static final String BULK_SAVE = "/bulk/save";

  @Autowired
  private SystemParameterService systemParameterService;

  public SystemParameterService getSystemParameterService() {
    return systemParameterService;
  }

  public void setSystemParameterService(SystemParameterService systemParameterService) {
    this.systemParameterService = systemParameterService;
  }

  @RequestMapping(value = SystemParameterController.FILTER_ALL_EDITABLE, method = RequestMethod.GET,
      produces = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseBody
  public ListRestResponse<SystemParameterResponse> filterAllEditable(@RequestParam String requestId) throws Exception {
    try {
      LOGGER.info("invoking filter all editable system parameter at controller. Request Id : {}",
          new Object[] {requestId});
      List<SystemParameter> systemParameters = getSystemParameterService().findAllEditable();
      List<SystemParameterResponse> responses = new ArrayList<SystemParameterResponse>();
      for (SystemParameter systemParameter : systemParameters) {
        SystemParameterResponse response = new SystemParameterResponse();
        BeanUtils.copyProperties(systemParameter, response);
        responses.add(response);
      }
      return new ListRestResponse<SystemParameterResponse>(null, null, true, requestId, responses, new PageMetaData(0,
          systemParameters.size(), systemParameters.size()));
    } catch (Exception e) {
      LOGGER.error("error invoking filter all editable system parameter at controller.", e);
      return new ListRestResponse<SystemParameterResponse>(e.getMessage(), null, false, requestId, null, null);
    }
  }

  @RequestMapping(value = SystemParameterController.BULK_SAVE, method = RequestMethod.POST,
      consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseBody
  public BaseRestResponse bulkSave(@RequestParam String requestId, @RequestBody SystemParameterBulkRequest request)
      throws Exception {
    try {
      LOGGER.info("invoking bulk save system parameter at controller. Request Id : {}, Request Body : {}",
          new Object[] {requestId, request});
      List<SystemParameter> systemParameters = new ArrayList<SystemParameter>();
      for (SystemParameterRequest systemParameterRequest : request.getSystemParameters()) {
        SystemParameter systemParameter = new SystemParameter();
        BeanUtils.copyProperties(systemParameterRequest, systemParameter);
        systemParameters.add(systemParameter);
      }
      getSystemParameterService().save(systemParameters);
      return new BaseRestResponse(null, null, true, requestId);
    } catch (Exception e) {
      LOGGER.error("error invoking bulk save system parameter at controller.", e);
      return new BaseRestResponse(e.getMessage(), null, false, requestId);
    }
  }

}
