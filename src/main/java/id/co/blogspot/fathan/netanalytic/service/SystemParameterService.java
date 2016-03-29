package id.co.blogspot.fathan.netanalytic.service;

import id.co.blogspot.fathan.netanalytic.entity.SystemParameter;

import java.util.List;

public interface SystemParameterService {

  List<SystemParameter> findAllEditable() throws Exception;

  void save(List<SystemParameter> systemParameters) throws Exception;

}
