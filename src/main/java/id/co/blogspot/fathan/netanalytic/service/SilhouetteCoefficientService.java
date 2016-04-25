package id.co.blogspot.fathan.netanalytic.service;

import java.util.List;
import java.util.Map;

public interface SilhouetteCoefficientService {

  List<Map<Integer, Object>> calculateSilhouetteCoefficient() throws Exception;

}
