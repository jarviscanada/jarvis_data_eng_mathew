package ca.jrvs.apps.twitter.spring;

import ca.jrvs.apps.twitter.TwitterCliApp;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(value = "ca.jrvs.apps.twitter")
public class TwitterCliComponentScan {

  public static void main(String[] args) {
    ApplicationContext appCon =
        new AnnotationConfigApplicationContext(TwitterCliComponentScan.class);
    TwitterCliApp app = appCon.getBean(TwitterCliApp.class);
    app.run(args);
  }

}
