package ca.jrvs.apps.trading.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@CrossOrigin
@RequestMapping("/status")
public class AppController {

  @GetMapping("/health")
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public String health() {
    return "Healthy";
  }
}
