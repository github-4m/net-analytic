package id.co.blogspot.fathan.netanalytic.repository;

import id.co.blogspot.fathan.netanalytic.entity.SystemParameter;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemParameterRepository extends JpaRepository<SystemParameter, String> {

  SystemParameter findByCode(String code) throws Exception;

}
