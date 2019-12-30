package ca.jrvs.apps.twitter.spring;

import ca.jrvs.apps.twitter.TwitterCliApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "ca.jrvs.apps.twitter")
public class TwitterCliSpringBoot implements CommandLineRunner {

  private TwitterCliApp cliApp;

  public static void main(String[] args) {
    SpringApplication app = new SpringApplication(TwitterCliSpringBoot.class);
    app.setWebApplicationType(WebApplicationType.NONE);
    app.run(args);
  }

  @Autowired
  public TwitterCliSpringBoot(TwitterCliApp app) {
    cliApp = app;
  }

  @Override
  public void run(String... args) throws Exception {
    cliApp.run(args);
  }
}
