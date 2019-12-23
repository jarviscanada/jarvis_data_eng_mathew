package ca.jrvs.apps.twitter.spring;

import ca.jrvs.apps.twitter.TwitterCliApp;
import ca.jrvs.apps.twitter.controller.Controller;
import ca.jrvs.apps.twitter.controller.TwitterController;
import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import ca.jrvs.apps.twitter.service.TwitterService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TwitterCliBean {

  public static void main(String[] args) {
    ApplicationContext appCon = new AnnotationConfigApplicationContext(TwitterCliBean.class);
    TwitterCliApp twitterCliApp = appCon.getBean(TwitterCliApp.class);
    twitterCliApp.run(args);
  }

  @Bean
  public TwitterCliApp twitterCliApp(Controller twitterController) {
    return new TwitterCliApp(twitterController);
  }

  @Bean
  public TwitterController twitterController(Service twitterService) {
    return new TwitterController(twitterService);
  }

  @Bean
  public TwitterService twitterService(CrdDao<Tweet, Long> twitterDao) {
    return  new TwitterService(twitterDao);
  }

  @Bean
  public TwitterDao twitterDao(HttpHelper twitterHttpHelper) {
    return new TwitterDao(twitterHttpHelper);
  }

  @Bean
  public TwitterHttpHelper twitterHttpHelper() {
    return new TwitterHttpHelper(
        System.getenv("CONSUMER_TOKEN"),
        System.getenv("CONSUMER_SECRET"),
        System.getenv("ACCESS_TOKEN"),
        System.getenv("ACCESS_SECRET")
    );
  }

}
