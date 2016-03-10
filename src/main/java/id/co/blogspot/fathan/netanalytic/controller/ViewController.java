package id.co.blogspot.fathan.netanalytic.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = ViewController.BASE_PATH)
public class ViewController {

  private static final Logger LOGGER = LoggerFactory.getLogger(ViewController.class);
  public static final String BASE_PATH = "/";

  @RequestMapping(method = RequestMethod.GET)
  public String viewHome() throws Exception {
    LOGGER.info("invoking view home at controller");
    return "home";
  }

}
